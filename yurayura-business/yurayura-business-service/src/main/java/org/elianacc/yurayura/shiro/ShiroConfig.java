package org.elianacc.yurayura.shiro;

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
 * @author ELiaNaCc
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

        fillterMap.put("/api/sys/menu/getSysSideMenu", "authc");
        fillterMap.put("/api/sys/menu/get*", "perms[sys_menu_select]");
        fillterMap.put("/api/sys/menu/insert*", "perms[sys_menu_insert]");
        fillterMap.put("/api/sys/menu/update*", "perms[sys_menu_update]");
        fillterMap.put("/api/sys/menu/delete*", "perms[sys_menu_delete]");

        fillterMap.put("/api/sys/menuSub/getByIndex", "authc");
        fillterMap.put("/api/sys/menuSub/getAll", "authc");
        fillterMap.put("/api/sys/menuSub/get*", "perms[sys_menu_select]");
        fillterMap.put("/api/sys/menuSub/insert*", "perms[sys_menu_insert]");
        fillterMap.put("/api/sys/menuSub/update*", "perms[sys_menu_update]");
        fillterMap.put("/api/sys/menuSub/delete*", "perms[sys_menu_delete]");

        fillterMap.put("/api/sys/dict/getAll", "authc");
        fillterMap.put("/api/sys/dict/getByDictCode", "authc");
        fillterMap.put("/api/sys/dict/get*", "perms[sys_dict_select]");
        fillterMap.put("/api/sys/dict/insert*", "perms[sys_dict_insert]");
        fillterMap.put("/api/sys/dict/update*", "perms[sys_dict_update]");

        fillterMap.put("/api/sys/manager/getVerifyCode", "anon");
        fillterMap.put("/api/sys/manager/login", "anon");
        fillterMap.put("/api/sys/manager/logout", "anon");
        fillterMap.put("/api/sys/manager/judgeAuthen", "anon");
        fillterMap.put("/api/sys/manager/notAuthentication", "anon");
        fillterMap.put("/api/sys/manager/notAuthorization", "anon");
        fillterMap.put("/api/sys/manager/getCurrentManagerMsg", "authc");
        fillterMap.put("/api/sys/manager/get*", "perms[sys_manager_select]");
        fillterMap.put("/api/sys/manager/insert*", "perms[sys_manager_insert]");
        fillterMap.put("/api/sys/manager/update*", "perms[sys_manager_update]");

        fillterMap.put("/api/sys/permission/getPermissionAuthorTree", "authc");
        fillterMap.put("/api/sys/permission/get*", "perms[sys_permission_select]");
        fillterMap.put("/api/sys/permission/insert*", "perms[sys_permission_insert]");
        fillterMap.put("/api/sys/permission/update*", "perms[sys_permission_update]");

        fillterMap.put("/api/comic/get*", "perms[comic_info_select]");
        fillterMap.put("/api/comic/insert*", "perms[comic_info_insert]");
        fillterMap.put("/api/comic/update*", "perms[comic_info_update]");
        fillterMap.put("/api/comic/deleteBatchByIds", "perms[comic_info_deleteBatch]");

        fillterMap.put("/api/user/get*", "perms[user_info_select]");
        fillterMap.put("/api/user/update*", "perms[user_info_update]");

        filterFactoryBean.setFilterChainDefinitionMap(fillterMap);
        filterFactoryBean.setLoginUrl("/api/sys/manager/notAuthentication");
        filterFactoryBean.setUnauthorizedUrl("/api/sys/manager/notAuthorization");

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
     * @return org.elianacc.yurayura.system.shiro.UserRealm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

}
