package org.elianacc.yurayura.dao.comic;

import org.apache.ibatis.annotations.Param;
import org.elianacc.yurayura.entity.comic.ComicUserData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 番剧用户数据 mapper interface
 *
 * @author ELiaNaCc
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
