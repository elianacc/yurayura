package org.elianacc.yurayura.service.comic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elianacc.yurayura.dao.comic.ComicUserDataMapper;
import org.elianacc.yurayura.entity.comic.ComicUserData;
import org.elianacc.yurayura.service.comic.IComicUserDataService;
import org.springframework.stereotype.Service;

/**
 * 番剧用户数据 service impl
 *
 * @author ELiaNaCc
 * @since 2019-11-17
 */
@Service
public class ComicUserDataServiceImpl extends ServiceImpl<ComicUserDataMapper, ComicUserData> implements IComicUserDataService {

}
