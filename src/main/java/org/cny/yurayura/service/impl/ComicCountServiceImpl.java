package org.cny.yurayura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cny.yurayura.entity.ComicCount;
import org.cny.yurayura.dao.ComicCountMapper;
import org.cny.yurayura.service.IComicCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 番剧数据 service impl
 * </p>
 *
 * @author CNY
 * @since 2019-11-17
 */
@Service
public class ComicCountServiceImpl extends ServiceImpl<ComicCountMapper, ComicCount> implements IComicCountService {

    @Autowired
    private ComicCountMapper comicCountMapper;

    @Transactional
    @Override
    public int deleteByComicId(Integer comicId) {
        QueryWrapper<ComicCount> queryWrapper = new QueryWrapper<>();
        return comicCountMapper.delete(queryWrapper.eq("comic_id", comicId));
    }
}
