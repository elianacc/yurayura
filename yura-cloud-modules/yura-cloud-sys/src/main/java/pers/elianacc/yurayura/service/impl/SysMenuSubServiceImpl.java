package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.elianacc.yurayura.dao.SysMenuMapper;
import pers.elianacc.yurayura.dao.SysMenuSubMapper;
import pers.elianacc.yurayura.dao.SysPermissionMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDTO;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDTO;
import pers.elianacc.yurayura.entity.SysMenuSub;
import pers.elianacc.yurayura.entity.SysPermission;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.enumerate.SysMenuTypeEnum;
import pers.elianacc.yurayura.enumerate.SysPermissionTypeEnum;
import pers.elianacc.yurayura.service.ISysMenuSubService;
import pers.elianacc.yurayura.vo.VueRouterVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统子菜单 service impl
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@Service
public class SysMenuSubServiceImpl extends ServiceImpl<SysMenuSubMapper, SysMenuSub> implements ISysMenuSubService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysMenuSubMapper sysMenuSubMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public void insert(SysMenuSubInsertDTO dto) {
        Assert.isTrue(("/business/" + dto.getMenuName()).equals(dto.getMenuIndex()), "菜单路径不正确");
        List<String> menuNameList = sysMenuMapper.getMenuNameAndMenuSubName();
        Assert.isTrue(!menuNameList.contains(dto.getMenuName()), "菜单标识已存在，请更换");
        SysMenuSub sysMenuSub = new SysMenuSub();
        BeanUtils.copyProperties(dto, sysMenuSub);
        sysMenuSub.setMenuType(SysMenuTypeEnum.SECONDLEVEL.getTypeId());
        sysMenuSubMapper.insert(sysMenuSub);
        SysPermission sysPermission = new SysPermission();
        sysPermission.setPermissionCode(sysMenuSub.getMenuName() + "_select");
        sysPermission.setPermissionName(sysMenuSub.getMenuTitle() + "查看");
        sysPermission.setPermissionType(SysPermissionTypeEnum.MENU.getTypeId());
        sysPermission.setPermissionStatus(EnableStatusEnum.ENABLE.getStatusId());
        sysPermission.setPermissionBelongSubmenuName(sysMenuSub.getMenuName());
        sysPermission.setPermissionSeq(1);
        sysPermissionMapper.insert(sysPermission);
        sysRoleMapper.insertRolePermissionForAdmin(sysPermission.getId());
    }

    @Override
    public void deleteById(IdDTO dto) {
        SysMenuSub deleteSysMenuSub = sysMenuSubMapper.selectById(dto.getId());
        List<SysPermission> deleteSysPermissions = sysPermissionMapper
                .selectList(Wrappers.<SysPermission>lambdaQuery()
                        .eq(SysPermission::getPermissionBelongSubmenuName, deleteSysMenuSub.getMenuName()));
        deleteSysPermissions.forEach(permission -> sysRoleMapper.deleteRolePermissionByPermissionId(permission.getId()));
        sysPermissionMapper
                .delete(Wrappers.<SysPermission>lambdaQuery()
                        .eq(SysPermission::getPermissionBelongSubmenuName, deleteSysMenuSub.getMenuName()));
        sysMenuSubMapper.deleteById(dto.getId());
    }

    @Override
    public void update(SysMenuSubUpdateDTO dto) {
        SysMenuSub sysMenuSub = new SysMenuSub();
        BeanUtils.copyProperties(dto, sysMenuSub);
        sysMenuSubMapper.updateById(sysMenuSub);
    }

    @Override
    public SysMenuSub getByIndex(String index) {
        return sysMenuSubMapper
                .selectOne(Wrappers.<SysMenuSub>lambdaQuery()
                        .eq(SysMenuSub::getMenuIndex, index));
    }

    @Override
    public List<SysMenuSub> getAll() {
        return sysMenuSubMapper.selectList(null);
    }

    @Override
    public List<VueRouterVO> getVueRouter() {
        List<VueRouterVO> voList = sysMenuSubMapper
                .selectList(null)
                .stream()
                .map(subMenu -> {
                    VueRouterVO routerVO = new VueRouterVO();
                    routerVO.setPath(subMenu.getMenuName());
                    String nameCamelCase = StrUtil.toCamelCase(subMenu.getMenuName());
                    String componentName = "Business" + nameCamelCase.substring(0, 1)
                            .toUpperCase() + nameCamelCase.substring(1);
                    routerVO.setName(componentName);
                    routerVO.setComponent(routerVO.getName());
                    return routerVO;
                }).collect(Collectors.toList());
        VueRouterVO routerVO2 = new VueRouterVO();
        routerVO2.setPath("index");
        routerVO2.setName("BusinessIndex");
        routerVO2.setComponent("BusinessIndex");
        voList.add(0, routerVO2);
        VueRouterVO routerVO1 = new VueRouterVO();
        routerVO1.setPath("");
        routerVO1.setName("Business");
        routerVO1.setComponent("BusinessIndex");
        voList.add(0, routerVO1);
        return voList;
    }
}
