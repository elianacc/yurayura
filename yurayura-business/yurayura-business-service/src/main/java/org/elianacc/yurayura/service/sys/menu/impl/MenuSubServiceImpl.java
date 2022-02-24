package org.elianacc.yurayura.service.sys.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elianacc.yurayura.dao.sys.manager.ManagerMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuMapper;
import org.elianacc.yurayura.dao.sys.menu.MenuSubMapper;
import org.elianacc.yurayura.dao.sys.permission.PermissionMapper;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.MenuSubInsertDto;
import org.elianacc.yurayura.dto.MenuSubUpdateDto;
import org.elianacc.yurayura.entity.sys.menu.MenuSub;
import org.elianacc.yurayura.entity.sys.permission.Permission;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.elianacc.yurayura.enumerate.MenuTypeEnum;
import org.elianacc.yurayura.enumerate.PermissionTypeEnum;
import org.elianacc.yurayura.service.sys.menu.IMenuSubService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统子菜单 service impl
 *
 * @author ELiaNaCc
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
    public String insert(MenuSubInsertDto dto) {
        String warn = "";
        if (!("/business/" + dto.getMenuName()).equals(dto.getMenuIndex())) {
            warn = "菜单路径不正确";
        } else {
            List<String> menuNameList = menuMapper.getMenuNameAndMenuSubName();
            if (menuNameList.contains(dto.getMenuName())) {
                warn = "菜单标识已存在，请更换";
            } else {
                MenuSub menuSub = new MenuSub();
                BeanUtils.copyProperties(dto, menuSub);
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
    public void deleteById(IdDto dto) {
        MenuSub deleteMenuSub = menuSubMapper.selectById(dto.getId());
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        List<Permission> deletePermissions = permissionMapper.selectList(permissionQueryWrapper
                .eq("permission_belong_submenu_name", deleteMenuSub.getMenuName()));
        deletePermissions.forEach(permission -> managerMapper.deleteManagerPermissionByPermissionId(permission.getId()));
        permissionMapper.delete(permissionQueryWrapper.eq("permission_belong_submenu_name", deleteMenuSub.getMenuName()));
        menuSubMapper.deleteById(dto.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(MenuSubUpdateDto dto) {
        String warn = "";
        MenuSub menuSub = new MenuSub();
        BeanUtils.copyProperties(dto, menuSub);
        menuSubMapper.updateById(menuSub);
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
