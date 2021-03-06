package com.scs.top.project.framework.shiro.kickoutfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * shiro工具类
 * @author admin
 */
public class ShiroFilterUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(ShiroFilterUtils.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 判断请求是否是ajax
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            logger.debug("shiro工具类【wyait-manager-->ShiroFilterUtils.isAjax】当前请求,为Ajax请求");
            return Boolean.TRUE;
        }
        logger.debug("shiro工具类【wyait-manager-->ShiroFilterUtils.isAjax】当前请求,非Ajax请求");
        return Boolean.FALSE;
    }

    /**
     * response输出json
     *
     * @param response
     * @param result
     */
    public static void out(HttpServletResponse response, ResponseResult result) {
        PrintWriter out = null;
        try {
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //设置返回类型
            response.setContentType("application/json");
            out = response.getWriter();
            //输出
            out.println(objectMapper.writeValueAsString(result));
            logger.error("用户在线数量限制【wyait-manage-->ShiroFilterUtils.out】响应json信息成功");
        } catch (Exception e) {
            logger.error("用户在线数量限制【wyait-manage-->ShiroFilterUtils.out】响应json信息出错", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

}
