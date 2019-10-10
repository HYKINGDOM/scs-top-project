package com.scs.top.project.module.spider.controller;


import com.github.pagehelper.PageInfo;

import com.scs.top.project.common.util.JsonUtils;
import com.scs.top.project.common.web.controller.AbstractCustomResult;
import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;
import com.scs.top.project.module.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 爬虫模块的请求接口
 *
 * @author yihur
 */
@RestController
@RequestMapping(value = "/project-crawl/crawl")
public class SpiderController extends AbstractCustomResult {

    @Autowired
    private SpiderService spiderService;

    @PostMapping(value = "/startcrawlbookchapter")
    public Callable<List<BookChapter>> getCrawlBookChapter(@RequestBody String bookInfo) {
        BookInfo bookInfo1 = JsonUtils.readValue(bookInfo, BookInfo.class);
        return () -> spiderService.getBookContentInfoByBookIdAndChapter(bookInfo1);
    }


    /**
     * 获取首页推荐书籍--数据会缓存到redis
     * @param parameter
     * @return
     */
    @PostMapping(value = "/getBookInfoHomePage")
    public Callable<PageInfo<BookInfo>> getBogetBookInfoHomePageokInfoHomePage(@RequestBody String parameter) {
        Map<String,String> paramMap = JsonUtils.readValue(parameter, Map.class);
        if (paramMap == null) {
            return null;
        }
        int pageNum= Integer.parseInt(String.valueOf(paramMap.get("pageNum")));
        int pageSize= Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
        BookInfo bookInfo = new BookInfo();
        return () -> spiderService.getBookInfoHomePage(pageNum,pageSize,bookInfo);
    }



    @PostMapping(value = "/getTestCrawlBookChapter")
    public List<BookChapter> getTestCrawlBookChapter(@RequestBody String bookInfo) {
        BookInfo bookInfo1 = JsonUtils.readValue(bookInfo, BookInfo.class);
        return spiderService.getBookContentInfoByBookIdAndChapter(bookInfo1);
    }

    @PostMapping(value = "/getTestBookContentInfoByBookIdAndChapter")
    public List<BookChapter> getTestBookContentInfoByBookIdAndChapter(@RequestBody String bookInfo) {
        BookInfo bookInfo1 = JsonUtils.readValue(bookInfo, BookInfo.class);
        return spiderService.getTestBookContentInfoByBookIdAndChapter(bookInfo1);
    }


    @GetMapping(value = "/getBookInfoUrl")
    public String getBookInfoUrl(){
        spiderService.getBookInfoUrl();
        return "查询成功";
    }

}
