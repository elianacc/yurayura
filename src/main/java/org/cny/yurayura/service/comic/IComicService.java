package org.cny.yurayura.service.comic;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.ComicSelectDTO;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.vo.ApiResult;

/**
 * 番剧 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询番剧（B端）
     *
     * @param pageNum
     * @param comicSelectDTO
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.comic.Comic>
     */
    public ApiResult getPageToB(Integer pageNum, ComicSelectDTO comicSelectDTO);

}
