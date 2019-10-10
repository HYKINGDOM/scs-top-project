package com.scs.top.project.common.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 返回JSON结果和状态信息
 * @author pmoit
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * 接口信息 提示
     * @param result  返回结果  true，false
     * @param message 提示信息
     * @return
     */
    public static String returnToMessage(boolean result, String message) {
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("success", result);
        resultMap.put("message", message);
        return JsonUtils.toJson(resultMap);
    }


    /**
     * 返回对象
     * @param result 返回结果
     * @param obj    返回对象
     * @return
     */
    public static String returnToResult(boolean result, Object obj) {
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("success", result);
        resultMap.put("data", obj);
        return JsonUtils.toJson(resultMap);
    }


    /**
     * 初始化一个新创建的 Message 对象
     */
    public AjaxResult() {
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error() {
        return error(0, "操作失败");
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult dataException() {
        return error(2, "数据异常,请检查数据");
    }


    /**
     * 返回错误消息
     *
     * @param msg 内容.00
     * @return 错误消息
     */
    public static AjaxResult error(String msg) {
        return error(0, msg);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(int code, String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }


    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", 1);
        json.put("msg", msg);
        return json;
    }


    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return success(1, "操作成功");
    }

    /**
     * 返回成功消息
     *
     * @param key 键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
