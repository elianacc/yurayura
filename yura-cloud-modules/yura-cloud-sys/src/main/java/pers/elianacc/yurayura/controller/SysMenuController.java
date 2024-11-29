package pers.elianacc.yurayura.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.lock.annotation.Lock4j;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysMenuInsertDTO;
import pers.elianacc.yurayura.dto.SysMenuUpdateDTO;
import pers.elianacc.yurayura.entity.SysMenu;
import pers.elianacc.yurayura.service.ISysMenuService;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysMenuTreeVO;

import java.util.List;

/**
 * 系统菜单 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/api/sys/menu")
@Api(tags = "系统菜单API")
public class SysMenuController {

    @Autowired
    private ISysMenuService iSysMenuService;

    /**
     * 查询系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.menu.SysMenu>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统菜单（根据系统菜单id）")
    public ApiResult<SysMenu> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysMenuService.getById(dto.getId()));
    }

    /**
     * 查询系统侧边菜单
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVO>>
     */
    @GetMapping("/getSysSideMenu")
    @ApiOperation("查询系统侧边菜单")
    public ApiResult<List<SysMenuTreeVO>> getSysSideMenu() {
        return ApiResult.success("树形列表查询成功", iSysMenuService.getTreeListByManagerId(StpUtil.getLoginIdAsInt()));
    }

    /**
     * 查询系统菜单树形列表
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<List<SysMenuTreeVO>>
     */
    @GetMapping("/getTreeList")
    @ApiOperation("查询系统菜单树形列表")
    public ApiResult<List<SysMenuTreeVO>> getTreeList() {
        return ApiResult.success("树形列表查询成功", iSysMenuService.getTreeList());
    }

    /**
     * 添加系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.menuName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统菜单")
    public ApiResult<String> insert(@Validated @RequestBody SysMenuInsertDTO dto) {
        iSysMenuService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统菜单
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统菜单")
    public ApiResult<String> update(@Validated @RequestBody SysMenuUpdateDTO dto) {
        iSysMenuService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 删除系统菜单（根据系统菜单id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/deleteById")
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("删除系统菜单（根据系统菜单id）")
    public ApiResult<String> deleteById(@Validated @RequestBody IdDTO dto) {
        iSysMenuService.deleteById(dto);
        return ApiResult.success("删除成功");
    }

}
