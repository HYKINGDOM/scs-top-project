package com.scs.top.project.module.spider.utils;

import com.scs.top.project.module.spider.mapper.SpiderMapper;
import com.scs.top.project.module.spider.pojo.BookChapter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.scs.top.project.common.util.JsoupUtils.getHtmlDocumentPost;
import static com.scs.top.project.common.util.JsoupUtils.trustEveryone;
import static com.scs.top.project.common.util.Utils.returnUrlRequestHeard;


/**
 * @author Administrator
 */
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
        System.out.println("第" + index + "章");
        spiderMapper.saveBookChapterOne(bookChapter);
    }
}
