package org.cny.yurayura.system.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.cny.yurayura.dao.sys.manager.ManagerMapper;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.enumerate.ManagerStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 自定义shiro Realm
 *
 * @author CNY
 * @since 2020-12-02
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 用户授权
     *
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        // 当前用户未认证，清空授权信息
        if (!subject.isAuthenticated()) {
            doClearCache(principalCollection);
            subject.logout();
            return null;
        }

        Manager currentManager = (Manager) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(currentManager.getManagerPermission());

        return info;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        Manager manager = managerMapper.selectOne(queryWrapper
                .eq("manager_name", userToken.getUsername())
                .eq("manager_status", ManagerStatusEnum.ENABLE.getStatusId()));
        // 判断用户是否存在
        if (StringUtils.isEmpty(manager)) {
            return null;
        }

        // 密码认证 shiro来处理
        return new SimpleAuthenticationInfo(manager, manager.getManagerPassword(), getName());
    }
}
