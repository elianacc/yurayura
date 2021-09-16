package org.cny.yurayura.dao.sys.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.cny.yurayura.bo.PermissionAuthorTreeSelectBo;
import org.cny.yurayura.entity.sys.permission.Permission;

import java.util.List;

/**
 * 系统权限 mapper interface
 *
 * @author CNY
 * @since 2021-08-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询权限授权树
     *
     * @param
     * @return java.util.List<org.cny.yurayura.bo.PermissionAuthorTreeSelectBo>
     */
    List<PermissionAuthorTreeSelectBo> getPermissionAuthorTree();

}
