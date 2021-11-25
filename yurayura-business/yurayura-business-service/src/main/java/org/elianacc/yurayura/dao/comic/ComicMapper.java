package org.elianacc.yurayura.dao.comic;

import org.elianacc.yurayura.dto.ComicSelectDto;
import org.elianacc.yurayura.entity.comic.Comic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 番剧 mapper interface
 *
 * @author ELiaNaCc
 * @since 2019-11-18
 */
public interface ComicMapper extends BaseMapper<Comic> {
    /**
     * 查询番剧（根据番剧查询dto）
     *
     * @param dto
     * @return java.util.List<org.elianacc.yurayura.entity.comic.Comic>
     */
    List<Comic> getListBySelectDto(ComicSelectDto dto);
}
