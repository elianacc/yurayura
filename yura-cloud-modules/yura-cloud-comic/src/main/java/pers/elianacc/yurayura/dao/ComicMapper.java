package pers.elianacc.yurayura.dao;

import pers.elianacc.yurayura.dto.ComicSelectDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.elianacc.yurayura.entity.Comic;

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
     * @return java.util.List<pers.elianacc.yurayura.entity.comic.Comic>
     */
    List<Comic> getListBySelectDTO(ComicSelectDTO dto);
}
