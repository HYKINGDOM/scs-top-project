package com.scs.top.project.framework.shiro.kickoutfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scs.top.project.common.util.PropertyUtil;
import com.scs.top.project.module.scs.pojo.SysUserInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

import static com.scs.top.project.common.util.IpUtils.getIpAddr;
import static com.scs.top.project.framework.shiro.ShiroUtils.*;

/**
 * 自定义过滤器，进行用户访问控制
 *
 * @author admin
 */
public class KickoutSessionFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionFilter.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;
    /**
     * 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = Integer.parseInt(PropertyUtil.getProperty("maxOnLineSession").trim());

    private SessionManager sessionManager;

    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * 设置Cache的key的前缀
     *
     * @param cacheManager
     */
    public void setCacheManager(CacheManager cacheManager) {
        //必须和ehcache缓存配置中的缓存name一致
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response){
        Subject subject = getSubjct();
        // 没有登录授权 且没有记住我
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            //判断是不是Ajax请求，异步请求，直接响应返回未登录
            ResponseResult responseResult = new ResponseResult();
            if (ShiroFilterUtils.isAjax(request)) {
                logger.info(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
                responseResult.setCode(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
                responseResult.setMessage("您已在别处登录，请您修改密码或重新登录");
                out(response, responseResult);
                return false;
            } else {
                return true;
            }
        }
        // 获得用户请求的URI
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        logger.info("===当前请求的uri：==" + path);
        logger.info("===当前请求的域名或ip+端口：==" + getIpAddr(req));
        //放行登录
        if (("/system/login").equals(path)) {
            return true;
        }
        Session session = getSession();
        logger.info("==session时间设置：" + (session.getTimeout()) + "===========");
        try {
            // 当前用户
            SysUserInfo user = getSysUser();
            String userAccount = user.getUserName();
            logger.info("===当前用户username：==" + userAccount);
            Serializable sessionId = session.getId();
            logger.info("===当前用户sessionId：==" + sessionId);
            // 读取缓存用户 没有就存入
            Deque<Serializable> deque = cache.get(userAccount);
            logger.info("===当前队列：==" + deque);
            if (deque == null) {
                // 初始化队列
                deque = new ArrayDeque<>();
            }
            // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
                // 将sessionId存入队列
                deque.push(sessionId);
                // 将用户的sessionId队列缓存
                cache.put(userAccount, deque);
            }
            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                logger.info("===deque队列长度：==" + deque.size());
                Serializable kickoutSessionId = null;
                // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
                // 如果踢出后者
                if (kickoutAfter) {
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                logger.info("===当前该账户的在线用户数量已更新：==" + userAccount);
                // 踢出后再更新下缓存队列
                cache.put(userAccount, deque);
                try {
                    // 获取被踢出的sessionId的session对象
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if (kickoutSession != null) {
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setAttribute("kickout", true);
                    }
                } catch (Exception e) {
                    logger.info("===kickoutSession异常==" + e.getMessage());
                }
            }
            // 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
            if (session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout")) {
                // 会话被踢出了
                try {
                    // 退出登录 调用shiroUtils中的方法
                    logout();
                } catch (Exception e) {
                    logger.info("===退出登录引发异常===" + e.getMessage());
                }
                saveRequest(request);
                logger.info("==踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
                // ajax请求  重定向
                return isAjaxResponse(request, response);
            }
            return true;
        } catch (Exception e) {
            logger.error("控制用户在线数量【lyd-admin-->KickoutSessionFilter.onAccessDenied】异常！", e.getMessage());
            // 重启后，ajax请求，报错：java.lang.ClassCastException:   -----处理 ajax请求
            return isAjaxResponse(request, response);
        }
    }

    /**
     * response输出json
     *
     * @param response
     * @param result
     */
    public static void out(ServletResponse response, ResponseResult result) {
        PrintWriter out = null;
        try {
            //设置编码
            response.setCharacterEncoding("UTF-8");
            //设置返回类型
            response.setContentType("application/json");
            out = response.getWriter();
            //输出
            out.println(OBJECT_MAPPER.writeValueAsString(result));
            logger.info("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息成功");
        } catch (Exception e) {
            logger.info("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息出错", e.getMessage());
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 判断是否已经踢出
     * 1.如果是Ajax 访问，那么给予json返回值提示。
     * 2.如果是普通请求，直接跳转到登录页
     */
    private boolean isAjaxResponse(ServletRequest request, ServletResponse response){
        //判断是不是Ajax请求
        ResponseResult responseResult = new ResponseResult();
        if (ShiroFilterUtils.isAjax(request)) {
            logger.info(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
            responseResult.setCode(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
            responseResult.setMessage("Ajax校验您已在别处登录，请您修改密码或重新登录");
            out(response, responseResult);
        } else {
            // 重定向
            try {
                WebUtils.issueRedirect(request, response, kickoutUrl);
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("===重定向引发异常===" + e.getMessage());
            }
        }
        return false;
    }

}
