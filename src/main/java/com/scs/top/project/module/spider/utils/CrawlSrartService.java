package com.scs.top.project.module.spider.utils;

import com.scs.top.project.module.spider.mapper.SpiderMapper;
import com.scs.top.project.module.spider.pojo.BookChapter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;


import static com.scs.top.project.common.util.JsoupUtils.getHtmlDocumentPost;

/**
 * @author Administrator
 */
@Slf4j
public class CrawlSrartService implements Runnable {


    @Autowired
    private SpiderMapper spiderMapper;

    private String bookAddressUrl;

    private String bookChapterAddress;

    private int index;


    public CrawlSrartService(String bookAddressUrl, String bookChapterAddress,int index) {
        this.bookAddressUrl = bookAddressUrl;
        this.bookChapterAddress = bookChapterAddress;
        this.index = index;
    }


    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        BookChapter bookChapter = new BookChapter();
        stringBuilder.append(bookAddressUrl);
        bookChapter.setChapterNameNum(index);
        Document document = getHtmlDocumentPost(stringBuilder.append(bookChapterAddress).toString());
        Element elementById = document.getElementById("contents");
        String textContext = elementById.text();
        bookChapter.setChapterContentNum(textContext.length());
        bookChapter.setChapterContent(textContext);
        log.info("读取存入第{}章",index);
        spiderMapper.saveBookChapterOne(bookChapter);
    }
}
