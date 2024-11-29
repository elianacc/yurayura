package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.dto.SysManagerInsertDTO;
import pers.elianacc.yurayura.dto.SysManagerSelectDTO;
import pers.elianacc.yurayura.dto.SysManagerUpdateDTO;
import pers.elianacc.yurayura.entity.SysManager;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVO;

import java.util.List;


/**
 * 系统管理员 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface ISysManagerService extends IService<SysManager> {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysManagerAndRoleVO>
     */
    public PageInfo<SysManagerAndRoleVO> getPage(SysManagerSelectDTO dto);

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return void
     */
    public void insert(SysManagerInsertDTO dto);

    /**
     * 批量删除系统管理员（根据系统管理员id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDTO dto);

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return void
     */
    public void update(SysManagerUpdateDTO dto);

    /**
     * 查询管理员拥有角色的所有权限（根据管理员id）
     *
     * @param managerId
     * @return List<String>
     */
    public List<String> getManagerRolePermission(Integer managerId);

    /**
     * 查询启用的管理员（根据管理员名）
     *
     * @param managerName
     * @return pers.elianacc.yurayura.entity.sys.manager.SysManager
     */
    public SysManager getEnableManagerByName(String managerName);

}
