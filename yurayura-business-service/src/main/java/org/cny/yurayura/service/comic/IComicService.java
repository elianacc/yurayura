package org.cny.yurayura.service.comic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.ComicInstAndUpdtDto;
import org.cny.yurayura.dto.ComicSelectDto;
import org.cny.yurayura.entity.comic.Comic;

import java.util.List;

/**
 * 番剧 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IComicService extends IService<Comic> {

    /**
     * 分页查询番剧
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.comic.Comic>
     */
    public PageInfo<Comic> getPage(ComicSelectDto dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(ComicInstAndUpdtDto dto);

    /**
     * 批量删除番剧（根据id组）
     *
     * @param ids
     * @return void
     */
    public void deleteBatchByIds(List<Integer> ids);

    /**
     * 修改番剧
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(ComicInstAndUpdtDto dto);

}
