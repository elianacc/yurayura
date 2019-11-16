package org.cny.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.cny.yurayura.annotation.PreventRepeatSubmit;
import org.cny.yurayura.entity.Comic;
import org.cny.yurayura.enumerate.ComicStatusEnum;
import org.cny.yurayura.service.IComicService;
import org.cny.yurayura.util.FileUtil;
import org.cny.yurayura.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
public class ComicController {

    @Autowired
    private IComicService iComicService;

    /**
     * 分页查询全部番剧
     *
     * @param pageNum
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getPageToAll")
    public Msg getPageToAll(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {

        PageInfo<Comic> comicPageInfo = iComicService.getPageToAll(pageNum);
        if (comicPageInfo.getTotal() != 0) {
            return Msg.success("分页查询成功", comicPageInfo);
        } else {
            return Msg.warn("系统数据为空");
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
    public Msg insert(HttpServletRequest request, Comic comic,
                      @RequestParam(value = "comicstatus", defaultValue = "0") Integer comicstatus,
                      @RequestParam(value = "comicudtime", defaultValue = "8") Integer comicudtime,
                      @RequestParam(value = "cmimgfile", required = false) MultipartFile cmimgfile) throws IOException {
        Integer comicStatus;
        String comicImgUrl;
        boolean result;

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
                // 番剧图片地址使用默认图片
                comicImgUrl = "images/tpjxz.jpg";
                comic.setComicImageUrl(comicImgUrl);
                result = iComicService.save(comic);
                if (result) {
                    return Msg.success("添加成功");
                } else {
                    return Msg.fail("系统错误");
                }
            case "1":
                return Msg.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return Msg.warn("图片不能超过100KB");
            default:
                // 番剧图片地址使用工具类传来的新文件名
                comicImgUrl = "images/" + imgUplRes;
                comic.setComicImageUrl(comicImgUrl);
                result = iComicService.save(comic);
                if (result) {
                    return Msg.success("添加成功");
                } else {
                    return Msg.fail("系统错误");
                }
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
    public Msg deleteById(HttpServletRequest request,
                          @RequestParam(value = "id", defaultValue = "0") Integer id) {
        Comic aComic = iComicService.getById(id);
        boolean result = iComicService.removeById(id);
        if (!StringUtils.isEmpty(aComic) && result) {
            // 如果用的是默认图片的，则不删除
            if (!(aComic.getComicImageUrl().equals("images/tpjxz.jpg"))) {
                // 删除番剧图片
                FileUtil.fileDelete(request, aComic.getComicImageUrl());
            }
            return Msg.success("删除成功");
        } else {
            return Msg.fail("系统错误");
        }
    }

    /**
     * 删除多个番剧
     *
     * @param request
     * @param ids
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/deleteBatchByIds")
    public Msg deleteBatchByIds(HttpServletRequest request, @RequestParam String ids) {
        List<Integer> delIdsList = new ArrayList<>();
        String[] delIdsArr = ids.split(",");
        for (String delIdsStr : delIdsArr) {
            delIdsList.add(Integer.parseInt(delIdsStr));
        }
        Collection<Comic> delComicList = iComicService.listByIds(delIdsList);
        boolean result = iComicService.removeByIds(delIdsList);
        if (!delComicList.isEmpty() && result) {
            for (Comic aComic : delComicList) {
                // 如果用的是默认图片的，则不删除
                if (!(aComic.getComicImageUrl().equals("images/tpjxz.jpg"))) {
                    // 删除番剧图片
                    FileUtil.fileDelete(request, aComic.getComicImageUrl());
                }
            }
            return Msg.success("删除成功");
        } else {
            return Msg.fail("系统错误");
        }
    }

    /**
     * 查询单个番剧
     *
     * @param id
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getOneById")
    public Msg getOneById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        Comic aComic = iComicService.getById(id);
        if (!StringUtils.isEmpty(aComic)) {
            return Msg.success("查询成功", aComic);
        } else {
            return Msg.fail("系统错误");
        }
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
    public Msg update(HttpServletRequest request, Comic comic,
                      @RequestParam(value = "comicstatus", defaultValue = "0") Integer comicstatus,
                      @RequestParam(value = "comicudtime", defaultValue = "8") Integer comicudtime,
                      @RequestParam(value = "cmimgfile", required = false) MultipartFile cmimgfile,
                      @RequestParam(value = "id", defaultValue = "0") Integer id) throws IOException {

        comic.setId(id);

        Integer comicStatus;
        boolean result;

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
                result = iComicService.updateById(comic);
                if (result) {
                    return Msg.success("修改成功");
                } else {
                    return Msg.fail("系统错误");
                }
            case "1":
                return Msg.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
            case "2":
                return Msg.warn("图片不能超过100KB");
            default:
                // 番剧图片地址使用工具类传来的新文件名
                String comicImgUrl = "images/" + imgUplRes;
                comic.setComicImageUrl(comicImgUrl);
                Comic aComic = iComicService.getById(comic.getId());
                result = iComicService.updateById(comic);
                if (!StringUtils.isEmpty(aComic) && result) {
                    // 如果用的是默认图片的，则不删除
                    if (!(aComic.getComicImageUrl().equals("images/tpjxz.jpg"))) {
                        // 删除番剧图片
                        FileUtil.fileDelete(request, aComic.getComicImageUrl());
                    }
                    return Msg.success("修改成功");
                } else {
                    return Msg.fail("系统错误");
                }
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
    public Msg getPageByName(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                             @RequestParam String comicName) {
        PageInfo<Comic> comicPageInfo = iComicService.getPageByName(pageNum, comicName);
        if (comicPageInfo.getTotal() != 0) {
            return Msg.success("分页查询成功", comicPageInfo);
        } else {
            return Msg.warn("搜索不到数据");
        }
    }

}
