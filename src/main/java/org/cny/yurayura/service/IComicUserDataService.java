package org.cny.yurayura.service;

import org.cny.yurayura.entity.ComicUserData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 番剧用户数据 service
 * </p>
 *
 * @author CNY
 * @since 2019-11-17
 */
public interface IComicUserDataService extends IService<ComicUserData> {

    /**
     * 删除番剧数据（根据关联comic表id）
     *
     * @param comicId
     */
    public void deleteByComicId(Integer comicId);
}
