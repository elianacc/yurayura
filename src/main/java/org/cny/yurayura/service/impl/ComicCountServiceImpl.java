package org.cny.yurayura.service.impl;

import org.cny.yurayura.entity.ComicCount;
import org.cny.yurayura.dao.ComicCountMapper;
import org.cny.yurayura.service.IComicCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
