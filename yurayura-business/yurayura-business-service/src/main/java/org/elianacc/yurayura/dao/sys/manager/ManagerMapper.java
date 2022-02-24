package org.elianacc.yurayura.dao.sys.manager;

import org.elianacc.yurayura.dto.ManagerSelectDto;
import org.elianacc.yurayura.entity.sys.manager.Manager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统管理员 mapper interface
 *
 * @author ELiaNaCc
 * @since 2019-11-18
 */
public interface ManagerMapper extends BaseMapper<Manager> {

    /**
     * 查询当前登入管理员的所有权限
     *
     * @param managerId
     * @return java.lang.String
     */
    String getCurrentManagerPermission(Integer managerId);

    /**
     * 批量添加管理员权限
     *
     * @param permissionIdList
     * @param managerId
     * @return void
     */
    void insertBatchManagerPermission(@Param("permissionIdList") List<Integer> permissionIdList, @Param("managerId") Integer managerId);

    /**
     * 删除管理员权限（根据管理员id）
     *
     * @param managerId
     * @return void
     */
    void deleteManagerPermissionByManagerId(Integer managerId);

    /**
     * 查询管理员及其权限（根据系统管理员查询dto）
     *
     * @param dto
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String, Object>> getManagerAndPermissionListBySelectDto(ManagerSelectDto dto);

    /**
     * 删除管理员权限（根据权限id）
     *
     * @param permissionId
     * @return void
     */
    void deleteManagerPermissionByPermissionId(Integer permissionId);

    /**
     * 添加管理员admin权限
     *
     * @param permissionId
     * @return void
     */
    void insertManagerPermissionForAdmin(Integer permissionId);
}
