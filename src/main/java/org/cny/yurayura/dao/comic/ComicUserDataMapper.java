package org.cny.yurayura.dao.comic;

import org.apache.ibatis.annotations.Param;
import org.cny.yurayura.entity.comic.ComicUserData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 番剧用户数据 Mapper 接口
 * </p>
 *
 * @author CNY
 * @since 2019-11-18
 */
public interface ComicUserDataMapper extends BaseMapper<ComicUserData> {

    /**
     * 批量删除番剧用户数据（根据关联comic表id）
     *
     * @param comicIdList
     * @return void
     */
    void deleteBatchByComicId(@Param("comicIdList") List<Integer> comicIdList);
}
