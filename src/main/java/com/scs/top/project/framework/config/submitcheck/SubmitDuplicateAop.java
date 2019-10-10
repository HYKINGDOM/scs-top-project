package com.scs.top.project.framework.config.submitcheck;

import com.google.common.cache.Cache;
import com.scs.top.project.common.util.AjaxResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * aop解析注解
 * 注解使用方法就是在controller接口上加上@SubmitDuplicate 注解
 * 注意:一般只用给暴露给用户操作的接口加上该注解
 * @author yihur
 * @date 2019/4/22
 */
@Aspect
@Component
public class SubmitDuplicateAop {

    private Log logger = LogFactory.getLog(getClass());

    @Resource
    private Cache<String, Integer> cache;

    @Around("execution(* com.isoftstone.pmit.*.*.controller.*Controller.*(..)) && @annotation(nrs)")
    public Object around(ProceedingJoinPoint pjp, SubmitDuplicate nrs) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionId = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId();
            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
            String servletPath = request.getServletPath();
            String key = sessionId + "-" + servletPath;
            // 如果缓存中有这个url视为重复提交
            if (cache.getIfPresent(key) == null) {
                Map<String,Object> object = (Map<String,Object>)pjp.proceed();
                cache.put(key, 0);
                logger.info(object.toString());
                return AjaxResult.success(Integer.parseInt(object.get("code").toString()), object.get("msg").toString());
            } else {
                logger.error("接口:"+servletPath + ",重复提交");
                return AjaxResult.error(0, "重复提交");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("验证重复提交时出现未知异常!");
            return AjaxResult.error(0, "验证重复提交时出现未知异常");
        }

    }

}