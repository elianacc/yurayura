package org.cny.yurayura.service.comic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.comic.ComicMapper;
import org.cny.yurayura.dto.ComicSelectDTO;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.service.comic.IComicService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ApiResult getPageToB(Integer pageNum, ComicSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(pageNum, 10);
        QueryWrapper<Comic> queryWrapper = new QueryWrapper<>();
        List<Comic> comicList = comicMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto.getSelectComicName()), "comic_name", dto.getSelectComicName())
                .eq(!StringUtils.isEmpty(dto.getSelectComicStatus())
                        , "comic_status", dto.getSelectComicStatus())
                .eq(!StringUtils.isEmpty(dto.getSelectComicShelfStatus())
                        , "comic_shelf_status", dto.getSelectComicShelfStatus())
                .orderByDesc("id"));
        PageInfo<Comic> pageInfo = new PageInfo<>(comicList, 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }
}
