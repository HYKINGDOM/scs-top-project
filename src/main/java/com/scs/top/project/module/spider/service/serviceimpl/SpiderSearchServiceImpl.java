package com.scs.top.project.module.spider.service.serviceimpl;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.scs.top.project.common.constant.RedisConstans;
import com.scs.top.project.framework.redis.RedisUtil;
import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;
import com.scs.top.project.module.spider.service.SpiderSearchService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.scs.top.project.common.util.JsoupUtils.getHtmlDocumentPost;
import static com.scs.top.project.common.util.JsoupUtils.returnUrlSwitch;
import static com.scs.top.project.common.util.Utils.encodingSwitchToGbk;
import static com.scs.top.project.common.util.Utils.returnUrlRequestHeard;


/**
 * @author Administrator
 */
@Service
public class SpiderSearchServiceImpl implements SpiderSearchService {

    public static final Logger logger = LoggerFactory.getLogger(SpiderSearchServiceImpl.class);


    @Autowired
    private RedisUtil redisUtil;


    /**
     * 设置查询的公共方法
     *
     * @param url
     * @return
     */
    public Document getHtmlDocument(String url) {
        Document document = null;
        try {
            Connection con = Jsoup.connect(url);
            con.header("Accept", "text/html, application/xhtml+xml, */*");
            con.header("Content-Type", "application/x-www-form-urlencoded");
            con.header("User-Agent", returnUrlRequestHeard());
            //解析请求结果
            document = con.get();
        } catch (IOException e) {
            e.getMessage();
        }
        return document;
    }


    @Override
    public List<BookInfo> getBookContentInfoByBookIdAndChapter(String bookName) {

        List<BookInfo> bookChapterList = new ArrayList<>();
        BookInfo bookInfo = new BookInfo();
        //书籍名字转码
        String encodingSwitchToGbk = encodingSwitchToGbk(bookName);
        //获取请求连接 查询URL
        String searchUrl = "https://www.x23us.com/modules/article/search.php?searchtype=keywords&searchkey=" + encodingSwitchToGbk;
        Document document = getHtmlDocument(searchUrl);
        Element content = document.getElementById("content");
        Elements links = content.getElementsByTag("tr");
        for (Element link : links) {
            if (link.text().contains("文章名称") && link.text().contains("最新章节")) {
                continue;
            }
            Elements byTag = link.getElementsByTag("a");
            String bookUrl = byTag.attr("href");
            // 获取该class内容 进行遍历
            Elements odd = link.getElementsByClass("odd");
            bookInfo.setBookAddressUrl(bookUrl);
            for (Element element : odd) {
                Elements taga = element.getElementsByTag("a");
                Elements center = element.getElementsByAttribute("align");
                String text = element.text();
                if (!taga.isEmpty()) {
                    bookInfo.setBookName(text);
                } else if (!center.isEmpty()) {
                    bookInfo.setUpdateTime(element.text());
                } else {
                    bookInfo.setAuthor(text);
                }
            }
            // 获取该class内容 进行遍历
            Elements even = link.getElementsByClass("even");
            for (Element element : even) {
                Elements tagA = element.getElementsByTag("a");
                Elements center = element.getElementsByAttribute("align");
                String text = element.text();
                if (!tagA.isEmpty()) {
                    String bookIndexUrl = tagA.attr("href");
                    bookInfo.setBookIndexUrl(bookIndexUrl);
                    bookInfo.setLatestChapters(text);
                } else if (!center.isEmpty()) {
                    bookInfo.setCurrentState(element.text());
                } else {
                    bookInfo.setArticleLength(text);
                }
            }
            bookInfo.setBookSource("https://www.x23us.com");
            bookChapterList.add(bookInfo);
            bookInfo = new BookInfo();
        }
        return bookChapterList;
    }


    @Override
    public BookChapter searchBookInfoReturnFirstChapterInfo(BookInfo bookInfo) {
        Long bookId = bookInfo.getBookId();
        String redisKey = bookId.toString() + "-00001";
        BookChapter bookChapter = new BookChapter();
        if (!redisUtil.hasKey(redisKey)) {
            bookChapter.setBookId(bookInfo.getBookId());
            String bookIndexUrl = bookInfo.getBookIndexUrl();
            Document document = getHtmlDocumentPost(bookIndexUrl);
            Element elementByIdAt = document.getElementById("at");
            Elements elementsByTag = elementByIdAt.getElementsByTag("a");
            Element element = elementsByTag.get(0);
            bookChapter.setChapterName(element.text());
            String href = element.attr("href");
            String urlAddress = bookIndexUrl + href;
            Document htmlDocument = getHtmlDocumentPost(urlAddress);
            Element contents = htmlDocument.getElementById("contents");
            String text = contents.text();
            bookChapter.setChapterContent(text);
            bookChapter.setBookId(bookId);
            bookChapter.setChapterContentNum(text.length());
            redisUtil.set(redisKey, bookChapter, RedisConstans.ONE_DAY_TIME_LONG);
            logger.info("===========存入缓存===========  key = " + redisKey + "当前模块为:pmit-project-spider-search");
        } else {
            bookChapter = (BookChapter) redisUtil.get(redisKey);
            logger.info("===========取出缓存===========  key = " + redisKey + "当前模块为:pmit-project-spider-search");
        }

        return bookChapter;
    }

