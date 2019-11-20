package org.cny.yurayura.controller;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.cny.yurayura.annotation.PreventRepeatSubmit;
import org.cny.yurayura.entity.Comic;
import org.cny.yurayura.entity.ComicCount;
import org.cny.yurayura.enumerate.ComicStatusEnum;
import org.cny.yurayura.service.IComicCountService;
import org.cny.yurayura.service.IComicService;
import org.cny.yurayura.util.FileUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
@Api(tags = "番剧相关接口")
public class ComicController {

    @Autowired
    private IComicService iComicService;
    @Autowired
    private IComicCountService iComicCountService;

    @Value("${yurayura.default-upload.image}")
    private String defaultUplImg;

    /**
     * 分页查询全部番剧
     *
     * @param pageNum
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getPageToAll")
    @ApiOperation("分页查询全部番剧")
    @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "1", required = true)
    public ApiResult getPageToAll(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
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
     * @param request
     * @param comic
     * @param comicstatus
     * @param comicudtime
     * @param cmimgfile
     * @return org.cny.yurayura.vo.Msg
     */
    @PreventRepeatSubmit
    @PostMapping("/insert")
    @ApiOperation("添加番剧")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comicstatus", value = "番剧状态", required = true),
            @ApiImplicitParam(name = "comicudtime", value = "番剧更新时间", defaultValue = "8"),
            @ApiImplicitParam(name = "cmimgfile", value = "番剧图片")
    })
    public ApiResult insert(HttpServletRequest request, Comic comic,
                            @RequestParam(value = "comicstatus", defaultValue = "0") Integer comicstatus,
                            @RequestParam(value = "comicudtime", defaultValue = "8") Integer comicudtime,
                            @RequestParam(value = "cmimgfile", required = false) MultipartFile cmimgfile) throws IOException {
        Integer comicStatus;
        String comicImgUrl;

        // 有更新时间赋值更新时间，没有则赋值更新状态
        if (comicudtime != ComicStatusEnum.UPDATING.getStatusId()) {
            comicStatus = comicudtime;
        } else {
            comicStatus = comicstatus;
        }
        comic.setComicStatus(comicStatus);

        // 调用图片上传工具
        String imgUplRes = FileUtil.imageUpload(request, cmimgfile);

        ComicCount comicCount = new ComicCount();

        switch (imgUplRes) {
            case "0":
                // 番剧图片地址使用默认图片
                comicImgUrl = defaultUplImg;
                comic.setComicImageUrl(comicImgUrl);
                iComicService.save(comic);
                comicCount.setComicId(comic.getId());
                comicCount.setComicName(comic.getComicName());
                comicCount.setComicEpisodes(24);
                comicCount.setComicNowEpisodes(1);
                comicCount.setComicViews(0);
                comicCount.setComicFavoriteNum(0);
                iComicCountService.save(comicCount);
                return ApiResult.success("添加成功");
            case "1":
                return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return ApiResult.warn("图片不能超过100KB");
            default:
                // 番剧图片地址使用工具类传来的新文件名
                comicImgUrl = "images/" + imgUplRes;
                comic.setComicImageUrl(comicImgUrl);
                iComicService.save(comic);
                comicCount.setComicId(comic.getId());
                comicCount.setComicName(comic.getComicName());
                comicCount.setComicEpisodes(24);
                comicCount.setComicNowEpisodes(1);
                comicCount.setComicViews(0);
                comicCount.setComicFavoriteNum(0);
                iComicCountService.save(comicCount);
                return ApiResult.success("添加成功");
        }

    }

    /**
     * 删除单个番剧
     *
     * @param request
     * @param id
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/deleteById")
    @ApiOperation("删除单个番剧")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public ApiResult deleteById(HttpServletRequest request,
                                @RequestParam(value = "id", defaultValue = "0") Integer id) {
        Comic comic = iComicService.getById(id);
        iComicService.removeById(id);
        // 如果用的是默认图片的，则不删除
        if (!(comic.getComicImageUrl().equals(defaultUplImg))) {
            // 删除番剧图片
            FileUtil.fileDelete(request, comic.getComicImageUrl());
        }
        iComicCountService.deleteByComicId(comic.getId());
        return ApiResult.success("删除成功");
    }

    /**
     * 删除多个番剧
     *
     * @param request
     * @param ids
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/deleteBatchByIds")
    @ApiOperation("删除多个番剧")
    @ApiImplicitParam(name = "ids", value = "id组", required = true)
    public ApiResult deleteBatchByIds(HttpServletRequest request, @RequestParam String ids) {
        List<Integer> delIdsList = new ArrayList<>();
        String[] delIdsArr = ids.split(",");
        for (String delIdsStr : delIdsArr) {
            delIdsList.add(Integer.parseInt(delIdsStr));
        }
        Collection<Comic> delComicList = iComicService.listByIds(delIdsList);
        iComicService.removeByIds(delIdsList);
        for (Comic comic : delComicList) {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(request, comic.getComicImageUrl());
            }
            iComicCountService.deleteByComicId(comic.getId());
        }
        return ApiResult.success("删除成功");
    }

    /**
     * 查询单个番剧
     *
     * @param id
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getOneById")
    @ApiOperation("查询单个番剧")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public ApiResult getOneById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        Comic comic = iComicService.getById(id);
        return ApiResult.success("查询成功", comic);
    }

    /**
     * 修改番剧
     *
     * @param request
     * @param comic
     * @param comicstatus
     * @param comicudtime
     * @param cmimgfile
     * @param id
     * @return org.cny.yurayura.vo.Msg
     */
    @PreventRepeatSubmit
    @PostMapping("/update")
    @ApiOperation("修改番剧")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comicstatus", value = "番剧状态", required = true),
            @ApiImplicitParam(name = "comicudtime", value = "番剧更新时间", defaultValue = "8"),
            @ApiImplicitParam(name = "cmimgfile", value = "番剧图片"),
            @ApiImplicitParam(name = "id", value = "id", required = true)
    })
    public ApiResult update(HttpServletRequest request, Comic comic,
                            @RequestParam(value = "comicstatus", defaultValue = "0") Integer comicstatus,
                            @RequestParam(value = "comicudtime", defaultValue = "8") Integer comicudtime,
                            @RequestParam(value = "cmimgfile", required = false) MultipartFile cmimgfile,
                            @RequestParam(value = "id", defaultValue = "0") Integer id) throws IOException {

        comic.setId(id);

        Integer comicStatus;

        // 有更新时间赋值更新时间，没有则赋值更新状态
        if (comicudtime != ComicStatusEnum.UPDATING.getStatusId()) {
            comicStatus = comicudtime;
        } else {
            comicStatus = comicstatus;
        }
        comic.setComicStatus(comicStatus);

        // 调用图片上传工具
        String imgUplRes = FileUtil.imageUpload(request, cmimgfile);
        switch (imgUplRes) {
            case "0":
                iComicService.updateById(comic);
                return ApiResult.success("修改成功");
            case "1":
                return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return ApiResult.warn("图片不能超过100KB");
            default:
                // 番剧图片地址使用工具类传来的新文件名
                String comicImgUrl = "images/" + imgUplRes;
                comic.setComicImageUrl(comicImgUrl);
                Comic aComic = iComicService.getById(comic.getId());
                iComicService.updateById(comic);
                // 如果用的是默认图片的，则不删除
                if (!(aComic.getComicImageUrl().equals(defaultUplImg))) {
                    // 删除番剧图片
                    FileUtil.fileDelete(request, aComic.getComicImageUrl());
                }
                return ApiResult.success("修改成功");
        }

    }

    /**
     * 搜索番剧
     *
     * @param pageNum
     * @param comicName
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getPageByName")
    @ApiOperation("搜索番剧")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comicName", value = "番剧名"),
            @ApiImplicitParam(name = "pageNum", value = "当前页数", defaultValue = "1", required = true)
    })
    public ApiResult getPageByName(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                   @RequestParam String comicName) {
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
