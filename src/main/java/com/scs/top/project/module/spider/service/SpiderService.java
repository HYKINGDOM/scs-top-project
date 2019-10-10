package com.scs.top.project.module.spider.service;


import com.github.pagehelper.PageInfo;
import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;

import java.util.List;
import java.util.Map;

public interface SpiderService {

    /**
     * 查询书籍信息
     * @param bookInfo
     * @return
     */
    List<BookChapter> getBookContentInfoByBookIdAndChapter(BookInfo bookInfo);

    /**
     * 查询测试
     * @param bookInfo
     * @return
     */
    List<BookChapter> getTestBookContentInfoByBookIdAndChapter(BookInfo bookInfo);

    /**
     * 首页展示书籍信息
     * @param pageNum
     * @param pageSize
     * @param bookInfo
     * @return
     */
    PageInfo<BookInfo> getBookInfoHomePage(int pageNum, int pageSize, BookInfo bookInfo);

    /**
     * 查询定时任务表达式
     * @return
     */
    Map<String,Object> getCronMapLists();

    /**
     * 查询网站首页推荐书籍入库
     */
    void getBookInfoUrl();


    /**
     * 更新执行时间
     * @param param
     */
    void updateScheduledTime(Map<String, Object> param);
}
