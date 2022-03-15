package org.elianacc.yurayura.controller.sys.dict;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.SysDictInsertDto;
import org.elianacc.yurayura.dto.SysDictSelectDto;
import org.elianacc.yurayura.dto.SysDictUpdateDto;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.entity.sys.dict.SysDict;
import org.elianacc.yurayura.service.sys.dict.ISysDictService;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统数据字典 controller
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/api/sys/dict")
@Api(tags = "系统数据字典API")
public class SysDictController {

    @Autowired
    private ISysDictService iSysDictService;

    /**
     * 查询系统数据字典（根据系统数据字典id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统数据字典（根据系统数据字典id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysDictService.getById(dto.getId()));
    }

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统数据字典")
    public ApiResult getPage(@RequestBody SysDictSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<SysDict> pageInfo = iSysDictService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    @ApiOperation("添加系统数据字典")
    public ApiResult insert(@RequestBody SysDictInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getDictCode())) {
            return ApiResult.warn("字典编码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictName())) {
            return ApiResult.warn("字典名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictVal())) {
            return ApiResult.warn("字典值不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dto.getDictCode().length() > 20 || dto.getDictName().length() > 20 || dto.getDictVal().length() > 20) {
            return ApiResult.warn("字典编码、字典名、字典值不能超过20个字符");
        } else if (!dto.getDictCode().matches("^[a-z][A-Za-z]*$")) {
            return ApiResult.warn("字典编码只能包含字母，以小写字母开头");
        }
        String warn = iSysDictService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    @ApiOperation("修改系统数据字典")
    public ApiResult update(@RequestBody SysDictUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictName())) {
            return ApiResult.warn("字典名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getDictSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dto.getDictName().length() > 20) {
            return ApiResult.warn("字典名不能超过20个字符");
        }
        String warn = iSysDictService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getByDictCode")
    @ApiOperation("查询系统数据字典（根据字典编码）")
    @ApiImplicitParam(name = "dictCode", value = "字典编码", required = true, dataTypeClass = String.class)
    public ApiResult getByDictCode(String dictCode) {
        if (ObjectUtils.isEmpty(dictCode)) {
            return ApiResult.warn("字典编码不能为空");
        }
        List<SysDict> sysDictList = iSysDictService.getByDictCode(dictCode);
        if (sysDictList.isEmpty()) {
            return ApiResult.warn("字典编码：" + dictCode + "对应系统数据字典为空");
        }
        return ApiResult.success("查询成功", sysDictList);
    }

    /**
     * 查询所有系统数据字典
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getAll")
    @ApiOperation("查询所有系统数据字典")
    public ApiResult getAll() {
        List<SysDict> sysDictList = iSysDictService.getAll();
        if (sysDictList.isEmpty()) {
            return ApiResult.warn("系统数据字典在redis中不存在，请添加");
        }
        return ApiResult.success("查询成功", sysDictList);
    }
}

