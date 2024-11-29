package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysMenuInsertDTO;
import pers.elianacc.yurayura.dto.SysMenuUpdateDTO;
import pers.elianacc.yurayura.entity.SysMenu;
import pers.elianacc.yurayura.vo.SysMenuTreeVO;

import java.util.List;

/**
 * 系统菜单 service
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.vo.SysMenuTreeVO>
     */
    public List<SysMenuTreeVO> getTreeList();

    /**
     * 查询系统菜单树形列表（根据管理员id）
     *
     * @param managerId
     * @return java.util.List<pers.elianacc.yurayura.vo.SysMenuTreeVO>
     */
    public List<SysMenuTreeVO> getTreeListByManagerId(Integer managerId);

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return void
     */
    public void insert(SysMenuInsertDTO dto);

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return void
     */
    public void deleteById(IdDTO dto);

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return void
     */
    public void update(SysMenuUpdateDTO dto);
}
