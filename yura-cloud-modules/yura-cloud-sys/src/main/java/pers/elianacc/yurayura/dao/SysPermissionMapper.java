package pers.elianacc.yurayura.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.elianacc.yurayura.entity.SysPermission;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO;

import java.util.List;

/**
 * 系统权限 mapper interface
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 查询权限授权树
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO>
     */
    List<SysPermissionAuthorTreeVO> getPermissionAuthorTree();

}
