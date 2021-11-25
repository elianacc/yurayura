package org.elianacc.yurayura.service.sys.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.elianacc.yurayura.bo.MenuTreeSelectBo;
import org.elianacc.yurayura.dao.sys.manager.ManagerMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuSubMapper;
import org.elianacc.yurayura.dao.sys.permission.PermissionMapper;
import org.elianacc.yurayura.entity.sys.manager.Manager;
import org.elianacc.yurayura.entity.sys.menu.Menu;
import org.elianacc.yurayura.entity.sys.menu.MenuSub;
import org.elianacc.yurayura.entity.sys.permission.Permission;
import org.elianacc.yurayura.enumerate.MenuTypeEnum;
import org.elianacc.yurayura.service.sys.menu.IMenuService;
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
    public String insert(Menu menu) {
        String warn = "";
        List<String> menuNameList = menuMapper.getMenuNameAndMenuSubName();
        if (menuNameList.contains(menu.getMenuName())) {
            warn = "菜单标识已存在，请更换";
        } else {
            menu.setMenuType(MenuTypeEnum.FIRSTLEVEL.getTypeId());
            menuMapper.insert(menu);
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        menuMapper.deleteById(id);
        QueryWrapper<MenuSub> queryWrapper = new QueryWrapper<>();
        List<MenuSub> deleteMenuSubs = menuSubMapper.selectList(queryWrapper.eq("menu_pid", id));
        deleteMenuSubs.forEach(menuSub -> {
            QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
            List<Permission> deletePermissions = permissionMapper.selectList(permissionQueryWrapper.eq("permission_belong_submenu_name", menuSub.getMenuName()));
            deletePermissions.forEach(permission -> managerMapper.deleteManagerPermissionByPermissionId(permission.getId()));
            permissionMapper.delete(permissionQueryWrapper.eq("permission_belong_submenu_name", menuSub.getMenuName()));
        });
        menuSubMapper.delete(queryWrapper.eq("menu_pid", id));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(Menu menu) {
        String warn = "";
        Menu oldMenu = menuMapper.selectById(menu.getId());
        if (!menu.getMenuName().equals(oldMenu.getMenuName())) {
            warn = "菜单标识确定后无法修改";
        } else {
            menuMapper.updateById(menu);
        }
        return warn;
    }
}
