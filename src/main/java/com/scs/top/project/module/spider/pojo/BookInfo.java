package com.scs.top.project.module.spider.pojo;


/**
 * 书籍的基本信息
 */
public class BookInfo {

    /**
     * 书籍ID 第一存入的时候生成
     */
    private Long bookId;

    /**
     * 书籍名
     */
    private String bookName;

    /**
     * 书籍类型
     */
    private String bookType;

    /**
     * 最新章节
     */
    private String latestChapters;

    /**
     * 作者
     */
    private String author;

    /**
     * 字数
     */
    private String articleLength;

    /**
     * 最后一次更新时间
     */
    private String updateTime;

    /**
     * 当前状态
     */
    private String currentState;

    /**
     * 收藏数
     */
    private String collectionNum;

    /**
     * 总点击数
     */
    private String totalClick;

    /**
     * 总推荐数
     */
    private String totalRecommendedNum;

    /**
     * 书籍简介
     */
    private String  content;

    /**
     * 书籍简介地址
     */
    private String bookAddressUrl;

    /**
     * 书籍的目录地址
     */
    private String bookIndexUrl;

    /**
     * 书籍封面地址
     */
    private String bookImageUrl;

    /**
     * 书籍来源
     */
    private String bookSource;

    /**
     * 爬取时间
     */
    private String crawlingTime;

    public String getCrawlingTime() {
        return crawlingTime;
    }

    public void setCrawlingTime(String crawlingTime) {
        this.crawlingTime = crawlingTime;
    }

    public String getBookSource() {
        return bookSource;
    }

    public void setBookSource(String bookSource) {
        this.bookSource = bookSource;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLatestChapters() {
        return latestChapters;
    }

    public void setLatestChapters(String latestChapters) {
        this.latestChapters = latestChapters;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleLength() {
        return articleLength;
    }

    public void setArticleLength(String articleLength) {
        this.articleLength = articleLength;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }

    public String getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(String totalClick) {
        this.totalClick = totalClick;
    }

    public String getTotalRecommendedNum() {
        return totalRecommendedNum;
    }

    public void setTotalRecommendedNum(String totalRecommendedNum) {
        this.totalRecommendedNum = totalRecommendedNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookAddressUrl() {
        return bookAddressUrl;
    }

    public void setBookAddressUrl(String bookAddressUrl) {
        this.bookAddressUrl = bookAddressUrl;
    }

    public String getBookIndexUrl() {
        return bookIndexUrl;
    }

    public void setBookIndexUrl(String bookIndexUrl) {
        this.bookIndexUrl = bookIndexUrl;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }


    @Override
    public String toString() {
        return "BookInfo{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookType='" + bookType + '\'' +
                ", latestChapters='" + latestChapters + '\'' +
                ", author='" + author + '\'' +
                ", articleLength='" + articleLength + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", currentState='" + currentState + '\'' +
                ", collectionNum='" + collectionNum + '\'' +
                ", totalClick='" + totalClick + '\'' +
                ", totalRecommendedNum='" + totalRecommendedNum + '\'' +
                ", content='" + content + '\'' +
                ", bookAddressUrl='" + bookAddressUrl + '\'' +
                ", bookIndexUrl='" + bookIndexUrl + '\'' +
                ", bookImageUrl='" + bookImageUrl + '\'' +
                ", bookSource='" + bookSource + '\'' +
                ", crawlingTime='" + crawlingTime + '\'' +
                '}';
    }
}

