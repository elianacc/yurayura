package org.cny.yurayura.service.sys.menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.cny.yurayura.bo.MenuTreeSelectBo;
import org.cny.yurayura.dao.sys.menu.MenuMapper;
import org.cny.yurayura.dao.sys.menu.MenuSubMapper;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.entity.sys.menu.Menu;
import org.cny.yurayura.entity.sys.menu.MenuSub;
import org.cny.yurayura.enumerate.MenuTypeEnum;
import org.cny.yurayura.service.sys.menu.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统菜单 service impl
 *
 * @author CNY
 * @since 2021-03-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuSubMapper menuSubMapper;

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
