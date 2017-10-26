package me.weix.fairy.config;

import me.weix.fairy.config.shiro.authc.DefaultModularRealm;
import me.weix.fairy.config.shiro.realm.EmailRealm;
import me.weix.fairy.config.shiro.realm.MobileRealm;
import me.weix.fairy.config.shiro.realm.UsernameRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.glassfish.hk2.api.UseProxy;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "anon");                      //表示可以匿名访问
        filterChainDefinitionMap.put("/api/swagger.json", "anon");
        filterChainDefinitionMap.put("*", "anon");

//        filterChainDefinitionMap.put("/logout*", "anon");
//        filterChainDefinitionMap.put("/jsp/error.html*", "anon");
//        filterChainDefinitionMap.put("/jsp/index.html*", "authc");
//        filterChainDefinitionMap.put(" ", "authc");//表示需要认证才可以访问
//        filterChainDefinitionMap.put("*", "authc");//表示需要认证才可以访问
//        filterChainDefinitionMap.put(".*", "authc");
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
    public DefaultModularRealm defaultModularRealm(@Qualifier("usernameRealm") UsernameRealm usernameRealm,
                                                   @Qualifier("emailRealm") EmailRealm emailRealm,
                                                   @Qualifier("mobileRealm") MobileRealm mobileRealm) {
        DefaultModularRealm defaultModularRealm = new DefaultModularRealm();
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
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("usernameRealm") UsernameRealm usernameRealm,
                                                     @Qualifier("emailRealm") EmailRealm emailRealm,
                                                     @Qualifier("mobileRealm") MobileRealm mobileRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        List<Realm> realms = new ArrayList<>();
        realms.add(usernameRealm);
        realms.add(emailRealm);
        realms.add(mobileRealm);
        securityManager.setRealms(realms);
        return securityManager;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        //System.out.println("ShiroConfiguration.getEhCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        manager.setCacheManager(ehCacheManager());
        return advisor;
    }
}
