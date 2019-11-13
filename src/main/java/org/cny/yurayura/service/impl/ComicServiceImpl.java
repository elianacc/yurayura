package org.cny.yurayura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.ComicMapper;
import org.cny.yurayura.entity.Comic;
import org.cny.yurayura.service.IComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 番剧 service impl
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class ComicServiceImpl extends ServiceImpl<ComicMapper, Comic> implements IComicService {

    @Autowired
    private ComicMapper comicMapper;

    @Override
    public PageInfo<Comic> getPageToAll(Integer pageNum) {
        // 设置分页
        PageHelper.startPage(pageNum, 10);
        QueryWrapper<Comic> queryWrapper = new QueryWrapper<>();
        List<Comic> comicList = comicMapper.selectList(queryWrapper.orderByDesc("id"));
        PageInfo<Comic> comicPageInfo = new PageInfo<>(comicList, 5);
        return comicPageInfo;
    }

    @Override
    public PageInfo<Comic> getPageByName(Integer pageNum, String comicName) {
        // 设置分页
        PageHelper.startPage(pageNum, 10);
        QueryWrapper<Comic> queryWrapper = new QueryWrapper<>();
        List<Comic> comicList = comicMapper.selectList(queryWrapper.like("comic_name", comicName).orderByDesc("id"));
        PageInfo<Comic> comicPageInfo = new PageInfo<>(comicList, 5);
        return comicPageInfo;
    }
}
