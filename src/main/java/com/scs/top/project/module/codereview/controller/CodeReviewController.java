package com.scs.top.project.module.codereview.controller;


import com.scs.top.project.common.util.JsonUtil;
import com.scs.top.project.common.web.controller.AbstractCustomResult;
import com.scs.top.project.module.codereview.service.CodeReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.Callable;


/**
 * todo v1.0需要的接口
 *      1,首页code review信息接口
 *      2,用户注册
 *      3,用户登录
 *
 *
 *
 *
 *
 *
 * @author Administrator
 */
@RestController
@RequestMapping(value = "/scs-top/scs")
public class CodeReviewController extends AbstractCustomResult {

    @Autowired
    private CodeReviewService codeReviewService;

    @PostMapping(value = "/getScsHomePageInfoPage")
    public Callable<Object> getScsHomePageInfoPage(@RequestBody String parameter) {
        Map<String, String> paramMap = (Map<String, String>) JsonUtil.toMap(parameter);
        if (paramMap == null) {
            return returnIsNullError();
        }
        Callable callable;
        try {
            int pageNum = Integer.parseInt(String.valueOf(paramMap.get("pageNum")));
            int pageSize = Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
            callable = () -> codeReviewService.getScsHomePageInfoPage(pageNum, pageSize, paramMap);
        } catch (Exception e) {
            return returnToException("查询异常", e.getClass() + ":" + e.getMessage());
        }
        return returnToCallable(200, callable, "查询成功", "scs home page query success", true);
    }
}
