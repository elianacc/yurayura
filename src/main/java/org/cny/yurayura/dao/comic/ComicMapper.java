package org.cny.yurayura.dao.comic;

import org.cny.yurayura.dto.ComicSelectDto;
import org.cny.yurayura.entity.comic.Comic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 番剧 mapper interface
 *
 * @author CNY
 * @since 2019-11-18
 */
public interface ComicMapper extends BaseMapper<Comic> {
    /**
     * 查询番剧（根据番剧查询dto）
     *
     * @param dto
     * @return java.util.List<org.cny.yurayura.entity.comic.Comic>
     */
    List<Comic> getListBySelectDto(ComicSelectDto dto);
}
