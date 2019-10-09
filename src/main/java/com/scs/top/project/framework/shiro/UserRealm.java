package com.scs.top.project.framework.shiro;

import com.isoftstone.pmit.common.util.DateUtils;
import com.isoftstone.pmit.common.util.StringUtils;
import com.isoftstone.pmit.system.menu.service.MenuService;
import com.isoftstone.pmit.system.role.service.IRoleService;
import com.isoftstone.pmit.system.user.entity.User;
import com.isoftstone.pmit.system.user.service.IUserService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.isoftstone.pmit.framework.shiro.ShiroUtils.isAdminister;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author admin
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private IRoleService IRoleService;

    @Autowired
    private IUserService IUserService;

    /**
     * ipsa登录的接口
     */
    public static final String IPSA_URL = "https://passport.isoftstone.com/?DomainUrl=http://ipsapro.isoftstone.com&Ret&ReturnUrl=%2fPortal#";
    /**
     * 登录成功后返回的地址
     */
    public static final String IPSA_RETURN_URL = "http://ipsapro.isoftstone.com/Portal";

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 判断是否是管理员
        if (isAdminister()) {
            simpleAuthorizationInfo.addRole("admin");
            simpleAuthorizationInfo.addStringPermission("*:*:*");
        } else {
            // 角色加入AuthorizationInfo认证对象
            simpleAuthorizationInfo.setRoles(IRoleService.selectRoleKeys());
            // 权限加入AuthorizationInfo认证对象
            simpleAuthorizationInfo.setStringPermissions(menuService.selectPermsByUserId());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userAccount = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

//        //IPSA登陆地址
//        HttpPost postLogin = new HttpPost(IPSA_URL);
//        //IPSA登陆信息设置
//        List<NameValuePair> formParams = new ArrayList<>();
//        formParams.add(new BasicNameValuePair("emp_DomainName", userAccount));
//        formParams.add(new BasicNameValuePair("emp_Password", password));
//        HttpEntity entity = null;
//        try {
//            entity = new UrlEncodedFormEntity(formParams, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        postLogin.setEntity(entity);
//        //定义接收的Cookie
//        BasicCookieStore basicCookieStore = new BasicCookieStore();
//        //模拟登录ipsa
//        CloseableHttpClient loginClient = HttpClients.custom().setDefaultCookieStore(basicCookieStore).build();
//        //调用IPSA登陆接口
//        CloseableHttpResponse execute = null;
//        try {
//            execute = loginClient.execute(postLogin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Header firstHeader = null;
//        if (execute != null) {
//            firstHeader = execute.getFirstHeader("Location");
//        } else {
//            throw new RuntimeException("IPSA请求异常,请检查链接");
//        }
//        User user = null;
//        if (null != firstHeader && firstHeader.getValue().equals(IPSA_RETURN_URL)) {
//            // 查询用户信息
//            user = IUserService.selectUserByLoginName(userAccount);
//            if (!StringUtils.isNotNull(user)) {
//                //用户不存在,将从pmoit.ipsaInfo 表中获取数据
//                user = IUserService.selectPmoitUserByLoginName(userAccount);
//                //throw new UnknownAccountException("用户账户没有权限");
//            } else {
//                recordLoginInfo(user);
//            }
//        } else {
//            //账号密码错误
//            throw new IncorrectCredentialsException("账号或密码不正确");
//        }


        User user = null;
            // 查询用户信息
            user = IUserService.selectUserByLoginName(userAccount);
            if (!StringUtils.isNotNull(user)) {
                //用户不存在,将从pmoit.ipsaInfo 表中获取数据
                user = IUserService.selectPmoitUserByLoginName(userAccount);
                //throw new UnknownAccountException("用户账户没有权限");
            }

        return new SimpleAuthenticationInfo(user.getUserAccount(), password, user.getUserName());
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }


    /**
     * 记录登录信息
     */
    private void recordLoginInfo(User user) {
        user.setLoginIp(ShiroUtils.getIp());
        user.setLoginDate(DateUtils.getNowDate());
        user.setUpdateBy(user.getUserAccount());
        IUserService.updateUser(user);
    }

}
