package pers.elianacc.yurayura.dao;

import pers.elianacc.yurayura.entity.SysMenu;
import pers.elianacc.yurayura.vo.SysMenuTreeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统菜单 mapper interface
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.vo.SysMenuTreeVO>
     */
    List<SysMenuTreeVO> getTreeList();

    /**
     * 查询系统菜单树形列表（根据管理员id）
     *
     * @param managerId
     * @return java.util.List<pers.elianacc.yurayura.vo.SysMenuTreeVO>
     */
    List<SysMenuTreeVO> getTreeListByManagerId(Integer managerId);

    /**
     * 查询系统菜单，子菜单标识
     *
     * @param
     * @return java.util.List<java.lang.String>
     */
    List<String> getMenuNameAndMenuSubName();
}
