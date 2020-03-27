package org.cny.yurayura.controller.sys.dict;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.cny.yurayura.annotation.PreventRepeatSubmit;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.enumerate.DictStatusEnum;
import org.cny.yurayura.service.sys.dict.IDictService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        Dict dict = iDictService.getById(id);
        return ApiResult.success("查询成功", dict);
    }

    /**
     * 分页查询系统数据字典
     *
     * @param pageNum
	 * @param dictSelectDTO
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统数据字典")
    @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "1", required = true, dataType = "int")
    public ApiResult getPage(Integer pageNum, DictSelectDTO dictSelectDTO) {
        if (pageNum == 0) {
            return ApiResult.warn("请输入页数");
        }
        PageInfo<Dict> pageInfo = iDictService.getPage(pageNum, dictSelectDTO);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit(prefix = "sysDictInsert")
    @PostMapping("/insert")
    @ApiOperation("添加系统数据字典")
    public ApiResult insert(Dict dict) {
        iDictService.save(dict);
        return ApiResult.success("添加成功");
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
        List<Integer> delIdList = new ArrayList<>();
        String[] delIdArr = ids.split(",");
        for (String delIdStr : delIdArr) {
            delIdList.add(Integer.parseInt(delIdStr));
        }
        iDictService.removeByIds(delIdList);
        return ApiResult.success("删除成功");
    }

    /**
     * 修改系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit(prefix = "sysDictUpdate")
    @PostMapping("/update")
    @ApiOperation("修改系统数据字典")
    public ApiResult update(Dict dict) {
        iDictService.updateById(dict);
        return ApiResult.success("修改成功");
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
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = iDictService.list(queryWrapper
                .eq("dict_code", dictCode)
                .eq("dict_status", DictStatusEnum.ENABLE.getStatusId())
                .orderByAsc("id"));
        return ApiResult.success("查询成功", dictList);
    }
}

