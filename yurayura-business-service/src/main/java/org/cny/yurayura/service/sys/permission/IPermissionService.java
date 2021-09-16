package org.cny.yurayura.service.sys.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.bo.PermissionAuthorTreeSelectBo;
import org.cny.yurayura.dto.PermissionSelectDto;
import org.cny.yurayura.entity.sys.permission.Permission;

import java.util.List;

/**
 * 系统权限 service
 *
 * @author CNY
 * @since 2021-08-05
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.sys.permission.Permission>
     */
    public PageInfo<Permission> getPage(PermissionSelectDto dto);

    /**
     * 添加系统权限
     *
     * @param permission
	 * @param permissionBtnVal
     * @return java.lang.String
     */
    public String insert(Permission permission, String permissionBtnVal);

    /**
     * 修改系统权限
     *
     * @param permission
	 * @param permissionBtnVal
     * @return java.lang.String
     */
    public String update(Permission permission, String permissionBtnVal);

    /**
     * 查询权限授权树
     *
     * @param
     * @return java.util.List<org.cny.yurayura.bo.PermissionAuthorTreeSelectBo>
     */
    public List<PermissionAuthorTreeSelectBo> getPermissionAuthorTree();

}