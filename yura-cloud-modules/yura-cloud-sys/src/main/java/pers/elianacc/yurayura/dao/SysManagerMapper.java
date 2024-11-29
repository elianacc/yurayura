package pers.elianacc.yurayura.dao;

import pers.elianacc.yurayura.dto.SysManagerSelectDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;
import pers.elianacc.yurayura.entity.SysManager;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVO;

import java.util.List;

/**
 * 系统管理员 mapper interface
 *
 * @author ELiaNaCc
 * @since 2019-11-18
 */
public interface SysManagerMapper extends BaseMapper<SysManager> {

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return java.util.List<java.lang.String>
     */
    List<String> getManagerRolePermission(Integer managerId);

    /**
     * 批量添加管理员角色
     *
     * @param roleIdList
     * @param managerId
     * @return void
     */
    void insertBatchManagerRole(@Param("roleIdList") List<Integer> roleIdList, @Param("managerId") Integer managerId);

    /**
     * 删除管理员角色（根据管理员id）
     *
     * @param managerId
     * @return void
     */
    void deleteManagerRoleByManagerId(Integer managerId);

    /**
     * 查询管理员及其拥有角色（根据系统管理员查询dto）
     *
     * @param dto
     * @return java.util.List<pers.elianacc.yurayura.vo.SysManagerAndRoleVO>
     */
    List<SysManagerAndRoleVO> getManagerAndRoleListBySelectDTO(SysManagerSelectDTO dto);

    /**
     * 删除管理员角色（根据角色id）
     *
     * @param roleId
     * @return void
     */
    void deleteManagerRoleByRoleId(Integer roleId);

}
