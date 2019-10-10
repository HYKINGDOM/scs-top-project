package com.scs.top.project.module.spider.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author yihur
 */
public class BookChapter {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 书籍ID
     */
    private Long bookId;

    /**
     * 章节名编号
     */
    private Integer chapterNameNum;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 章节字数
     */
    private Integer chapterContentNum;

    /**
     * 章节内容
     */
    private String chapterContent;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getChapterNameNum() {
        return chapterNameNum;
    }

    public void setChapterNameNum(Integer chapterNameNum) {
        this.chapterNameNum = chapterNameNum;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getChapterContentNum() {
        return chapterContentNum;
    }

    public void setChapterContentNum(Integer chapterContentNum) {
        this.chapterContentNum = chapterContentNum;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
