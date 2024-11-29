package pers.elianacc.yurayura.dao;

import pers.elianacc.yurayura.dto.SysRoleSelectDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import pers.elianacc.yurayura.entity.SysRole;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVO;

import java.util.List;

/**
 * 系统角色 mapper interface
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询角色及其拥有权限（根据系统角色查询dto）
     *
     * @param dto
     * @return java.util.List<pers.elianacc.yurayura.vo.SysRoleAndPermissionVO>
     */
    List<SysRoleAndPermissionVO> getRoleAndPermissionListBySelectDTO(SysRoleSelectDTO dto);

    /**
     * 删除角色权限（根据权限id）
     *
     * @param permissionId
     * @return void
     */
    void deleteRolePermissionByPermissionId(Integer permissionId);

    /**
     * 添加超级管理员的角色权限
     *
     * @param permissionId
     * @return void
     */
    void insertRolePermissionForAdmin(Integer permissionId);

    /**
     * 批量添加角色权限
     *
     * @param permissionIdList
	 * @param roleId
     * @return void
     */
    void insertBatchRolePermission(@Param("permissionIdList") List<Integer> permissionIdList, @Param("roleId") Integer roleId);

    /**
     * 删除角色权限（根据角色id）
     *
     * @param roleId
     * @return void
     */
    void deleteRolePermissionByRoleId(Integer roleId);

}
