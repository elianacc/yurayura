package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysPermissionInsertDTO;
import pers.elianacc.yurayura.dto.SysPermissionSelectDTO;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDTO;
import pers.elianacc.yurayura.entity.SysPermission;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO;

import java.util.List;

/**
 * 系统权限 service
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.permission.SysPermission>
     */
    public PageInfo<SysPermission> getPage(SysPermissionSelectDTO dto);

    /**
     * 添加系统权限
     *
     * @param dto
     * @return void
     */
    public void insert(SysPermissionInsertDTO dto);

    /**
     * 修改系统权限
     *
     * @param dto
     * @return void
     */
    public void update(SysPermissionUpdateDTO dto);

    /**
     * 查询权限授权树
     *
     * @param
     * @return java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO>
     */
    public List<SysPermissionAuthorTreeVO> getPermissionAuthorTree();

}
