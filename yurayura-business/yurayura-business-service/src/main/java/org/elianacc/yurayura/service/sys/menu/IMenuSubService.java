package org.elianacc.yurayura.service.sys.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elianacc.yurayura.entity.sys.menu.MenuSub;

import java.util.List;

/**
 * 系统子菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface IMenuSubService extends IService<MenuSub> {

    /**
     * 添加系统子菜单
     *
     * @param menuSub
     * @return java.lang.String
     */
    public String insert(MenuSub menuSub);

    /**
     * 删除系统子菜单（根据id）
     *
     * @param id
     * @return void
     */
    public void deleteById(Integer id);

    /**
     * 修改系统子菜单
     *
     * @param menuSub
     * @return java.lang.String
     */
    public String update(MenuSub menuSub);

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return org.elianacc.yurayura.entity.sys.menu.MenuSub
     */
    public MenuSub getByIndex(String index);

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.entity.sys.menu.MenuSub>
     */
    public List<MenuSub> getAll();
}
