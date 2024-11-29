package pers.elianacc.yurayura.dao;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.elianacc.yurayura.entity.ComicUserData;

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
