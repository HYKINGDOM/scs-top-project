package com.scs.top.project.module.spider.mapper;

import com.scs.top.project.module.spider.pojo.BookChapter;
import com.scs.top.project.module.spider.pojo.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Mapper
public interface SpiderMapper {


    /**
     * 保存书籍信息
     * @param bookInfo
     * @return
     */
    void saveBookInfoData(BookInfo bookInfo);

    /**
     * 批量保存书籍信息
     * @param bookInfos
     */
    void saveBookInfoDataLists(List<BookInfo> bookInfos);

    /**
     * 保存书籍内容
     * @param bookChapter
     */
    void saveBookChapter(List<BookChapter> bookChapter);

    /**
     * 保存书籍内容
     * @param bookChapter
     */
    void saveBookChapterOne(BookChapter bookChapter);


    /**
     * 根据书籍章节内容信息查询
     * @param bookInfo
     * @return
     */
    List<BookChapter> getBookContentInfoByBookIdAndChapter(BookInfo bookInfo);

    /**
     * 查询书籍信息
     * @param bookInfo
     * @return
     */
    List<BookInfo> selectBookInfoData(BookInfo bookInfo);

    /**
     * 查询定时任务表达式
     * @param param
     * @return
     */
    Map<String,Object> getCronMapLists(Map<String, Object> param);

    /**
     * 更新执行时间
     * @param param
     */
    void updateScheduledTime(Map<String, Object> param);
}
