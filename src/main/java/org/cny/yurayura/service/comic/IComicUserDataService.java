package org.cny.yurayura.service.comic;

import org.cny.yurayura.entity.comic.ComicUserData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 番剧用户数据 service
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

    /**
     * 批量删除番剧用户数据（根据关联comic表id）
     *
     * @param comicIdList
     * @return void
     */
    public void deleteBatchByComicId(List<Integer> comicIdList);
}
