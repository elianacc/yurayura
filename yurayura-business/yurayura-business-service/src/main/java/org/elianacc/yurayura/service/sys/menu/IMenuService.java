package org.elianacc.yurayura.service.sys.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elianacc.yurayura.bo.MenuTreeSelectBo;
import org.elianacc.yurayura.entity.sys.menu.Menu;

import java.util.List;

/**
 * 系统菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询系统菜单树形列表（当前管理员有权限的）
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    public List<MenuTreeSelectBo> getTreeListForCurrentManager();

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    public List<MenuTreeSelectBo> getTreeList();

    /**
     * 添加系统菜单
     *
     * @param menu
     * @return java.lang.String
     */
    public String insert(Menu menu);

    /**
     * 删除系统菜单（根据id）
     *
     * @param id
     * @return void
     */
    public void deleteById(Integer id);

    /**
     * 修改系统菜单
     *
     * @param menu
     * @return java.lang.String
     */
    public String update(Menu menu);
}
