package org.cny.yurayura.controller.comic;


import io.swagger.annotations.*;
import org.cny.yurayura.annotation.PreventRepeatSubmit;
import org.cny.yurayura.dto.ComicInstAndUpdtDTO;
import org.cny.yurayura.dto.ComicSelectDTO;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * @param pageNum
	 * @param pageSize
	 * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPageToB")
    @ApiOperation("分页查询番剧（B端）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页记录数", defaultValue = "10", dataType = "int")
    })
    public ApiResult getPageToB(Integer pageNum, Integer pageSize, ComicSelectDTO dto) {
        if (StringUtils.isEmpty(pageNum)) {
            return ApiResult.warn("页码不能为空");
        } else if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10; //页记录数默认10
        }
        return iComicService.getPageToB(pageNum, pageSize, dto);
    }

    /**
     * 添加番剧
     *
     * @param dto
     * @param cmImgFile
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit(prefix = "comicInsert")
    @PostMapping("/insert")
    @ApiOperation("添加番剧")
    public ApiResult insert(ComicInstAndUpdtDTO dto
            , @ApiParam(value = "图片文件") @RequestParam(value = "cmImgFile", required = false) MultipartFile cmImgFile) {
        if (StringUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("番剧名不能为空");
        } else if (StringUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("番剧状态不能为空");
        } else if (StringUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("番剧上架状态不能为空");
        }
        return iComicService.insert(dto, cmImgFile);
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
    public ApiResult deleteBatchByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ApiResult.warn("id组不能为空");
        }
        return iComicService.deleteBatchByIds(ids);
    }

    /**
     * 修改番剧
     *
     * @param dto
     * @param cmImgFile
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit(prefix = "comicUpdate")
    @PostMapping("/update")
    @ApiOperation("修改番剧")
    public ApiResult update(ComicInstAndUpdtDTO dto
            , @ApiParam(value = "图片文件") @RequestParam(value = "cmImgFile", required = false) MultipartFile cmImgFile) {
        if (StringUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (StringUtils.isEmpty(dto.getComicName())) {
            return ApiResult.warn("番剧名不能为空");
        } else if (StringUtils.isEmpty(dto.getComicStatus())) {
            return ApiResult.warn("番剧状态不能为空");
        } else if (StringUtils.isEmpty(dto.getComicShelfStatus())) {
            return ApiResult.warn("番剧上架状态不能为空");
        }
        return iComicService.update(dto, cmImgFile);
    }
}
