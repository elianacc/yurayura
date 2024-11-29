package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDTO;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDTO;
import pers.elianacc.yurayura.entity.SysMenuSub;

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
     * @return void
     */
    public void insert(SysMenuSubInsertDTO dto);

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return void
     */
    public void deleteById(IdDTO dto);

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return void
     */
    public void update(SysMenuSubUpdateDTO dto);

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return pers.elianacc.yurayura.entity.sys.menu.SysMenuSub
     */
    public SysMenuSub getByIndex(String index);

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    public List<SysMenuSub> getAll();
}
