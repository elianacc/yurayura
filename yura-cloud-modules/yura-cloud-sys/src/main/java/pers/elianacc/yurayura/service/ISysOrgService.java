package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.SysOrgSelectDTO;
import pers.elianacc.yurayura.entity.SysOrg;

/**
 * 系统组织 service
 *
 * @author ELiaNaCc
 * @since 2024-05-14
 */
public interface ISysOrgService extends IService<SysOrg> {

    /**
     * 分页查询系统组织
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.sys.org.SysOrg>
     */
    public PageInfo<SysOrg> getPage(SysOrgSelectDTO dto);

}
