package com.scs.top.project.module.spider.service.serviceimpl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.scs.top.project.common.constant.RedisConstans;
import com.scs.top.project.common.util.StringUtils;
import com.scs.top.project.framework.druid.DataSourceTransaction;
import com.scs.top.project.framework.druid.TransactionConfig;
import com.scs.top.project.framework.redis.RedisUtil;
import com.scs.top.project.module.spider.mapper.SpiderMapper;
import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;
import com.scs.top.project.module.spider.service.SpiderService;
import com.scs.top.project.module.spider.utils.CrawlSrartService;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static com.scs.top.project.common.util.DateUtils.returnYearMonthDayDateTime;
import static com.scs.top.project.common.util.JsoupUtils.getHtmlDocumentPost;
import static com.scs.top.project.common.util.Utils.returnRadomInt;


/**
 * @author Administrator
 */
@Service
public class SpiderServiceImpl implements SpiderService {

    private final Logger logger = LoggerFactory.getLogger(SpiderServiceImpl.class);


    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Autowired
    private SpiderMapper spiderMapper;

    private RedisUtil redisUtil;

    @Autowired
    public SpiderServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    /**
     * 创建书籍ID
     *
     * @return
     */
    private long createBookId() {
        StringBuilder stringBuilder = new StringBuilder();
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        stringBuilder.append(currentTime);
        Integer randomIntOne = returnRadomInt(2);
        stringBuilder.append(randomIntOne);
        Integer randomIntTwo = returnRadomInt(2);
        stringBuilder.append(randomIntTwo);
        return Long.parseLong(stringBuilder.toString());
    }


