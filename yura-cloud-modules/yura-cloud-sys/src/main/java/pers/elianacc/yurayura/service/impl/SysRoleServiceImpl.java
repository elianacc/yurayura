package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dao.SysManagerMapper;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.SysRoleInsertDTO;
import pers.elianacc.yurayura.dto.SysRoleSelectDTO;
import pers.elianacc.yurayura.dto.SysRoleUpdateDTO;
import pers.elianacc.yurayura.entity.SysRole;
import pers.elianacc.yurayura.enumerate.AdminOrgEnum;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysRoleService;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色 service impl
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysManagerMapper sysManagerMapper;

    @Override
    public PageInfo<SysRoleAndPermissionVO> getPage(SysRoleSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysRoleAndPermissionVO> roleAndPermissionList = sysRoleMapper.getRoleAndPermissionListBySelectDTO(dto);
        return new PageInfo<>(roleAndPermissionList, 5);
    }

    @Override
    public List<SysRole> getByOrg(Integer orgId) {
        return sysRoleMapper
                .selectList(Wrappers.<SysRole>lambdaQuery()
                        .eq(SysRole::getRoleStatus, EnableStatusEnum.ENABLE.getStatusId())
                        .eq(SysRole::getRoleOrg, orgId)
                        .ne(SysRole::getRoleOrg, AdminOrgEnum.ADMIN_ORG.getOrg()));
    }

    @Override
    public void insert(SysRoleInsertDTO dto) {
        List<SysRole> sysRoleList = sysRoleMapper
                .selectList(Wrappers.<SysRole>lambdaQuery()
                        .eq(SysRole::getRoleName, dto.getRoleName())
                        .eq(SysRole::getRoleOrg, dto.getRoleOrg()));
        Assert.isTrue(sysRoleList.isEmpty(), "此组织角色名已经存在");
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setRoleCreateTime(LocalDateTime.now());
        sysRoleMapper.insert(sysRole);
        if (!dto.getPermissionIdArr().isEmpty()) {
            List<Integer> permissionIdExistList = dto.getPermissionIdArr()
                    .stream()
                    .filter(permissionId -> !(sysPermissionMapper
                            .selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!permissionIdExistList.isEmpty()) {
                sysRoleMapper.insertBatchRolePermission(permissionIdExistList, sysRole.getId());
            }
        }
    }

    @Override
    public void update(SysRoleUpdateDTO dto) {
        SysRole oldRole = sysRoleMapper.selectById(dto.getId());
        Assert.isTrue(!oldRole.getRoleOrg().equals(AdminOrgEnum.ADMIN_ORG.getOrg()), "超级管理员的角色信息不允许被修改");
        List<SysRole> sysRoleList = sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, dto.getRoleName())
                .eq(SysRole::getRoleOrg, oldRole.getRoleOrg()));
        Assert.isTrue(sysRoleList.isEmpty() || oldRole.getRoleName().equals(dto.getRoleName())
                , "此组织角色名已经存在");
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setRoleUpdateTime(LocalDateTime.now());
        sysRoleMapper.updateById(sysRole);
        sysRoleMapper.deleteRolePermissionByRoleId(sysRole.getId());
        if (!dto.getPermissionIdArr().isEmpty()) {
            List<Integer> permissionIdExistList = dto.getPermissionIdArr()
                    .stream()
                    .filter(permissionId -> !(sysPermissionMapper
                            .selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!permissionIdExistList.isEmpty()) {
                sysRoleMapper.insertBatchRolePermission(permissionIdExistList, sysRole.getId());
            }
        }
        if (oldRole.getRoleStatus() == EnableStatusEnum.ENABLE.getStatusId().intValue()
                && sysRole.getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()) {
            sysManagerMapper.deleteManagerRoleByRoleId(sysRole.getId());
        }
    }


}
