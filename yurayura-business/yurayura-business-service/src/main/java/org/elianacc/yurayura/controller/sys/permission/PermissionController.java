package org.elianacc.yurayura.controller.sys.permission;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.PermissionSelectDto;
import org.elianacc.yurayura.entity.sys.permission.Permission;
import org.elianacc.yurayura.enumerate.PermissionTypeEnum;
import org.elianacc.yurayura.service.sys.permission.IPermissionService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限 controller
 *
 * @author ELiaNaCc
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
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统权限（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iPermissionService.getById(id));
    }

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getPage")
    @ApiOperation("分页查询系统权限")
    public ApiResult getPage(PermissionSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
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
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统权限")
    @ApiImplicitParam(name = "permissionBtnVal", value = "权限按钮值")
    public ApiResult insert(Permission permission, String permissionBtnVal) {
        if (ObjectUtils.isEmpty(permission.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(permissionBtnVal) && permission.getPermissionType() == PermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (permission.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iPermissionService.insert(permission, permissionBtnVal);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统权限
     *
     * @param permission
     * @param permissionBtnVal
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统权限")
    @ApiImplicitParam(name = "permissionBtnVal", value = "权限按钮值")
    public ApiResult update(Permission permission, String permissionBtnVal) {
        if (ObjectUtils.isEmpty(permission.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(permission.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(permissionBtnVal) && permission.getPermissionType() == PermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (permission.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iPermissionService.update(permission, permissionBtnVal);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getPermissionAuthorTree")
    @ApiOperation("查询权限授权树")
    public ApiResult getPermissionAuthorTree() {
        return ApiResult.success("权限授权树查询成功", iPermissionService.getPermissionAuthorTree());
    }

}

