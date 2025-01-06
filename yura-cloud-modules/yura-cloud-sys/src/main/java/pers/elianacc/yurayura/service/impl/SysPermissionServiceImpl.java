package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.SysPermissionInsertDTO;
import pers.elianacc.yurayura.dto.SysPermissionSelectDTO;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDTO;
import pers.elianacc.yurayura.entity.SysPermission;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.enumerate.SysPermissionTypeEnum;
import pers.elianacc.yurayura.service.ISysPermissionService;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO;

import java.util.List;

/**
 * 系统权限 service impl
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public PageInfo<SysPermission> getPage(SysPermissionSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysPermission> sysPermissionList = sysPermissionMapper
                .selectList(Wrappers.<SysPermission>lambdaQuery()
                        .apply(!ObjectUtils.isEmpty(dto.getPermissionCode())
                                , "instr(permission_code, {0}) > 0", dto.getPermissionCode())
                        .eq(!ObjectUtils.isEmpty(dto.getPermissionType())
                                , SysPermission::getPermissionType, dto.getPermissionType())
                        .eq(!ObjectUtils.isEmpty(dto.getPermissionStatus())
                                , SysPermission::getPermissionStatus, dto.getPermissionStatus())
                        .eq(!ObjectUtils.isEmpty(dto.getPermissionBelongSubmenuName())
                                , SysPermission::getPermissionBelongSubmenuName, dto.getPermissionBelongSubmenuName())
                        .orderByAsc(SysPermission::getPermissionBelongSubmenuName, SysPermission::getPermissionSeq)
                );
        return new PageInfo<>(sysPermissionList, 5);
    }

    @Override
    public void insert(SysPermissionInsertDTO dto) {
        Assert.isTrue(!(ObjectUtils.isEmpty(dto.getPermissionBtnVal())
                        && dto.getPermissionType() == SysPermissionTypeEnum.BUTTON.getTypeId().intValue())
                , "权限类型为按钮时权限按钮不能为空");
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        if (ObjectUtils.isEmpty(dto.getPermissionBtnVal())) {
            sysPermission.setPermissionCode(sysPermission.getPermissionBelongSubmenuName() + "_select");
        } else {
            sysPermission.setPermissionCode(sysPermission
                    .getPermissionBelongSubmenuName() + "_" + dto.getPermissionBtnVal());
        }
        List<SysPermission> existPermList = sysPermissionMapper.selectList(Wrappers.<SysPermission>lambdaQuery()
                .eq(SysPermission::getPermissionCode, sysPermission.getPermissionCode()));
        Assert.isTrue(existPermList.isEmpty(), "此权限已经存在");
        sysPermissionMapper.insert(sysPermission);
        if (sysPermission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
            sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
        }
    }

    @Override
    public void update(SysPermissionUpdateDTO dto) {
        SysPermission oldPerm = sysPermissionMapper.selectById(dto.getId());
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        if (sysPermission.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()) {
            sysRoleMapper.deleteRolePermissionByPermissionId(sysPermission.getId());
        }
        sysPermissionMapper.updateById(sysPermission);
        if (oldPerm.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()
                && sysPermission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
            sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
        }
    }

    @Override
    public List<SysPermissionAuthorTreeVO> getPermissionAuthorTree() {
        return sysPermissionMapper.getPermissionAuthorTree();
    }

}
