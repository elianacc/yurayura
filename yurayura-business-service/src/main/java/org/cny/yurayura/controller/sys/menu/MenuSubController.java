package org.cny.yurayura.controller.sys.menu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.cny.yurayura.entity.sys.menu.MenuSub;
import org.cny.yurayura.service.sys.menu.IMenuSubService;
import org.cny.yurayura.system.annotation.PreventRepeatSubmit;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统子菜单 controller
 *
 * @author CNY
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/api/sys/menuSub")
@Api(tags = "系统子菜单API")
public class MenuSubController {

    @Autowired
    private IMenuSubService iMenuSubService;

    /**
     * 查询系统子菜单（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统子菜单（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iMenuSubService.getById(id));
    }

    /**
     * 添加系统子菜单
     *
     * @param menuSub
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统子菜单")
    public ApiResult insert(@RequestBody MenuSub menuSub) {
        if (StringUtils.isEmpty(menuSub.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuIndex())) {
            return ApiResult.warn("路径不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuPid())) {
            return ApiResult.warn("父菜单id不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (!menuSub.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (menuSub.getMenuTitle().length() > 20 || menuSub.getMenuName().length() > 20 || menuSub.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iMenuSubService.insert(menuSub);
        if (!StringUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统子菜单
     *
     * @param menuSub
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统子菜单")
    public ApiResult update(@RequestBody MenuSub menuSub) {
        if (StringUtils.isEmpty(menuSub.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuIndex())) {
            return ApiResult.warn("路径不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (StringUtils.isEmpty(menuSub.getMenuPid())) {
            return ApiResult.warn("父菜单id不能为空");
        } else if (!menuSub.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (menuSub.getMenuTitle().length() > 20 || menuSub.getMenuName().length() > 20 || menuSub.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iMenuSubService.update(menuSub);
        if (!StringUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getByIndex")
    @ApiOperation("查询系统子菜单（根据路径）")
    public ApiResult getByIndex(String index) {
        if (StringUtils.isEmpty(index)) {
            return ApiResult.warn("路径不能为空");
        }
        return ApiResult.success("查询成功", iMenuSubService.getByIndex(index));
    }

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统子菜单")
    public ApiResult getAllMenuName() {
        return ApiResult.success("查询成功", iMenuSubService.getAll());
    }
}

