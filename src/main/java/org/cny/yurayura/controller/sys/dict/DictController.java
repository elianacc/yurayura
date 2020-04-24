package org.cny.yurayura.controller.sys.dict;


import io.swagger.annotations.*;
import org.cny.yurayura.system.annotation.PreventRepeatSubmit;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.cny.yurayura.service.sys.dict.IDictService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统数据字典 controller
 *
 * @author CNY
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/sys/dict")
@Api(tags = "系统数据字典API")
public class DictController {

    @Autowired
    private IDictService iDictService;

    /**
     * 查询系统数据字典（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getById")
    @ApiOperation("查询系统数据字典（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iDictService.getById(id));
    }

    /**
     * 分页查询系统数据字典
     *
     * @param pageNum
     * @param pageSize
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统数据字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", defaultValue = "20", dataType = "int")
    })
    public ApiResult getPage(Integer pageNum, Integer pageSize, DictSelectDTO dto) {
        if (StringUtils.isEmpty(pageNum)) {
            return ApiResult.warn("页码不能为空");
        } else if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10; //页记录数默认10
        }
        return iDictService.getPage(pageNum, pageSize, dto);
    }

    /**
     * 添加系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加系统数据字典")
    public ApiResult insert(Dict dict) {
        if (StringUtils.isEmpty(dict.getDictCode().trim())) {
            return ApiResult.warn("字典编码不能为空");
        } else if (StringUtils.isEmpty(dict.getDictName().trim())) {
            return ApiResult.warn("字典名不能为空");
        } else if (StringUtils.isEmpty(dict.getDictVal().trim())) {
            return ApiResult.warn("字典值不能为空");
        } else if (StringUtils.isEmpty(dict.getDictStatus())) {
            return ApiResult.warn("字典状态不能为空");
        }
        return iDictService.insert(dict);
    }

    /**
     * 批量删除系统数据字典（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/deleteBatchByIds")
    @ApiOperation("批量删除系统数据字典（根据id组）")
    @ApiImplicitParam(name = "ids", value = "id组", required = true)
    public ApiResult deleteBatchByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ApiResult.warn("id组不能为空");
        }
        return iDictService.deleteBatchByIds(ids);
    }

    /**
     * 修改系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/update")
    @ApiOperation("修改系统数据字典")
    public ApiResult update(Dict dict) {
        if (StringUtils.isEmpty(dict.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (StringUtils.isEmpty(dict.getDictCode().trim())) {
            return ApiResult.warn("字典编码不能为空");
        } else if (StringUtils.isEmpty(dict.getDictName().trim())) {
            return ApiResult.warn("字典名不能为空");
        } else if (StringUtils.isEmpty(dict.getDictVal().trim())) {
            return ApiResult.warn("字典值不能为空");
        } else if (StringUtils.isEmpty(dict.getDictStatus())) {
            return ApiResult.warn("字典状态不能为空");
        }
        return iDictService.update(dict);
    }

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getByDictCode")
    @ApiOperation("查询系统数据字典（根据字典编码）")
    @ApiImplicitParam(name = "dictCode", value = "字典编码", required = true)
    public ApiResult getByDictCode(String dictCode) {
        if (StringUtils.isEmpty(dictCode.trim())) {
            return ApiResult.warn("字典编码不能为空");
        }
        return iDictService.getByDictCode(dictCode);
    }
}

