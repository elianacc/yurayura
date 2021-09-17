package org.cny.yurayura.controller.sys.permission;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.cny.yurayura.dto.PermissionSelectDto;
import org.cny.yurayura.entity.sys.permission.Permission;
import org.cny.yurayura.enumerate.PermissionTypeEnum;
import org.cny.yurayura.system.annotation.PreventRepeatSubmit;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.cny.yurayura.service.sys.permission.IPermissionService;
import org.cny.yurayura.vo.ApiResult;

/**
 * 系统权限 controller
 *
 * @author CNY
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/api/sys/permission")
@Api(tags = "系统权限API")
public class PermissionController {

    @Autowired
    private IPermissionService iPermissionService;

    /**
     * 查询系统权限（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统权限（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iPermissionService.getById(id));
    }

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getPage")
    @ApiOperation("分页查询系统权限")
    public ApiResult getPage(PermissionSelectDto dto) {
        if (StringUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (StringUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Permission> pageInfo = iPermissionService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统权限
     *
     * @param permission
     * @param permissionBtnVal
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统权限")
    @ApiImplicitParam(name = "permissionBtnVal", value = "权限按钮值")
    public ApiResult insert(Permission permission, String permissionBtnVal) {
        if (StringUtils.isEmpty(permission.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (StringUtils.isEmpty(permissionBtnVal) && permission.getPermissionType() == PermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (permission.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iPermissionService.insert(permission, permissionBtnVal);
        if (!StringUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统权限
     *
     * @param permission
     * @param permissionBtnVal
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统权限")
    @ApiImplicitParam(name = "permissionBtnVal", value = "权限按钮值")
    public ApiResult update(Permission permission, String permissionBtnVal) {
        if (StringUtils.isEmpty(permission.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (StringUtils.isEmpty(permission.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (StringUtils.isEmpty(permissionBtnVal) && permission.getPermissionType() == PermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (permission.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iPermissionService.update(permission, permissionBtnVal);
        if (!StringUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return org.cny.yurayura.vo.ApiResult
     */
    @GetMapping("/getPermissionAuthorTree")
    @ApiOperation("查询权限授权树")
    public ApiResult getPermissionAuthorTree() {
        return ApiResult.success("权限授权树查询成功", iPermissionService.getPermissionAuthorTree());
    }

}

