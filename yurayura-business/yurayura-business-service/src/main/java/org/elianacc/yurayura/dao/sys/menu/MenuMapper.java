package org.elianacc.yurayura.dao.sys.menu;

import org.elianacc.yurayura.bo.MenuTreeSelectBo;
import org.elianacc.yurayura.entity.sys.menu.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 系统菜单 mapper interface
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    List<MenuTreeSelectBo> getTreeList();

    /**
     * 查询系统菜单列表（当前管理员有权限的）
     *
     * @param managerId
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    List<MenuTreeSelectBo> getTreeListForCurrentManager(Integer managerId);

    /**
     * 查询系统菜单，子菜单标识
     *
     * @param
     * @return java.util.List<java.lang.String>
     */
    List<String> getMenuNameAndMenuSubName();
}
