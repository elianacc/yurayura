package org.elianacc.yurayura.service.sys.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.elianacc.yurayura.bo.MenuTreeSelectBo;
import org.elianacc.yurayura.dao.sys.manager.ManagerMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuSubMapper;
import org.elianacc.yurayura.dao.sys.permission.PermissionMapper;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.MenuInsertDto;
import org.elianacc.yurayura.dto.MenuUpdateDto;
import org.elianacc.yurayura.entity.sys.manager.Manager;
import org.elianacc.yurayura.entity.sys.menu.Menu;
import org.elianacc.yurayura.entity.sys.menu.MenuSub;
import org.elianacc.yurayura.entity.sys.permission.Permission;
import org.elianacc.yurayura.enumerate.MenuTypeEnum;
import org.elianacc.yurayura.service.sys.menu.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuSubMapper menuSubMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public List<MenuTreeSelectBo> getTreeListForCurrentManager() {
        Manager currentManager = (Manager) SecurityUtils.getSubject().getPrincipal();
        return menuMapper.getTreeListForCurrentManager(currentManager.getId());
    }

    @Override
    public List<MenuTreeSelectBo> getTreeList() {
        return menuMapper.getTreeList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(MenuInsertDto dto) {
        String warn = "";
        List<String> menuNameList = menuMapper.getMenuNameAndMenuSubName();
        if (menuNameList.contains(dto.getMenuName())) {
            warn = "菜单标识已存在，请更换";
        } else {
            Menu menu = new Menu();
            BeanUtils.copyProperties(dto, menu);
            menu.setMenuType(MenuTypeEnum.FIRSTLEVEL.getTypeId());
            menuMapper.insert(menu);
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(IdDto dto) {
        menuMapper.deleteById(dto.getId());
        QueryWrapper<MenuSub> queryWrapper = new QueryWrapper<>();
        List<MenuSub> deleteMenuSubs = menuSubMapper.selectList(queryWrapper.eq("menu_pid", dto.getId()));
        deleteMenuSubs.forEach(menuSub -> {
            QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
            List<Permission> deletePermissions = permissionMapper.selectList(permissionQueryWrapper
                    .eq("permission_belong_submenu_name", menuSub.getMenuName()));
            deletePermissions.forEach(permission -> managerMapper.deleteManagerPermissionByPermissionId(permission.getId()));
            permissionMapper.delete(permissionQueryWrapper.eq("permission_belong_submenu_name", menuSub.getMenuName()));
        });
        menuSubMapper.delete(queryWrapper.eq("menu_pid", dto.getId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(MenuUpdateDto dto) {
        String warn = "";
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        menuMapper.updateById(menu);
        return warn;
    }
}
