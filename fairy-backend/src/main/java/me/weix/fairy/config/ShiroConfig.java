
package me.weix.fairy.config;

import me.weix.fairy.config.shiro.authc.DefaultModularRealmAuthenticator;
import me.weix.fairy.config.shiro.realm.EmailRealm;
import me.weix.fairy.config.shiro.realm.MobileRealm;
import me.weix.fairy.config.shiro.realm.UsernameRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


/*
 * @Author: WeiX
 * @Date: 2017/10/23
 * @description :
 */


@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置自定义的权限登录器
    @Bean(name = "usernameRealm")
    public UsernameRealm usernameRealm() {
        UsernameRealm authRealm = new UsernameRealm();
        return authRealm;
    }

    @Bean(name = "emailRealm")
    public EmailRealm emailRealm() {
        EmailRealm authRealm = new EmailRealm();
        return authRealm;
    }

    @Bean(name = "mobileRealm")
    public MobileRealm mobileRealm() {
        MobileRealm authRealm = new MobileRealm();
        return authRealm;
    }

    @Bean(name = "defaultModularRealm")
    public DefaultModularRealmAuthenticator defaultModularRealm(@Qualifier("usernameRealm") UsernameRealm usernameRealm,
                                                                @Qualifier("emailRealm") EmailRealm emailRealm,
                                                                @Qualifier("mobileRealm") MobileRealm mobileRealm) {
        DefaultModularRealmAuthenticator defaultModularRealm = new DefaultModularRealmAuthenticator();
        Map<String, Object> definedRealms = new HashMap<>();
        definedRealms.put("usernameRealm", usernameRealm);
        definedRealms.put("emailRealm", emailRealm);
        definedRealms.put("mobileRealm", mobileRealm);
        defaultModularRealm.setDefinedRealms(definedRealms);
        return defaultModularRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("usernameRealm") UsernameRealm usernameRealm,
                                                     @Qualifier("emailRealm") EmailRealm emailRealm,
                                                     @Qualifier("mobileRealm") MobileRealm mobileRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        List<Realm> realms = new ArrayList<>();
        realms.add(usernameRealm);
        realms.add(emailRealm);
        realms.add(mobileRealm);
        securityManager.setRealms(realms);
        securityManager.setAuthenticator(defaultModularRealm(usernameRealm,emailRealm,mobileRealm));
        return securityManager;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}

