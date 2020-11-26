package org.cny.yurayura.controller.comic;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.cny.yurayura.dto.ComicInstAndUpdtDto;
import org.cny.yurayura.dto.ComicSelectDto;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.system.annotation.PreventRepeatSubmit;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 番剧 controller
 *
 * @author CNY
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/comic")
@Api(tags = "番剧API")
public class ComicController {

    @Autowired
    private IComicService iComicService;

    /**
     * 查询番剧（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getById")
    @ApiOperation("查询番剧（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getById(Integer id) {
        if (StringUtils.isEmpty(id)) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iComicService.getById(id));
    }

    /**
     * 分页查询番剧（B端）
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage4B")
    @ApiOperation("分页查询番剧（B端）")
    public ApiResult getPage4B(@RequestBody ComicSelectDto dto) {
        if (StringUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (StringUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); //页记录数默认10
        }
        return iComicService.getPage4B(dto);
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加番剧")
    public ApiResult insert(ComicInstAndUpdtDto dto) throws IOException {
        if (StringUtils.isEmpty(dto.getComicName().trim())) {
            return ApiResult.warn("名称不能为空");
        } else if (StringUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (StringUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        }
        return iComicService.insert(dto);
    }

    /**
     * 批量删除番剧（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/deleteBatchByIds")
    @ApiOperation("批量删除番剧（根据id组）")
    @ApiImplicitParam(name = "ids", value = "id组", required = true)
    public ApiResult deleteBatchByIds(@RequestParam("ids") List<Integer> ids) {
        if (ids.isEmpty()) {
            return ApiResult.warn("id组不能为空");
        }
        return iComicService.deleteBatchByIds(ids);
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/update")
    @ApiOperation("修改番剧")
    public ApiResult update(ComicInstAndUpdtDto dto) throws IOException {
        if (dto.getId() == 0) {
            return ApiResult.warn("id不能为空");
        } else if (StringUtils.isEmpty(dto.getComicName().trim())) {
            return ApiResult.warn("名称不能为空");
        } else if (StringUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (StringUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("上架状态不能为空");
        } else if (dto.getComicContent().length() > 500) {
            return ApiResult.warn("简介不能超过500个字符");
        }
        return iComicService.update(dto);
    }
}
