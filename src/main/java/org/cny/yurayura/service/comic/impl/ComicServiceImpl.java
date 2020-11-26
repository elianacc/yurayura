package org.cny.yurayura.service.comic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.comic.ComicMapper;
import org.cny.yurayura.dao.comic.ComicUserDataMapper;
import org.cny.yurayura.dto.ComicInstAndUpdtDto;
import org.cny.yurayura.dto.ComicSelectDto;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.entity.comic.ComicUserData;
import org.cny.yurayura.enumerate.ComicStatusEnum;
import org.cny.yurayura.enumerate.ImgUploadResultEnum;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.system.util.FileUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
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
    public ApiResult getPage4B(ComicSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Comic> comicList = comicMapper.getListBySelectDto(dto);
        PageInfo<Comic> pageInfo = new PageInfo<>(comicList, 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult insert(ComicInstAndUpdtDto dto) throws IOException {
        // 更新状态为非完结，更新状态为更新时间
        Integer comicStatus = dto.getComicStatus().intValue() == ComicStatusEnum.FINISHED.getStatusId() ? dto.getComicStatus() : dto.getComicUdTime();

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getCmImgFile());

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
    public ApiResult deleteBatchByIds(List<Integer> ids) {
        List<Comic> delComicList = comicMapper.selectBatchIds(ids);
        comicMapper.deleteBatchIds(ids);
        comicUserDataMapper.deleteBatchByComicId(ids);
        delComicList.forEach(comic -> {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(comic.getComicImageUrl());
            }
        });
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult update(ComicInstAndUpdtDto dto) throws IOException {
        // 更新状态为非完结，更新状态为更新时间
        Integer comicStatus = dto.getComicStatus().intValue() == ComicStatusEnum.FINISHED.getStatusId() ? dto.getComicStatus() : dto.getComicUdTime();

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getCmImgFile());

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
