package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.SysOrgMapper;
import pers.elianacc.yurayura.dto.SysOrgSelectDTO;
import pers.elianacc.yurayura.entity.SysOrg;
import pers.elianacc.yurayura.service.ISysOrgService;

import java.util.List;

/**
 * 系统组织 service impl
 *
 * @author ELiaNaCc
 * @since 2024-05-14
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements ISysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public PageInfo<SysOrg> getPage(SysOrgSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());

        List<SysOrg> sysOrgList = sysOrgMapper.selectList(Wrappers.<SysOrg>lambdaQuery()
                .apply(!ObjectUtils.isEmpty(dto.getOrgName())
                        , "instr(org_name, {0}) > 0", dto.getOrgName())
                .orderByDesc(SysOrg::getOrgCreateTime)
        );
        return new PageInfo<>(sysOrgList, 5);
    }



}