    @Override
    @DataSourceTransaction(name="second")
    public List<BookChapter> getBookContentInfoByBookIdAndChapter(BookInfo bookInfo) {
        long bookId = createBookId();
        bookInfo.setBookId(bookId);
        Callable<BookInfo> callable = () -> setBookContentInfo(bookInfo);
        FutureTask<BookInfo> futureTask = new FutureTask<>(callable);
        executorService.execute(futureTask);

        Callable<List<BookChapter>> listCallable = () -> startBookChapterCrawl(bookInfo, 5);
        FutureTask<List<BookChapter>> listFutureTask = new FutureTask<>(listCallable);
        executorService.execute(listFutureTask);
        List<BookChapter> bookChapterList = null;
        try {
            bookChapterList = listFutureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return bookChapterList;
    }

    @Override
    @DataSourceTransaction(name="second")
    public List<BookChapter> getTestBookContentInfoByBookIdAndChapter(BookInfo bookInfo) {
        String bookAddressUrl = bookInfo.getBookIndexUrl();

        Document parse = getHtmlDocumentPost(bookAddressUrl);
        Element aHead = parse.getElementById("at");


        Elements select = aHead.select("a[href]");

        List<String> list = new ArrayList<>();
        for (Element element : select) {
            list.add(element.attr("href"));
        }


        for (int i = 0; i < 10; i++) {
            executorService.execute(new CrawlSrartService(bookAddressUrl, list.get(i), i));
        }

        executorService.shutdown();

        // wait until all tasks are finished
        while (!executorService.isTerminated()) {
            logger.info("等待线程全部执行完成");
        }


        return spiderMapper.getBookContentInfoByBookIdAndChapter(bookInfo);
    }

    @Override
    @DataSourceTransaction(name="second")
    public PageInfo<BookInfo> getBookInfoHomePage(int pageNum, int pageSize, BookInfo bookInfo) {
        PageHelper.startPage(pageNum, pageSize);
        List<BookInfo> bookInfos = null;
        PageInfo<BookInfo> bookInfoPageInfo = null;
        String yearMonthDayDateTime = returnYearMonthDayDateTime() + "-" + pageNum;
        if (!redisUtil.hasKey(yearMonthDayDateTime)) {
            bookInfos = spiderMapper.selectBookInfoData(bookInfo);
            bookInfoPageInfo = new PageInfo<>(bookInfos);
            redisUtil.set(yearMonthDayDateTime, bookInfoPageInfo, RedisConstans.ONE_DAY_TIME_LONG);
            logger.info("===========存入缓存===========  key=" + yearMonthDayDateTime);
        } else {
            logger.info("===========读取缓存===========  key=" + yearMonthDayDateTime);
            Object object = redisUtil.get(yearMonthDayDateTime);
            bookInfoPageInfo = (PageInfo<BookInfo>) object;
            long expire = redisUtil.getExpire(yearMonthDayDateTime);
            logger.info("===========缓存时间===========  time=" + expire);
        }
        return bookInfoPageInfo;
    }

    @Override
    @DataSourceTransaction(name="second")
    public Map<String, Object> getCronMapLists() {
        Map<String, Object> param = Maps.newHashMapWithExpectedSize(2);
        param.put("scheduleTaskNum", 101);
        return spiderMapper.getCronMapLists(param);
    }

    /**
     * 获取首页推荐书籍信息，并存入数据库
     */
    @Override
    @DataSourceTransaction(name="second")
    public void getBookInfoUrl() {
        String bookWebSiteUrl = "https://www.x23us.com/";
        Document htmlDocument = getHtmlDocumentPost(bookWebSiteUrl);
        Element sDdById = htmlDocument.getElementById("s_dd");
        Elements ddByTag = sDdById.getElementsByTag("dd");
        BookInfo bookInfo = new BookInfo();
        for (Element element : ddByTag) {
            String text = element.text();
            bookInfo.setBookName(text);
            Elements elementsByTag = element.getElementsByTag("a");
            String bookAddressUrl = elementsByTag.get(0).attr("href");
            bookInfo.setBookAddressUrl(bookAddressUrl);
            String[] bookUrl = bookAddressUrl.split("/");
            bookInfo.setBookId(Long.valueOf(bookUrl[4]));
            String bookIndexUrl = elementsByTag.get(1).attr("href");
            bookInfo.setBookIndexUrl(bookIndexUrl);
            setBookContentInfo(bookInfo);
            if (!checkObjAllFieldsIsNull(bookInfo)) {
                spiderMapper.saveBookInfoData(bookInfo);
                logger.info(bookInfo.toString());
            }
            bookInfo = new BookInfo();
        }

    }

    @Override
    @DataSourceTransaction(name="second")
    public void updateScheduledTime(Map<String, Object> param) {
        spiderMapper.updateScheduledTime(param);
    }


    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /**
     * 开始爬取书籍数据
     *
     * @param bookInfo
     */
    private List<BookChapter> startBookChapterCrawl(BookInfo bookInfo, int bookChapterNum) {

        BookChapter bookChapter = new BookChapter();
        StringBuilder stringBuilder = new StringBuilder();
        List<BookChapter> bookChapterList = new ArrayList<>();

        Long bookId = bookInfo.getBookId();
        String bookIndexUrl = bookInfo.getBookIndexUrl();
        Document htmlDocument = getHtmlDocumentPost(bookIndexUrl);
        Elements td = htmlDocument.getElementsByTag("td");


        int index = 1;
        int size = td.size();

        if (size <= bookChapterNum) {
            bookChapterNum = size;
        }

        for (int i = 0; i < bookChapterNum; i++) {
            stringBuilder.append(bookIndexUrl);
            bookChapter.setBookId(bookId);
            String attr = td.get(i).getElementsByTag("a").get(0).attr("href");
            String text = td.get(i).text();
            bookChapter.setChapterName(text);
            bookChapter.setChapterNameNum(index);
            Document document = getHtmlDocumentPost(stringBuilder.append(attr).toString());
            Element elementById = document.getElementById("contents");
            String textContext = elementById.text();
            bookChapter.setChapterContentNum(textContext.length());
            bookChapter.setChapterContent(textContext);
            stringBuilder.setLength(0);
            bookChapterList.add(bookChapter);
            index++;
            bookChapter = new BookChapter();
        }

        return bookChapterList;
    }



    public static String getUrlDocumentInfo(String url) {
        String result = null;
        try {
            //创建httpclient对象 (这里设置成全局变量，相对于同一个请求session、cookie会跟着携带过去)
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建get方式请求对象
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-type", "application/json");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //包装一下
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            httpGet.addHeader("Connection", "keep-alive");

            //通过请求对象获取响应对象
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //获取结果实体
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "GBK");
            }

            //释放链接
            response.close();
        }
        //这里还可以捕获超时异常，重新连接抓取
        catch (Exception e) {
            result = null;
            System.err.println("采集操作出错");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据书籍地址获取书籍简介信息
     */
    private BookInfo setBookContentInfo(BookInfo bookInfo) {
        bookInfo.setBookSource("顶点小说网--https://www.x23us.com");
        String addressUrl = "https://www.x23us.com/files";
        Document document = getHtmlDocumentPost(bookInfo.getBookAddressUrl());
        if (document == null) {
            return null;
        }
        Elements dd = document.getElementsByTag("dd");
        String bookName = dd.get(0).text().replace("全文阅读", "").trim();
        bookInfo.setBookName(bookName);
        //获取书籍简介
        Elements text = document.getElementsByTag("p");
        bookInfo.setContent(text.get(5).text());
        //最新章节
        bookInfo.setLatestChapters(text.get(9).getElementsByTag("a").get(0).text());
        //获取书籍封面地址
        Elements elements = document.select("img[src]");
        String srcImg = elements.get(1).attr("src").replace("/files", addressUrl);
        bookInfo.setBookImageUrl(srcImg);
        Element at = document.getElementById("at");
        Elements elementsByTag = at.getElementsByTag("td");
        for (int i = 0; i < elementsByTag.size(); i++) {
            switch (i) {
                case 0:
                    bookInfo.setBookType(elementsByTag.get(i).text());
                    break;
                case 1:
                    bookInfo.setAuthor(elementsByTag.get(i).text());
                    break;
                case 2:
                    bookInfo.setCurrentState(elementsByTag.get(i).text());
                    break;
                case 3:
                    bookInfo.setCollectionNum(elementsByTag.get(i).text());
                    break;
                case 4:
                    bookInfo.setArticleLength(elementsByTag.get(i).text());
                    break;
                case 5:
                    bookInfo.setUpdateTime(elementsByTag.get(i).text());
                    break;
                case 6:
                    bookInfo.setTotalClick(elementsByTag.get(i).text());
                    break;
                case 9:
                    bookInfo.setTotalRecommendedNum(elementsByTag.get(i).text());
                    break;
                default:
                    break;
            }
        }
        return bookInfo;
    }


}
