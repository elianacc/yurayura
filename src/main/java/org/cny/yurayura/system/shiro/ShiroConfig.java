package org.cny.yurayura.system.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro config
 *
 * @author CNY
 * @since 2020-12-02
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean（shiro过滤器工厂）放入bean中
     *
     * @param securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        /*
         *  添加shiro内置过滤器
         *  anon: 无需认证即可访问
         *  authc: 必须认证才可访问
         *  user: 必须拥有记住我功能才可访问
         *  perms: 必须拥有某个权限才可访问
         *  role: 必须拥有某个角色才可访问
         * */
        Map<String, String> fillterMap = new LinkedHashMap<>();

        fillterMap.put("/business/business_index", "authc");
        fillterMap.put("/business/sys_dict", "authc");
        fillterMap.put("/business/sys_manager", "authc");
        fillterMap.put("/business/comic_info", "authc");

        fillterMap.put("/comic/get*", "perms[select]");
        fillterMap.put("/comic/insert*", "perms[insert]");
        fillterMap.put("/comic/delete*", "perms[delete]");
        fillterMap.put("/comic/update*", "perms[update]");
        fillterMap.put("/sys/dict/getByDictCode", "perms[select]");
        fillterMap.put("/sys/dict/**", "perms[sys]");
        fillterMap.put("/sys/manager/getVerifyCode", "anon");
        fillterMap.put("/sys/manager/login", "anon");
        fillterMap.put("/sys/manager/logout", "anon");
        fillterMap.put("/sys/manager/**", "perms[sys]");

        filterFactoryBean.setFilterChainDefinitionMap(fillterMap);
        filterFactoryBean.setLoginUrl("/business/manager_unlogin");

        return filterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager（默认安全管理器）放入bean中
     *
     * @param userRealm
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 把ManagerRealm放入DefaultWebSecurityManager
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建自定义的Realm对象放入bean中
     *
     * @param
     * @return org.cny.yurayura.system.shiro.UserRealm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 创建ShiroDialect放入bean中（整合thymeleaf shiro）
     *
     * @param
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
