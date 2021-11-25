package org.elianacc.yurayura.controller.sys.menu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.entity.sys.menu.Menu;
import org.elianacc.yurayura.service.sys.menu.IMenuService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单 controller
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/api/sys/menu")
@Api(tags = "系统菜单API")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 查询系统菜单（根据id）
     *
     * @param id
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统菜单（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iMenuService.getById(id));
    }

    /**
     * 查询系统侧边菜单
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getSysSideMenu")
    @ApiOperation("查询系统侧边菜单")
    public ApiResult getSysSideMenu() {
        return ApiResult.success("侧边菜单查询成功", iMenuService.getTreeListForCurrentManager());
    }

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getTreeList")
    @ApiOperation("查询系统菜单树形列表")
    public ApiResult getTreeList() {
        return ApiResult.success("树形列表查询成功", iMenuService.getTreeList());
    }

    /**
     * 添加系统菜单
     *
     * @param menu
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统菜单")
    public ApiResult insert(@RequestBody Menu menu) {
        if (ObjectUtils.isEmpty(menu.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (!menu.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (menu.getMenuTitle().length() > 20 || menu.getMenuName().length() > 20 || menu.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iMenuService.insert(menu);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统菜单
     *
     * @param menu
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统菜单")
    public ApiResult update(@RequestBody Menu menu) {
        if (ObjectUtils.isEmpty(menu.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(menu.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (!menu.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (menu.getMenuTitle().length() > 20 || menu.getMenuName().length() > 20 || menu.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iMenuService.update(menu);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统菜单（根据id）
     *
     * @param id
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @DeleteMapping("/deleteById")
    @ApiOperation("删除系统菜单（根据id）")
    public ApiResult deleteById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        iMenuService.deleteById(id);
        return ApiResult.success("删除成功");
    }
}

