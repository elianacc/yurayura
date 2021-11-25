package org.elianacc.yurayura.controller.sys.dict;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.DictSelectDto;
import org.elianacc.yurayura.entity.sys.dict.Dict;
import org.elianacc.yurayura.service.sys.dict.IDictService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
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
public class DictController {

    @Autowired
    private IDictService iDictService;

    /**
     * 查询系统数据字典（根据id）
     *
     * @param id
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统数据字典（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iDictService.getById(id));
    }

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getPage")
    @ApiOperation("分页查询系统数据字典")
    public ApiResult getPage(DictSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Dict> pageInfo = iDictService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统数据字典
     *
     * @param dict
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统数据字典")
    public ApiResult insert(@RequestBody Dict dict) {
        if (ObjectUtils.isEmpty(dict.getDictCode())) {
            return ApiResult.warn("字典编码不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictName())) {
            return ApiResult.warn("字典名不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictVal())) {
            return ApiResult.warn("字典值不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dict.getDictCode().length() > 20 || dict.getDictName().length() > 20 || dict.getDictVal().length() > 20) {
            return ApiResult.warn("字典编码、字典名、字典值不能超过20个字符");
        } else if (!dict.getDictCode().matches("^[a-z][A-Za-z]*$")) {
            return ApiResult.warn("字典编码只能包含字母，以小写字母开头");
        }
        String warn = iDictService.insert(dict);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统数据字典
     *
     * @param dict
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改系统数据字典")
    public ApiResult update(@RequestBody Dict dict) {
        if (ObjectUtils.isEmpty(dict.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictCode())) {
            return ApiResult.warn("字典编码不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictName())) {
            return ApiResult.warn("字典名不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictVal())) {
            return ApiResult.warn("字典值不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dict.getDictSeq())) {
            return ApiResult.warn("序号不能为空");
        } else if (dict.getDictCode().length() > 20 || dict.getDictName().length() > 20 || dict.getDictVal().length() > 20) {
            return ApiResult.warn("字典编码、字典名、字典值不能超过20个字符");
        } else if (!dict.getDictCode().matches("^[a-z][A-Za-z]*$")) {
            return ApiResult.warn("字典编码只能包含字母，以小写字母开头");
        }
        String warn = iDictService.update(dict);
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
    @ApiImplicitParam(name = "dictCode", value = "字典编码", required = true)
    public ApiResult getByDictCode(String dictCode) {
        if (ObjectUtils.isEmpty(dictCode)) {
            return ApiResult.warn("字典编码不能为空");
        }
        List<Dict> dictList = iDictService.getByDictCode(dictCode);
        if (dictList.isEmpty()) {
            return ApiResult.warn("字典编码：" + dictCode + "对应系统数据字典为空");
        }
        return ApiResult.success("查询成功", dictList);
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
        List<Dict> dictList = iDictService.getAll();
        if (dictList.isEmpty()) {
            return ApiResult.warn("系统数据字典在redis中不存在，请添加");
        }
        return ApiResult.success("查询成功", dictList);
    }
}

