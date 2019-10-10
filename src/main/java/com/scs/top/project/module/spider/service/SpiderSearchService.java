package com.scs.top.project.module.spider.service;

import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;

import java.util.List;
import java.util.Map;

/**
 * @author yihur
 */
public interface SpiderSearchService {

    /**
     * 查询书籍信息
     *
     * @param bookInfo
     * @return
     */
    List<BookInfo> getBookContentInfoByBookIdAndChapter(String bookInfo);

    /**
     * 根据书籍信息查询 返回首章内容
     *
     * @param bookInfo
     * @return
     */
    BookChapter searchBookInfoReturnFirstChapterInfo(BookInfo bookInfo);

    /**
     * 根据书籍信息返回书籍章节目录返回
     *
     * @param bookInfo
     * @return
     */
    List<Map<String, Object>> searchBookInfoReturnChapterIndex(BookInfo bookInfo);

    /**
     * 根据书籍信息和章节内容返回
     * @param bookInfo
     * @param chapterIndex
     * @return
     */
    BookChapter searchBookInfoReturnAnyChapterInfo(BookInfo bookInfo, String chapterIndex);


    void testBookInfo();
}
