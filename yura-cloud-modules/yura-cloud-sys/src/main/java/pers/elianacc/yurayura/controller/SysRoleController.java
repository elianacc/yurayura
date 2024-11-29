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
import pers.elianacc.yurayura.dto.SysRoleInsertDTO;
import pers.elianacc.yurayura.dto.SysRoleSelectDTO;
import pers.elianacc.yurayura.dto.SysRoleUpdateDTO;
import pers.elianacc.yurayura.entity.SysRole;
import pers.elianacc.yurayura.service.ISysRoleService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysRoleAndPermissionVO;

import java.util.List;

/**
 * 系统角色 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-16
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
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.role.SysRole>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统角色（根据系统角色id）")
    public ApiResult<SysRole> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysRoleService.getById(dto.getId()));
    }

    /**
     * 分页查询系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysRoleAndPermissionVO>>
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统角色")
    public ApiResult<PageInfo<SysRoleAndPermissionVO>> getPage(@Validated @RequestBody SysRoleSelectDTO dto) {
        return ApiResult.success("分页查询成功", iSysRoleService.getPage(dto));
    }

    /**
     * 添加系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.roleName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统角色")
    public ApiResult<String> insert(@Validated @RequestBody SysRoleInsertDTO dto) {
        iSysRoleService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统角色
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统角色")
    public ApiResult<String> update(@Validated @RequestBody SysRoleUpdateDTO dto) {
        iSysRoleService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统角色（根据组织）
     *
     * @param orgId
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.role.SysRole>>
     */
    @GetMapping("/getByOrg/{orgId}")
    @ApiOperation("查询系统角色（根据组织）")
    public ApiResult<List<SysRole>> getByOrg(@PathVariable("orgId") Integer orgId) {
        return ApiResult.success("查询成功", iSysRoleService.getByOrg(orgId));
    }
}
