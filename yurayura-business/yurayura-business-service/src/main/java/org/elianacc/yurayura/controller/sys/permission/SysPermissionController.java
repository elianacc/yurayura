package org.elianacc.yurayura.controller.sys.permission;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysPermissionInsertDto;
import org.elianacc.yurayura.dto.SysPermissionSelectDto;
import org.elianacc.yurayura.dto.SysPermissionUpdateDto;
import org.elianacc.yurayura.entity.sys.permission.SysPermission;
import org.elianacc.yurayura.enumerate.SysPermissionTypeEnum;
import org.elianacc.yurayura.service.sys.permission.ISysPermissionService;
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
public class SysPermissionController {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 查询系统权限（根据系统权限id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统权限（根据系统权限id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysPermissionService.getById(dto.getId()));
    }

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统权限")
    public ApiResult getPage(@RequestBody SysPermissionSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<SysPermission> pageInfo = iSysPermissionService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统权限")
    public ApiResult insert(@RequestBody SysPermissionInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionBelongSubmenuName())) {
            return ApiResult.warn("所属子菜单标识不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionType())) {
            return ApiResult.warn("权限类型不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionBtnVal()) && dto.getPermissionType() == SysPermissionTypeEnum.BUTTON.getTypeId().intValue()) {
            return ApiResult.warn("权限类型为按钮时权限按钮不能为空");
        } else if (dto.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iSysPermissionService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统权限
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统权限")
    public ApiResult update(@RequestBody SysPermissionUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionName())) {
            return ApiResult.warn("权限名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPermissionSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dto.getPermissionName().length() > 20) {
            return ApiResult.warn("权限名称不能超过20个字符");
        }
        String warn = iSysPermissionService.update(dto);
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
        return ApiResult.success("权限授权树查询成功", iSysPermissionService.getPermissionAuthorTree());
    }

}

