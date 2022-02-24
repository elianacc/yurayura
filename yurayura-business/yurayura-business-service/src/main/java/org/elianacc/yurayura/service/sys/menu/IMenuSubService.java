package org.elianacc.yurayura.service.sys.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.MenuSubInsertDto;
import org.elianacc.yurayura.dto.MenuSubUpdateDto;
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
     * @param dto
     * @return java.lang.String
     */
    public String insert(MenuSubInsertDto dto);

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return void
     */
    public void deleteById(IdDto dto);

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(MenuSubUpdateDto dto);

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
