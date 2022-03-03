package org.elianacc.yurayura.service.sys.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysMenuSubInsertDto;
import org.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import org.elianacc.yurayura.entity.sys.menu.SysMenuSub;

import java.util.List;

/**
 * 系统子菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface ISysMenuSubService extends IService<SysMenuSub> {

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysMenuSubInsertDto dto);

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
    public String update(SysMenuSubUpdateDto dto);

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return org.elianacc.yurayura.entity.sys.menu.SysMenuSub
     */
    public SysMenuSub getByIndex(String index);

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    public List<SysMenuSub> getAll();
}
