package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysMenuSubInsertDTO;
import pers.elianacc.yurayura.dto.SysMenuSubUpdateDTO;
import pers.elianacc.yurayura.entity.SysMenuSub;
import pers.elianacc.yurayura.service.ISysMenuSubService;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统子菜单 controller
 *
 * @author ELiaNaCc
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/api/sys/menuSub")
@Api(tags = "系统子菜单API")
@Validated
public class SysMenuSubController {

    @Autowired
    private ISysMenuSubService iSysMenuSubService;

    /**
     * 查询系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统子菜单（根据系统子菜单id）")
    public ApiResult<SysMenuSub> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysMenuSubService.getById(dto.getId()));
    }

    /**
     * 添加系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统子菜单")
    public ApiResult<String> insert(@Validated @RequestBody SysMenuSubInsertDTO dto) {
        iSysMenuSubService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统子菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统子菜单")
    public ApiResult<String> update(@Validated @RequestBody SysMenuSubUpdateDTO dto) {
        iSysMenuSubService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统子菜单（根据系统子菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("删除系统子菜单（根据系统子菜单id）")
    public ApiResult<String> deleteById(@Validated @RequestBody IdDTO dto) {
        iSysMenuSubService.deleteById(dto);
        return ApiResult.success("删除成功");
    }

    /**
     * 查询系统子菜单（根据路径）
     *
     * @param index
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>
     */
    @GetMapping("/getByIndex")
    @ApiOperation("查询系统子菜单（根据路径）")
    @ApiImplicitParam(name = "index", value = "路径", required = true, dataTypeClass = String.class)
    public ApiResult<SysMenuSub> getByIndex(@NotBlank(message = "路径不能为空") @RequestParam String index) {
        return ApiResult.success("查询成功", iSysMenuSubService.getByIndex(index));
    }

    /**
     * 查询所有系统子菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.menu.SysMenuSub>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统子菜单")
    public ApiResult<List<SysMenuSub>> getAll() {
        return ApiResult.success("查询成功", iSysMenuSubService.getAll());
    }
}
