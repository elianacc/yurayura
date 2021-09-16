package org.cny.yurayura.service.sys.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cny.yurayura.dao.sys.manager.ManagerMapper;
import org.cny.yurayura.dao.sys.menu.MenuMapper;
import org.cny.yurayura.dao.sys.menu.MenuSubMapper;
import org.cny.yurayura.dao.sys.permission.PermissionMapper;
import org.cny.yurayura.entity.sys.menu.MenuSub;
import org.cny.yurayura.entity.sys.permission.Permission;
import org.cny.yurayura.enumerate.EnableStatusEnum;
import org.cny.yurayura.enumerate.MenuTypeEnum;
import org.cny.yurayura.enumerate.PermissionTypeEnum;
import org.cny.yurayura.service.sys.menu.IMenuSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统子菜单 service impl
 *
 * @author CNY
 * @since 2021-03-16
 */
@Service
public class MenuSubServiceImpl extends ServiceImpl<MenuSubMapper, MenuSub> implements IMenuSubService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuSubMapper menuSubMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ManagerMapper managerMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(MenuSub menuSub) {
        String warn = "";
        if (!("/business/" + menuSub.getMenuName()).equals(menuSub.getMenuIndex())) {
            warn = "菜单路径不正确";
        } else {
            List<String> menuNameList = menuMapper.getMenuNameAndMenuSubName();
            if (menuNameList.contains(menuSub.getMenuName())) {
                warn = "菜单标识已存在，请更换";
            } else {
                menuSub.setMenuType(MenuTypeEnum.SECONDLEVEL.getTypeId());
                menuSubMapper.insert(menuSub);
                Permission permission = new Permission();
                permission.setPermissionCode(menuSub.getMenuName() + "_select");
                permission.setPermissionName(menuSub.getMenuTitle() + "查看");
                permission.setPermissionType(PermissionTypeEnum.MENU.getTypeId());
                permission.setPermissionStatus(EnableStatusEnum.ENABLE.getStatusId());
                permission.setPermissionBelongSubmenuName(menuSub.getMenuName());
                permission.setPermissionSeq(1);
                permissionMapper.insert(permission);
                managerMapper.insertManagerPermissionForAdmin(permission.getId());
            }
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        menuSubMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(MenuSub menuSub) {
        String warn = "";
        if (!("/business/" + menuSub.getMenuName()).equals(menuSub.getMenuIndex())) {
            warn = "菜单路径不正确";
        } else {
            MenuSub oldMenuSub = menuSubMapper.selectById(menuSub.getId());
            if (!menuSub.getMenuName().equals(oldMenuSub.getMenuName())) {
                warn = "菜单标识确定后无法修改";
            } else {
                menuSubMapper.updateById(menuSub);
            }
        }
        return warn;
    }

    @Override
    public MenuSub getByIndex(String index) {
        QueryWrapper<MenuSub> queryWrapper = new QueryWrapper<>();
        return menuSubMapper.selectOne(queryWrapper.eq("menu_index", index));
    }

    @Override
    public List<MenuSub> getAll() {
        return menuSubMapper.selectList(null);
    }
}
