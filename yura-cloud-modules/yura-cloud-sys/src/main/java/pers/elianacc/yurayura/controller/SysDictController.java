package pers.elianacc.yurayura.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.SysDictInsertDTO;
import pers.elianacc.yurayura.dto.SysDictSelectDTO;
import pers.elianacc.yurayura.dto.SysDictUpdateDTO;
import pers.elianacc.yurayura.entity.SysDict;
import pers.elianacc.yurayura.service.ISysDictService;
import pers.elianacc.yurayura.vo.ApiResult;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 系统数据字典 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/api/sys/dict")
@Api(tags = "系统数据字典API")
@Validated
public class SysDictController {

    @Autowired
    private ISysDictService iSysDictService;

    /**
     * 查询系统数据字典（根据系统数据字典id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.dict.SysDict>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统数据字典（根据系统数据字典id）")
    public ApiResult<SysDict> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysDictService.getById(dto.getId()));
    }

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<PageInfo<SysDict>>
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统数据字典")
    public ApiResult<PageInfo<SysDict>> getPage(@Validated @RequestBody SysDictSelectDTO dto) {
        return ApiResult.success("分页查询成功", iSysDictService.getPage(dto));
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"#dto.dictCode", "#dto.dictVal"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统数据字典")
    public ApiResult<String> insert(@Validated @RequestBody SysDictInsertDTO dto) {
        iSysDictService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统数据字典")
    public ApiResult<String> update(@Validated @RequestBody SysDictUpdateDTO dto) {
        iSysDictService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getByDictCode")
    @ApiOperation("查询系统数据字典（根据字典编码）")
    @ApiImplicitParam(name = "dictCode", value = "字典编码", required = true, dataTypeClass = String.class)
    public ApiResult<List<SysDict>> getByDictCode(@NotBlank(message = "字典编码不能为空") @RequestParam String dictCode) {
        return ApiResult.success("查询成功", iSysDictService.getByDictCode(dictCode));
    }

    /**
     * 查询所有系统数据字典
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.util.List<pers.elianacc.yurayura.entity.sys.dict.SysDict>>
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统数据字典")
    public ApiResult<List<SysDict>> getAll() {
        return ApiResult.success("查询成功", iSysDictService.getAll());
    }

}