    private List<Map<String, Object>> returnChapterIndex(String bookIndexUrl) {
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
        Document document = getHtmlDocumentPost(bookIndexUrl);
        Elements elementByIdAt = document.getElementById("at").getElementsByTag("a");

        for (Element element : elementByIdAt) {
            String indexName = element.text();
            map.put("indexName", indexName);
            String indexUrl = bookIndexUrl + element.attr("href");
            map.put("indexUrl", indexUrl);
            list.add(map);
            map = Maps.newHashMapWithExpectedSize(2);
        }
        return list;
    }



    @Override
    public List<Map<String, Object>> searchBookInfoReturnChapterIndex(BookInfo bookInfo) {
        String bookInfoIndex = bookInfo.getBookId().toString() + "-index";
        List<Map<String, Object>> list = null;
        if (!redisUtil.hasKey(bookInfoIndex)) {
            list = returnChapterIndex(bookInfo.getBookIndexUrl());
            redisUtil.set(bookInfoIndex, list, RedisConstans.ONE_DAY_TIME_LONG);
            logger.info("===========存入缓存===========  key = " + bookInfoIndex + "当前模块为:pmit-project-spider-search");
        } else {
            list = (List<Map<String, Object>>) redisUtil.get(bookInfoIndex);
            logger.info("===========取出缓存===========  key = " + bookInfoIndex + "当前模块为:pmit-project-spider-search");
        }
        return list;
    }

    @Override
    public BookChapter searchBookInfoReturnAnyChapterInfo(BookInfo bookInfo, String chapterIndex) {
        long bookId = bookInfo.getBookId();
        String redisKey = bookId + "-" + returnUrlSwitch(chapterIndex);
        BookChapter bookChapter = new BookChapter();
        if (!redisUtil.hasKey(redisKey)) {
            Document htmlDocumentPost = getHtmlDocumentPost(chapterIndex);
            String h1 = htmlDocumentPost.getElementsByTag("h1").text();
            bookChapter.setChapterName(h1);
            Element contents = htmlDocumentPost.getElementById("contents");
            String text = contents.text();
            bookChapter.setChapterContent(text);
            bookChapter.setBookId(bookId);
            bookChapter.setChapterContentNum(text.length());
            redisUtil.set(redisKey, bookChapter, RedisConstans.ONE_DAY_TIME_LONG);
            logger.info("===========存入缓存===========  key = " + redisKey + "当前模块为:pmit-project-spider-search");
        } else {
            bookChapter = (BookChapter) redisUtil.get(redisKey);
            logger.info("===========取出缓存===========  key = " + redisKey + "当前模块为:pmit-project-spider-search");
        }
        return bookChapter;
    }

    @Override
    public void testBookInfo() {

        BookInfo bookInfo = new BookInfo();
        String baseUrl = "https://www.x23us.com";
        String url = "https://www.x23us.com/book/55435";
        Document htmlDocument = getHtmlDocument(url);
        Element content = htmlDocument.getElementById("content");
        String src = content.getElementsByTag("img").attr("src");
        //书籍地址
        String bookIndexUrl = content.getElementsByClass("read").attr("href");
        Elements elementsByTag = content.getElementById("at").getElementsByTag("td");
        int size = elementsByTag.size();
        for (int i = 0; i < size; i++) {
            Element element = elementsByTag.get(i);
            switch (i) {
                case 0:
                    System.out.println("书籍类型" + element.text());
                    bookInfo.setBookType(element.text());
                    break;
                case 1:
                    System.out.println("作者" + element.text());
                    break;
                case 2:
                    System.out.println("书籍状态" + element.text());
                    bookInfo.setCurrentState(element.text());
                    break;
                case 3:
                    System.out.println("收藏数" + element.text());
                    bookInfo.setCollectionNum(element.text());
                    break;
                case 4:
                    System.out.println("全文长度" + element.text());
                    break;
                case 5:
                    System.out.println("最后更新" + element.text());
                    break;
                case 6:
                    System.out.println("总点击数" + element.text());
                    break;
                case 7:
                    System.out.println("本月点击" + element.text());
                    break;
                case 8:
                    System.out.println("本周点击" + element.text());
                    break;
                case 9:
                    System.out.println("总推荐数" + element.text());
                    break;
                case 10:
                    System.out.println("本月推荐" + element.text());
                    break;
                case 11:
                    System.out.println("本周推荐" + element.text());
                    break;
                default:
                    break;
            }
        }
        //书籍简介内容
        String text = content.getElementsByTag("dd").get(3).getElementsByTag("p").get(1).text();
        bookInfo.setContent(text);
        System.out.println(text);
    }


}
