package org.elianacc.yurayura.service.sys.menu.impl;

import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.elianacc.yurayura.bo.SysMenuTreeSelectBo;
import org.elianacc.yurayura.dao.sys.menu.SysMenuMapper;
import org.elianacc.yurayura.dao.sys.menu.SysMenuSubMapper;
import org.elianacc.yurayura.dao.sys.permission.SysPermissionMapper;
import org.elianacc.yurayura.dao.sys.role.SysRoleMapper;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysMenuInsertDto;
import org.elianacc.yurayura.dto.SysMenuUpdateDto;
import org.elianacc.yurayura.entity.sys.manager.SysManager;
import org.elianacc.yurayura.entity.sys.menu.SysMenu;
import org.elianacc.yurayura.entity.sys.menu.SysMenuSub;
import org.elianacc.yurayura.entity.sys.permission.SysPermission;
import org.elianacc.yurayura.enumerate.SysMenuTypeEnum;
import org.elianacc.yurayura.service.sys.menu.ISysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统菜单 service impl
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuSubMapper sysMenuSubMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysMenuTreeSelectBo> getTreeListForCurrentManager() {
        SysManager currentSysManager = (SysManager) SecurityUtils.getSubject().getPrincipal();
        return sysMenuMapper.getTreeListForCurrentManager(currentSysManager.getId());
    }

    @Override
    public List<SysMenuTreeSelectBo> getTreeList() {
        return sysMenuMapper.getTreeList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @Override
    public String insert(SysMenuInsertDto dto) {
        String warn = "";
        List<String> menuNameList = sysMenuMapper.getMenuNameAndMenuSubName();
        if (menuNameList.contains(dto.getMenuName())) {
            warn = "菜单标识已存在，请更换";
        } else {
            SysMenu sysMenu = new SysMenu();
            BeanUtils.copyProperties(dto, sysMenu);
            sysMenu.setMenuType(SysMenuTypeEnum.FIRSTLEVEL.getTypeId());
            sysMenuMapper.insert(sysMenu);
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(IdDto dto) {
        sysMenuMapper.deleteById(dto.getId());
        QueryWrapper<SysMenuSub> queryWrapper = new QueryWrapper<>();
        List<SysMenuSub> deleteSysMenuSubs = sysMenuSubMapper.selectList(queryWrapper.eq("menu_pid", dto.getId()));
        deleteSysMenuSubs.forEach(menuSub -> {
            QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
            List<SysPermission> deleteSysPermissions = sysPermissionMapper.selectList(permissionQueryWrapper
                    .eq("permission_belong_submenu_name", menuSub.getMenuName()));
            deleteSysPermissions.forEach(permission -> sysRoleMapper.deleteRolePermissionByPermissionId(permission.getId()));
            sysPermissionMapper.delete(permissionQueryWrapper.eq("permission_belong_submenu_name", menuSub.getMenuName()));
        });
        sysMenuSubMapper.delete(queryWrapper.eq("menu_pid", dto.getId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @Override
    public String update(SysMenuUpdateDto dto) {
        String warn = "";
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        sysMenuMapper.updateById(sysMenu);
        return warn;
    }
}
