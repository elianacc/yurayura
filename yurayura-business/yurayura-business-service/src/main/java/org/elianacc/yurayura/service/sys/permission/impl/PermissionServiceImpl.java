package org.elianacc.yurayura.service.sys.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.bo.PermissionAuthorTreeSelectBo;
import org.elianacc.yurayura.dao.sys.manager.ManagerMapper;
import org.elianacc.yurayura.dao.sys.permission.PermissionMapper;
import org.elianacc.yurayura.dto.PermissionSelectDto;
import org.elianacc.yurayura.entity.sys.permission.Permission;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.elianacc.yurayura.service.sys.permission.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 系统权限 service impl
 *
 * @author ELiaNaCc
 * @since 2021-08-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ManagerMapper managerMapper;


    @Override
    public PageInfo<Permission> getPage(PermissionSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper
                .like(!ObjectUtils.isEmpty(dto.getPermissionCode()), "permission_code", dto.getPermissionCode())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionType()), "permission_type", dto.getPermissionType())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionStatus()), "permission_status", dto.getPermissionStatus())
                .eq(!ObjectUtils.isEmpty(dto.getPermissionBelongSubmenuName()), "permission_belong_submenu_name", dto.getPermissionBelongSubmenuName())
                .orderByAsc("permission_belong_submenu_name", "permission_seq")
        );
        return new PageInfo<>(permissionList, 5);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(Permission permission, String permissionBtnVal) {
        String warn = "";
        if (ObjectUtils.isEmpty(permissionBtnVal)) {
            permission.setPermissionCode(permission.getPermissionBelongSubmenuName() + "_select");
        } else {
            permission.setPermissionCode(permission.getPermissionBelongSubmenuName() + "_" + permissionBtnVal);
        }
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        List<Permission> existPermList = permissionMapper.selectList(queryWrapper.eq("permission_code", permission.getPermissionCode()));
        if (!existPermList.isEmpty()) {
            warn = "此权限已经存在";
        } else {
            permissionMapper.insert(permission);
            if (permission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
                managerMapper.insertManagerPermissionForAdmin(permission.getId());
            }
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(Permission permission, String permissionBtnVal) {
        String warn = "";
        if (ObjectUtils.isEmpty(permissionBtnVal)) {
            permission.setPermissionCode(permission.getPermissionBelongSubmenuName() + "_select");
        } else {
            permission.setPermissionCode(permission.getPermissionBelongSubmenuName() + "_" + permissionBtnVal);
        }
        Permission oldPerm = permissionMapper.selectById(permission.getId());
        if (!oldPerm.getPermissionCode().equals(permission.getPermissionCode())) {
            warn = "所属子菜单标识，权限类型，权限按钮确定后无法修改";
        } else {
            if (permission.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()) {
                managerMapper.deleteManagerPermissionByPermissionId(permission.getId());
            }
            permissionMapper.updateById(permission);
            if (oldPerm.getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()
                    && permission.getPermissionStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()) {
                managerMapper.insertManagerPermissionForAdmin(permission.getId());
            }
        }
        return warn;
    }

    @Override
    public List<PermissionAuthorTreeSelectBo> getPermissionAuthorTree() {
        return permissionMapper.getPermissionAuthorTree();
    }

}
