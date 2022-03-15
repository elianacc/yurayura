package org.elianacc.yurayura.service.comic.impl;

import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.elianacc.yurayura.dao.comic.ComicMapper;
import org.elianacc.yurayura.dao.comic.ComicUserDataMapper;
import org.elianacc.yurayura.dto.ComicInsertDto;
import org.elianacc.yurayura.dto.ComicSelectDto;
import org.elianacc.yurayura.dto.ComicUpdateDto;
import org.elianacc.yurayura.dto.IdsDto;
import org.elianacc.yurayura.entity.comic.Comic;
import org.elianacc.yurayura.entity.comic.ComicUserData;
import org.elianacc.yurayura.enumerate.ComicStatusEnum;
import org.elianacc.yurayura.enumerate.ImgUploadCategoryEnum;
import org.elianacc.yurayura.enumerate.ImgUploadResultEnum;
import org.elianacc.yurayura.service.comic.IComicService;
import org.elianacc.yurayura.system.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 番剧 service impl
 *
 * @author ELiaNaCc
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
    public PageInfo<Comic> getPage(ComicSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Comic> comicList = comicMapper.getListBySelectDto(dto);
        return new PageInfo<>(comicList, 5);
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.comicName"}, autoRelease = false)
    @SneakyThrows
    @Override
    public String insert(ComicInsertDto dto) {
        String warn = "";
        Integer comicStatus;
        // 更新状态为非完结，且更新时间不为空，更新状态为更新时间
        if (dto.getComicStatus().intValue() == ComicStatusEnum.UPDATING.getStatusId() && !ObjectUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getComicImgFile(), 100, ImgUploadCategoryEnum.COMICIMG.getCategory());

        Comic comic = new Comic();
        ComicUserData comicUserData = new ComicUserData();

        if (imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())) {
            warn = ImgUploadResultEnum.FORMATNOTALLOW.getResult();
        } else if (imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())) {
            warn = ImgUploadResultEnum.SIZEBEYOND.getResult();
        } else {
            BeanUtils.copyProperties(dto, comic);
            comic.setComicStatus(comicStatus);
            // 番剧图片地址使用默认图片
            comic.setComicImageUrl(defaultUplCmImg);
            // 上传图片不为空，番剧图片地址使用上传图片地址
            if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
                comic.setComicImageUrl(imgUplRes);
            }
            comic.setComicCreateTime(LocalDateTime.now());
            comic.setComicUpdateTime(null);
            comicMapper.insert(comic);
            comicUserData.setComicId(comic.getId());
            comicUserData.setComicName(comic.getComicName());
            comicUserData.setComicPlayNum(0);
            comicUserData.setComicFavoriteNum(0);
            comicUserData.setComicUserDataCreateTime(LocalDateTime.now());
            comicUserData.setComicUserDataUpdateTime(null);
            comicUserDataMapper.insert(comicUserData);
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByIds(IdsDto dto) {
        List<Comic> delComicList = comicMapper.selectBatchIds(dto.getIds());
        comicMapper.deleteBatchIds(dto.getIds());
        comicUserDataMapper.deleteBatchByComicId(dto.getIds());
        delComicList.forEach(comic -> {
            // 如果用的是默认图片的，则不删除
            if (!(comic.getComicImageUrl().equals(defaultUplCmImg))) {
                // 删除番剧图片
                FileUtil.fileDelete(comic.getComicImageUrl());
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Lock4j(keys = {"#dto.id"}, autoRelease = false)
    @SneakyThrows
    @Override
    public String update(ComicUpdateDto dto) {
        String warn = "";
        Integer comicStatus;
        // 更新状态为非完结，且更新时间不为空，更新状态为更新时间
        if (dto.getComicStatus().intValue() == ComicStatusEnum.UPDATING.getStatusId() && !ObjectUtils.isEmpty(dto.getComicUdTime())) {
            comicStatus = dto.getComicUdTime();
        } else {
            comicStatus = dto.getComicStatus();
        }

        // 获取图片上传结果
        String imgUplRes = FileUtil.imageUpload(dto.getComicImgFile(), 100, ImgUploadCategoryEnum.COMICIMG.getCategory());

        Comic comic = new Comic();

        if (imgUplRes.equals(ImgUploadResultEnum.FORMATNOTALLOW.getResult())) {
            warn = ImgUploadResultEnum.FORMATNOTALLOW.getResult();
        } else if (imgUplRes.equals(ImgUploadResultEnum.SIZEBEYOND.getResult())) {
            warn = ImgUploadResultEnum.SIZEBEYOND.getResult();
        } else {
            BeanUtils.copyProperties(dto, comic);
            comic.setComicStatus(comicStatus);
            comic.setComicUpdateTime(LocalDateTime.now());
            // 上传图片不为空，番剧图片地址使用上传图片地址
            if (!imgUplRes.equals(ImgUploadResultEnum.NULL.getResult())) {
                comic.setComicImageUrl(imgUplRes);
                Comic aComic = comicMapper.selectById(comic.getId());
                // 如果用的是默认图片的，则不删除
                if (!(aComic.getComicImageUrl().equals(defaultUplCmImg))) {
                    // 删除番剧图片
                    FileUtil.fileDelete(aComic.getComicImageUrl());
                }
            }
            comicMapper.updateById(comic);
        }
        return warn;
    }
}
