package com.scs.top.project.module.spider.controller;


import com.scs.top.project.common.util.AjaxResult;
import com.scs.top.project.common.util.JsonUtils;
import com.scs.top.project.common.web.controller.AbstractController;
import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;
import com.scs.top.project.module.spider.service.SpiderSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 爬虫模块的请求接口
 *
 * @author yihur
 */
@RestController
@RequestMapping(value = "/project-search/search")
public class SpiderSearchController extends AbstractController {

    @Autowired
    private SpiderSearchService spiderService;

    @PostMapping(value = "/getbookinfo")
    public String getBookInfo(@RequestBody String bookName) {
        Map<String, String> map = JsonUtils.readValue(bookName, Map.class);
        List<BookInfo> bookChaptersList = null;
        try {
            if (map == null) {
                return AjaxResult.returnToMessage(false, "请出输入查询的书籍名");
            }
            bookChaptersList = spiderService.getBookContentInfoByBookIdAndChapter(map.get("bookName"));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("==========getBookInfo error===========" + e.getMessage());
            return AjaxResult.returnToMessage(false, e.getMessage());
        }
        return AjaxResult.returnToResult(true, bookChaptersList);
    }


    @PostMapping(value = "/testbookinfo")
    public Callable<List<BookInfo>> testBookInfo(@RequestBody String bookName) {
        Map<String, String> map = JsonUtils.readValue(bookName, Map.class);
        Callable<List<BookInfo>> bookInfoCallable = () -> spiderService.getBookContentInfoByBookIdAndChapter(map.get("bookName"));
        return bookInfoCallable;
    }


    /**
     * 根据书籍信息查询 返回首章内容
     *
     * @param param
     * @return bookInfoCallable
     */
    @PostMapping(value = "/searchBookInfoReturnFirstChapterInfo")
    public Callable<BookChapter> searchBookInfoReturnFirstChapterInfo(@RequestBody String param) {
        BookInfo bookInfo = JsonUtils.readValue(param, BookInfo.class);
        return () -> spiderService.searchBookInfoReturnFirstChapterInfo(bookInfo);
    }

    /**
     * 根据书籍信息返回书籍章节目录返回
     *
     * @param param
     * @return Callable<List < Map < String, Object>>>
     */
    @PostMapping(value = "/searchBookInfoReturnChapterIndex")
    public Callable<List<Map<String, Object>>> searchBookInfoReturnChapterIndex(@RequestBody String param) {
        BookInfo bookInfo = JsonUtils.readValue(param, BookInfo.class);
        return () -> spiderService.searchBookInfoReturnChapterIndex(bookInfo);
    }

    /**
     * 根据书籍信息和章节内容返回
     *
     * @param param
     * @return bookInfoCallable
     */
    @PostMapping(value = "/searchBookInfoReturnAnyChapterInfo")
    public Callable<BookChapter> searchBookInfoReturnAnyChapterInfo(@RequestBody String param) {
        Map<String, Object> prams = JsonUtils.readValue(param, Map.class);
        if (param == null) {
            return null;
        }
        BookInfo bookInfo = JsonUtils.readValue(JsonUtils.toJson(prams.get("data")), BookInfo.class);
        String chapterIndex = JsonUtils.readValue(JsonUtils.toJson(prams.get("indexUrl")), String.class);
        return () -> spiderService.searchBookInfoReturnAnyChapterInfo(bookInfo, chapterIndex);
    }

}
