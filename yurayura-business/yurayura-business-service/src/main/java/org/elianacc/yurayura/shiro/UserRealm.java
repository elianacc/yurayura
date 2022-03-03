package org.elianacc.yurayura.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.elianacc.yurayura.dao.sys.manager.SysManagerMapper;
import org.elianacc.yurayura.entity.sys.manager.SysManager;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 自定义shiro Realm
 *
 * @author ELiaNaCc
 * @since 2020-12-02
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysManagerMapper sysManagerMapper;

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

        SysManager currentSysManager = (SysManager) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addStringPermission(sysManagerMapper.getCurrentManagerPermission(currentSysManager.getId()));

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
        QueryWrapper<SysManager> queryWrapper = new QueryWrapper<>();
        SysManager sysManager = sysManagerMapper.selectOne(queryWrapper
                .eq("manager_name", userToken.getUsername())
                .eq("manager_status", EnableStatusEnum.ENABLE.getStatusId()));
        // 判断用户是否存在
        if (ObjectUtils.isEmpty(sysManager)) {
            return null;
        }

        // 密码认证 shiro来处理
        return new SimpleAuthenticationInfo(sysManager, sysManager.getManagerPassword(), getName());
    }
}
