package com.scs.top.project.framework.shiro;


import com.scs.top.project.common.util.BeanUtils;
import com.scs.top.project.common.util.StringUtils;
import com.scs.top.project.module.scs.pojo.SysUserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;


import java.util.HashMap;
import java.util.Map;

import static org.apache.shiro.SecurityUtils.getSubject;

/**
 * shiro 工具类
 *
 * @author admin
 */
public class ShiroUtils {

    public static Subject getSubjct() {
        return getSubject();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }

    public static void logout() {
        getSubjct().logout();
    }

    /**
     * 判断当前操作人是否是管理员
     * @author yihur
     * @date 2019/4/1
     * @return boolean
     */
    public static boolean isAdminister() {
        boolean flag = false;
        SysUserInfo user = getSysUser();
        if (user == null) {
            return false;
        }
        Integer dateSource = user.getCurrentRole().getDataScope();
        if (dateSource == 1) {
            flag = true;
        }
        return flag;
    }

    public static SysUserInfo getSysUser() {
        SysUserInfo user = null;
        Object obj = getSubject().getPrincipal();
        if (StringUtils.isNotNull(obj)) {
            user = new SysUserInfo();
            BeanUtils.copyBeanProp(user, obj);
        }
        return user;
    }

    public static Map<String,Object> getCurrentUserToMap(){
        Map<String,Object> map = new HashMap<>(10);
        SysUserInfo user = getSysUser();
//        map.put("userRoleDeptRegionList",user.getUserRoleDeptRegionList());
//        map.put("userAccount",user.getUserAccount());
//        map.put("userId",user.getUserId().toString());
//        map.put("userName",user.getUserName());
//        map.put("curDepts",user.getDepts());
//        map.put("curRole",user.getCurrentRole());
//        map.put("curRegions",user.getCurrentUserRegionList());
        return map;
    }



    public static void setSysUser(SysUserInfo user) {

        Subject subject = getSubjct();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载Principal
        subject.runAs(newPrincipalCollection);
    }

    public static void clearCachedAuthorizationInfo() {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
        realm.clearCachedAuthorizationInfo();
    }


    public static String getUserId() {
        return getSysUser().getSingleCode();
    }

    public static String getLoginName() {
        return getSysUser().getUserName();
    }

    public static String getIp() {
        return getSubjct().getSession().getHost();
    }

    public static String getSessionId() {
        return String.valueOf(getSubjct().getSession().getId());
    }
}
