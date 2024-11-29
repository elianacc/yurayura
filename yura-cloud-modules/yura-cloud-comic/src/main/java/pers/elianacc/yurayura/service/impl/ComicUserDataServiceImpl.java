package pers.elianacc.yurayura.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.elianacc.yurayura.dao.ComicUserDataMapper;
import pers.elianacc.yurayura.entity.ComicUserData;
import pers.elianacc.yurayura.service.IComicUserDataService;
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
