package org.elianacc.yurayura.service.sys.role.impl;

import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dao.sys.manager.SysManagerMapper;
import org.elianacc.yurayura.dao.sys.permission.SysPermissionMapper;
import org.elianacc.yurayura.dao.sys.role.SysRoleMapper;
import org.elianacc.yurayura.dto.SysRoleInsertDto;
import org.elianacc.yurayura.dto.SysRoleSelectDto;
import org.elianacc.yurayura.dto.SysRoleUpdateDto;
import org.elianacc.yurayura.entity.sys.role.SysRole;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.elianacc.yurayura.service.sys.role.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    public PageInfo<Map<String, Object>> getPage(SysRoleSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Map<String, Object>> roleAndPermissionList = sysRoleMapper.getRoleAndPermissionListBySelectDto(dto);
        return new PageInfo<>(roleAndPermissionList, 5);
    }

    @Override
    public List<SysRole> getAll() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        return sysRoleMapper.selectList(queryWrapper.ne("id", 1));
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.roleName"}, autoRelease = false)
    @Override
    public String insert(SysRoleInsertDto dto) {
        String warn = "";
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        List<SysRole> sysRoleList = sysRoleMapper.selectList(queryWrapper.eq("role_name", dto.getRoleName()));
        if (sysRoleList.isEmpty()) {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(dto, sysRole);
            sysRole.setRoleCreateTime(LocalDateTime.now());
            sysRoleMapper.insert(sysRole);
            if (!dto.getPermissionIdArr().isEmpty()) {
                List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                        .filter(permissionId -> !(sysPermissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                if (!permissionIdExistList.isEmpty()) {
                    sysRoleMapper.insertBatchRolePermission(permissionIdExistList, sysRole.getId());
                }
            }
        } else {
            warn = "角色名已经存在";
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @Override
    public String update(SysRoleUpdateDto dto) {
        String warn = "";
        SysRole oldRole = sysRoleMapper.selectById(dto.getId());
        if (oldRole.getRoleName().equals("超级管理员")) {
            warn = "超级管理员的角色信息不允许被修改";
            return warn;
        }
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        List<SysRole> sysRoleList = sysRoleMapper.selectList(queryWrapper.eq("role_name", dto.getRoleName()));
        if (!sysRoleList.isEmpty() && !oldRole.getRoleName().equals(dto.getRoleName())) {
            warn = "角色名已经存在";
        } else {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(dto, sysRole);
            sysRole.setRoleUpdateTime(LocalDateTime.now());
            sysRoleMapper.updateById(sysRole);
            sysRoleMapper.deleteRolePermissionByRoleId(sysRole.getId());
            if (!dto.getPermissionIdArr().isEmpty()) {
                List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                        .filter(permissionId -> !(sysPermissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
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
        return warn;
    }


}
