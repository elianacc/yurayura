package org.cny.yurayura.controller.comic;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.cny.yurayura.annotation.PreventRepeatSubmit;
import org.cny.yurayura.dto.ComicInstAndUpdtDTO;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.entity.comic.ComicUserData;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.service.comic.IComicUserDataService;
import org.cny.yurayura.util.FileUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 番剧 controller
 * </p>
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
    @Autowired
    private IComicUserDataService iComicUserDataService;

    @Value("${yurayura.default-upload.comic-image}")
    private String defaultUplCmImg;

    /**
     * 分页查询全部番剧
     *
     * @param pageNum
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPageToAll")
    @ApiOperation("分页查询全部番剧")
    @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "1", required = true, dataType = "int")
    public ApiResult getPageToAll(Integer pageNum) {
        if (pageNum == 0) {
            return ApiResult.warn("请输入页数");
        }
        PageInfo<Comic> comicPageInfo = iComicService.getPageToAll(pageNum);
        if (comicPageInfo.getTotal() != 0) {
            return ApiResult.success("分页查询成功", comicPageInfo);
        } else {
            return ApiResult.warn("系统数据为空");
        }
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
    public ApiResult insert(ComicInstAndUpdtDTO dto,
                            @ApiParam(value = "图片文件") @RequestParam(value = "cmImgFile", required = false) MultipartFile cmImgFile) throws IOException {

        Integer comicStatus;
        // 有更新时间赋值更新时间，没有则赋值更新状态
        if (!StringUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 调用图片上传工具
        String imgUplRes = FileUtil.imageUpload(cmImgFile);

        Comic comic = new Comic();
        ComicUserData comicUserData = new ComicUserData();

        switch (imgUplRes) {
            case "0":
                BeanUtils.copyProperties(dto, comic);
                comic.setComicStatus(comicStatus);
                // 番剧图片地址使用默认图片
                comic.setComicImageUrl(defaultUplCmImg);
                comic.setComicCreateTime(LocalDateTime.now());
                iComicService.save(comic);
                comicUserData.setComicId(comic.getId());
                comicUserData.setComicName(comic.getComicName());
                comicUserData.setComicPlayNum(0);
                comicUserData.setComicFavoriteNum(0);
                comicUserData.setComicUserDataCreateTime(LocalDateTime.now());
                iComicUserDataService.save(comicUserData);
                return ApiResult.success("添加成功");
            case "1":
                return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return ApiResult.warn("图片不能超过100KB");
            default:
                BeanUtils.copyProperties(dto, comic);
                comic.setComicStatus(comicStatus);
                // 番剧图片地址使用工具类传来的新文件名
                comic.setComicImageUrl("upload/" + imgUplRes);
                comic.setComicCreateTime(LocalDateTime.now());
                iComicService.save(comic);
                comicUserData.setComicId(comic.getId());
                comicUserData.setComicName(comic.getComicName());
                comicUserData.setComicPlayNum(0);
                comicUserData.setComicFavoriteNum(0);
                comicUserData.setComicUserDataCreateTime(LocalDateTime.now());
                iComicUserDataService.save(comicUserData);
                return ApiResult.success("添加成功");
        }

    }

    /**
     * 删除番剧（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/deleteById")
    @ApiOperation("删除番剧（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int")
    public ApiResult deleteById(Integer id) {
        Comic comic = iComicService.getById(id);
        iComicService.removeById(id);
        // 如果用的是默认图片的，则不删除
        if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
            // 删除番剧图片
            FileUtil.fileDelete(comic.getComicImageUrl());
        }
        iComicUserDataService.deleteByComicId(comic.getId());
        return ApiResult.success("删除成功");
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
        List<Integer> delIdsList = new ArrayList<>();
        String[] delIdsArr = ids.split(",");
        for (String delIdsStr : delIdsArr) {
            delIdsList.add(Integer.parseInt(delIdsStr));
        }
        List<Comic> delComicList = iComicService.listByIds(delIdsList);
        iComicService.removeByIds(delIdsList);
        for (Comic comic : delComicList) {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(comic.getComicImageUrl());
            }
            iComicUserDataService.deleteByComicId(comic.getId());
        }
        return ApiResult.success("删除成功");
    }

    /**
     * 查询番剧（根据id）
     *
     * @param id
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getOneById")
    @ApiOperation("查询番剧（根据id）")
    @ApiImplicitParam(name = "id", value = "id", required = true, defaultValue = "1", dataType = "int")
    public ApiResult getOneById(Integer id) {
        Comic comic = iComicService.getById(id);
        return ApiResult.success("查询成功", comic);
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
    public ApiResult update(ComicInstAndUpdtDTO dto,
                            @ApiParam(value = "图片文件") @RequestParam(value = "cmImgFile", required = false) MultipartFile cmImgFile) throws IOException {

        Integer comicStatus;
        // 有更新时间赋值更新时间，没有则赋值更新状态
        if (!StringUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 调用图片上传工具
        String imgUplRes = FileUtil.imageUpload(cmImgFile);

        Comic comic = new Comic();

        switch (imgUplRes) {
            case "0":
                BeanUtils.copyProperties(dto, comic);
                comic.setComicStatus(comicStatus);
                comic.setComicUpdateTime(LocalDateTime.now());
                iComicService.updateById(comic);
                return ApiResult.success("修改成功");
            case "1":
                return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return ApiResult.warn("图片不能超过100KB");
            default:
                BeanUtils.copyProperties(dto, comic);
                Comic aComic = iComicService.getById(comic.getId());
                comic.setComicStatus(comicStatus);
                // 番剧图片地址使用工具类传来的新文件名
                comic.setComicImageUrl("upload/" + imgUplRes);
                comic.setComicUpdateTime(LocalDateTime.now());
                iComicService.updateById(comic);
                // 如果用的是默认图片的，则不删除
                if (!(aComic.getComicImageUrl().equals(defaultUplCmImg))) {
                    // 删除番剧图片
                    FileUtil.fileDelete(aComic.getComicImageUrl());
                }
                return ApiResult.success("修改成功");
        }

    }

    /**
     * 搜索番剧
     *
     * @param pageNum
     * @param comicName
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/getPageByName")
    @ApiOperation("搜索番剧")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comicName", value = "番剧名", required = true),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "1", required = true, dataType = "int")
    })
    public ApiResult getPageByName(Integer pageNum, String comicName) {
        if (pageNum == 0) {
            return ApiResult.warn("请输入页数");
        }
        PageInfo<Comic> comicPageInfo = iComicService.getPageByName(pageNum, comicName);
        if (comicPageInfo.getTotal() != 0) {
            return ApiResult.success("分页查询成功", comicPageInfo);
        } else {
            return ApiResult.warn("搜索不到数据");
        }
    }

}
