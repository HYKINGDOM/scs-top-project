package com.scs.top.project.common.web.controller;


import com.scs.top.project.common.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yihur
 * controller 公共组件
 */
public abstract class AbstractController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 返回成功
     */
    protected AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    protected AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    protected AjaxResult success(int code, String message) {
        return AjaxResult.success(code, message);
    }

    /**
     * 返回成功消息
     */
    protected AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    protected AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    protected AjaxResult error(int code, String message) {
        return AjaxResult.error(code, message);
    }

}
