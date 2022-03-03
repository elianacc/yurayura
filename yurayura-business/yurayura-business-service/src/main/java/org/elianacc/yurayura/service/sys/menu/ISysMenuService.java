package org.elianacc.yurayura.service.sys.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import org.elianacc.yurayura.bo.SysMenuTreeSelectBo;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysMenuInsertDto;
import org.elianacc.yurayura.dto.SysMenuUpdateDto;
import org.elianacc.yurayura.entity.sys.menu.SysMenu;

import java.util.List;

/**
 * 系统菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询系统菜单树形列表（当前管理员有权限的）
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    public List<SysMenuTreeSelectBo> getTreeListForCurrentManager();

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.bo.MenuBo>
     */
    public List<SysMenuTreeSelectBo> getTreeList();

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysMenuInsertDto dto);

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return void
     */
    public void deleteById(IdDto dto);

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysMenuUpdateDto dto);
}
