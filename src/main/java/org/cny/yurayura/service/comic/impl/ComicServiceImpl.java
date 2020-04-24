package org.cny.yurayura.service.comic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.cny.yurayura.dao.comic.ComicMapper;
import org.cny.yurayura.dao.comic.ComicUserDataMapper;
import org.cny.yurayura.dto.ComicInstAndUpdtDTO;
import org.cny.yurayura.dto.ComicSelectDTO;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.entity.comic.ComicUserData;
import org.cny.yurayura.enumerate.ImgUploadResultEnum;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.system.util.FileUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 番剧 service impl
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class ComicServiceImpl extends ServiceImpl<ComicMapper, Comic> implements IComicService {

    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private ComicUserDataMapper comicUserDataMapper;

    @Value("${yurayura.default-upload.comic-image}")
    private String defaultUplCmImg;

    @Override
    public ApiResult getPageToB(Integer pageNum, Integer pageSize, ComicSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Comic> queryWrapper = new QueryWrapper<>();
        List<Comic> comicList = comicMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto.getComicName()), "comic_name", dto.getComicName())
                .eq(!StringUtils.isEmpty(dto.getComicStatus())
                        , "comic_status", dto.getComicStatus())
                .eq(!StringUtils.isEmpty(dto.getComicShelfStatus())
                        , "comic_shelf_status", dto.getComicShelfStatus())
                .orderByDesc("id"));
        PageInfo<Comic> pageInfo = new PageInfo<>(comicList, 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    @Override
    public ApiResult insert(ComicInstAndUpdtDTO dto, MultipartFile cmImgFile) {
        // 有更新时间,更新状态为更新时间
        Integer comicStatus = StringUtils.isEmpty(dto.getComicUdTime()) ? dto.getComicStatus() : dto.getComicUdTime();

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(cmImgFile);

        Comic comic = new Comic();
        ComicUserData comicUserData = new ComicUserData();

        if (imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())) {
            return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
        } else if (imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())) {
            return ApiResult.warn("图片不能超过100KB");
        } else {
            BeanUtils.copyProperties(dto, comic);
            comic.setComicStatus(comicStatus);
            // 番剧图片地址使用默认图片
            comic.setComicImageUrl(defaultUplCmImg);
            // 上传图片不为空，番剧图片地址使用上传图片地址
            if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
                comic.setComicImageUrl("/upload/" + imgUplRes);
            }
            comic.setComicCreateTime(LocalDateTime.now());
            comic.setComicUpdateTime(LocalDateTime.now());
            comicMapper.insert(comic);
            comicUserData.setComicId(comic.getId());
            comicUserData.setComicName(comic.getComicName());
            comicUserData.setComicPlayNum(0);
            comicUserData.setComicFavoriteNum(0);
            comicUserData.setComicUserDataCreateTime(LocalDateTime.now());
            comicUserData.setComicUserDataUpdateTime(LocalDateTime.now());
            comicUserDataMapper.insert(comicUserData);
            return ApiResult.success("添加成功");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult deleteBatchByIds(String ids) {
        List<Integer> delIdList = new ArrayList<>();
        String[] delIdArr = ids.split(",");
        for (String delIdStr : delIdArr) {
            delIdList.add(Integer.parseInt(delIdStr));
        }
        List<Comic> delComicList = comicMapper.selectBatchIds(delIdList);
        comicMapper.deleteBatchIds(delIdList);
        comicUserDataMapper.deleteBatchByComicId(delIdList);
        for (Comic comic : delComicList) {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(comic.getComicImageUrl());
            }
        }
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    @Override
    public ApiResult update(ComicInstAndUpdtDTO dto, MultipartFile cmImgFile) {
        // 有更新时间,更新状态为更新时间
        Integer comicStatus = StringUtils.isEmpty(dto.getComicUdTime()) ? dto.getComicStatus() : dto.getComicUdTime();

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(cmImgFile);

        Comic comic = new Comic();

        if (imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())) {
            return ApiResult.warn("图片格式必须是.gif,jpeg,jpg,png中的一种");
        } else if (imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())) {
            return ApiResult.warn("图片不能超过100KB");
        } else {
            BeanUtils.copyProperties(dto, comic);
            comic.setComicStatus(comicStatus);
            comic.setComicUpdateTime(LocalDateTime.now());
            // 上传图片不为空，番剧图片地址使用上传图片地址
            if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
                comic.setComicImageUrl("/upload/" + imgUplRes);
                Comic aComic = comicMapper.selectById(comic.getId());
                // 如果用的是默认图片的，则不删除
                if (!(aComic.getComicImageUrl().equals(defaultUplCmImg))) {
                    // 删除番剧图片
                    FileUtil.fileDelete(aComic.getComicImageUrl());
                }
            }
            comicMapper.updateById(comic);
            return ApiResult.success("修改成功");
        }
    }
}
