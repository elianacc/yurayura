package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysRoleInsertDTO;
import pers.elianacc.yurayura.dto.SysRoleSelectDTO;
import pers.elianacc.yurayura.dto.SysRoleUpdateDTO;
import pers.elianacc.yurayura.entity.SysRole;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVO;

import java.util.List;

/**
 * 系统角色 service
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVO>
     */
    public PageInfo<SysRoleAndPermissionVO> getPage(SysRoleSelectDTO dto);

    /**
     * 查询系统角色（根据组织）
     *
     * @param orgId
     * @return List<SysRole>
     */
    public List<SysRole> getByOrg(Integer orgId);

    /**
     * 添加系统角色
     *
     * @param dto
     * @return void
     */
    public void insert(SysRoleInsertDTO dto);

    /**
     * 修改系统角色
     *
     * @param dto
     * @return void
     */
    public void update(SysRoleUpdateDTO dto);

}
