package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysPermissionInsertDTO;
import pers.elianacc.yurayura.dto.SysPermissionSelectDTO;
import pers.elianacc.yurayura.dto.SysPermissionUpdateDTO;
import pers.elianacc.yurayura.entity.SysPermission;
import pers.elianacc.yurayura.service.ISysPermissionService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO;

import java.util.List;

/**
 * 系统权限 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
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
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.permission.SysPermission>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统权限（根据系统权限id）")
    public ApiResult<SysPermission> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysPermissionService.getById(dto.getId()));
    }

    /**
     * 分页查询系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysPermission>>
     */
    @PostMapping("/getPage")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, autoRelease = false)
    @ApiOperation("分页查询系统权限")
    public ApiResult<PageInfo<SysPermission>> getPage(@Validated @RequestBody SysPermissionSelectDTO dto) {
        return ApiResult.success("分页查询成功", iSysPermissionService.getPage(dto));
    }

    /**
     * 添加系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.permissionType"
            , "#dto.permissionBelongSubmenuName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统权限")
    public ApiResult<String> insert(@Validated @RequestBody SysPermissionInsertDTO dto) {
        iSysPermissionService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统权限
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统权限")
    public ApiResult<String> update(@Validated @RequestBody SysPermissionUpdateDTO dto) {
        iSysPermissionService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询权限授权树
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.vo.SysPermissionAuthorTreeVO>>
     */
    @GetMapping("/getPermissionAuthorTree")
    @ApiOperation("查询权限授权树")
    public ApiResult<List<SysPermissionAuthorTreeVO>> getPermissionAuthorTree() {
        return ApiResult.success("权限授权树查询成功", iSysPermissionService.getPermissionAuthorTree());
    }

}
