package org.cny.yurayura.service;

import org.cny.yurayura.entity.ComicCount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 番剧数据 service
 * </p>
 *
 * @author CNY
 * @since 2019-11-17
 */
public interface IComicCountService extends IService<ComicCount> {

    /**
     * 关联comic表id删除
     *
     * @param comicId
     * @return boolean
     */
    int deleteByComicId(Integer comicId);
}
