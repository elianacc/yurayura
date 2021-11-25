package org.elianacc.yurayura.controller.comic;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elianacc.yurayura.dto.ComicInstAndUpdtDto;
import org.elianacc.yurayura.dto.ComicSelectDto;
import org.elianacc.yurayura.entity.comic.Comic;
import org.elianacc.yurayura.service.comic.IComicService;
import org.elianacc.yurayura.system.annotation.PreventRepeatSubmit;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 番剧 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/api/comic")
@Api(tags = "番剧API")
public class ComicController {

    @Autowired
    private IComicService iComicService;

    /**
     * 查询番剧（根据id）
     *
     * @param id
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询番剧（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicService.getById(id));
    }

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getPage")
    @ApiOperation("分页查询番剧")
    public ApiResult getPage(ComicSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Comic> pageInfo = iComicService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加番剧")
    public ApiResult insert(ComicInstAndUpdtDto dto) {
        if (ObjectUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicTime())) {
            return ApiResult.warn("放送时间不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        } else if (dto.getComicName().length() > 30) {
            return ApiResult.warn("名称不能超过30个字符");
        }
        String warn = iComicService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 批量删除番剧（根据id组）
     *
     * @param ids
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @DeleteMapping("/deleteBatchByIds")
    @ApiOperation("批量删除番剧（根据id组）")
    @ApiImplicitParam(name = "ids", value = "id组", required = true)
    public ApiResult deleteBatchByIds(@RequestParam("ids") List<Integer> ids) {
        if (ids.isEmpty()) {
            return ApiResult.warn("id组不能为空");
        }
        iComicService.deleteBatchByIds(ids);
        return ApiResult.success("删除成功");
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PutMapping("/update")
    @ApiOperation("修改番剧")
    public ApiResult update(ComicInstAndUpdtDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("名称不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicTime())) {
            return ApiResult.warn("放送时间不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (ObjectUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        } else if (dto.getComicName().length() > 30) {
            return ApiResult.warn("名称不能超过30个字符");
        }
        String warn = iComicService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("修改成功");
    }
}
