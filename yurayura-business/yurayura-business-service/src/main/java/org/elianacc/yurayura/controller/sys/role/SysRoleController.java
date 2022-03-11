package org.elianacc.yurayura.controller.sys.role;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.SysRoleInsertDto;
import org.elianacc.yurayura.dto.SysRoleSelectDto;
import org.elianacc.yurayura.dto.SysRoleUpdateDto;
import org.elianacc.yurayura.service.sys.role.ISysRoleService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统角色 controller
 *
 * @author ELiaNaCc
 * @since 2022-03-07
 */
@RestController
@RequestMapping("/api/sys/role")
@Api(tags = "系统角色API")
public class SysRoleController {

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 查询系统角色（根据系统角色id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统角色（根据系统角色id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysRoleService.getById(dto.getId()));
    }

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统角色")
    public ApiResult getPage(@RequestBody SysRoleSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Map<String, Object>> pageInfo = iSysRoleService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统角色")
    public ApiResult insert(@RequestBody SysRoleInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getRoleName())) {
            return ApiResult.warn("角色名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getRoleName().length() > 20) {
            return ApiResult.warn("角色名不能超过20个字符");
        }
        String warn = iSysRoleService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统角色
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统角色")
    public ApiResult update(@RequestBody SysRoleUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleName())) {
            return ApiResult.warn("角色名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getRoleStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getRoleName().length() > 20) {
            return ApiResult.warn("角色名不能超过20个字符");
        }
        String warn = iSysRoleService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询所有系统角色
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统角色")
    public ApiResult getAll() {
        return ApiResult.success("查询成功", iSysRoleService.getAll());
    }
}

