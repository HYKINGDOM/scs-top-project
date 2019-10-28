package com.scs.top.project.framework.shiro;

import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ShiroInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRealm userRealm;

    @Resource
    private RealmSecurityManager securityManager;

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        if (this.userRealm != null && this.securityManager != null) {
            securityManager.setRealm(userRealm);
        }
    }


    @EventListener
    public void whenContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)context.getBean("securityManager");
        UserRealm shiroRealm = (UserRealm)context.getBean("userRealm");
        securityManager.setRealm(shiroRealm);

        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = (AuthorizationAttributeSourceAdvisor)context.getBean("authorizationAttributeSourceAdvisor");
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);

        AbstractShiroFilter shiroFilter = (AbstractShiroFilter)context.getBean("shiroFilter");
        shiroFilter.setSecurityManager(securityManager);
    }
    



}

