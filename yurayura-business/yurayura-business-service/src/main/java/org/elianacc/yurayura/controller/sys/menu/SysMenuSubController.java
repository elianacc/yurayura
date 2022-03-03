package org.elianacc.yurayura.controller.sys.menu;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysMenuSubInsertDto;
import org.elianacc.yurayura.dto.SysMenuSubUpdateDto;
import org.elianacc.yurayura.service.sys.menu.ISysMenuSubService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统子菜单 controller
 *
 * @author ELiaNaCc
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/api/sys/menuSub")
@Api(tags = "系统子菜单API")
public class SysMenuSubController {

    @Autowired
    private ISysMenuSubService iSysMenuSubService;

    /**
     * 查询系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统子菜单（根据系统子菜单id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysMenuSubService.getById(dto.getId()));
    }

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统子菜单")
    public ApiResult insert(@RequestBody SysMenuSubInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuName())) {
            return ApiResult.warn("标识不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIndex())) {
            return ApiResult.warn("路径不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuPid())) {
            return ApiResult.warn("父菜单id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (!dto.getMenuName().matches("^[a-z][a-z_]*$")) {
            return ApiResult.warn("标识只能包含小写字母下划线，以小写字母开头");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuName().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题、标识不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iSysMenuSubService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统子菜单")
    public ApiResult update(@RequestBody SysMenuSubUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuTitle())) {
            return ApiResult.warn("标题不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuIconClass())) {
            return ApiResult.warn("图标样式不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getMenuStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getMenuTitle().length() > 20 || dto.getMenuIconClass().length() > 30) {
            return ApiResult.warn("标题不能超过20个字符，图标样式不能超过30个字符");
        }
        String warn = iSysMenuSubService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/deleteById")
    @ApiOperation("删除系统子菜单（根据系统子菜单id）")
    public ApiResult deleteById(@RequestBody IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        iSysMenuSubService.deleteById(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getByIndex")
    @ApiOperation("查询系统子菜单（根据路径）")
    @ApiImplicitParam(name = "index", value = "路径", required = true, dataTypeClass = String.class)
    public ApiResult getByIndex(String index) {
        if (ObjectUtils.isEmpty(index)) {
            return ApiResult.warn("路径不能为空");
        }
        return ApiResult.success("查询成功", iSysMenuSubService.getByIndex(index));
    }

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统子菜单")
    public ApiResult getAllMenuName() {
        return ApiResult.success("查询成功", iSysMenuSubService.getAll());
    }
}

