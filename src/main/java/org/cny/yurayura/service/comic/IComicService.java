package org.cny.yurayura.service.comic;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.ComicInstAndUpdtDto;
import org.cny.yurayura.dto.ComicSelectDto;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.vo.ApiResult;

import java.util.List;

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
	 * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getPage4B(ComicSelectDto dto);

    /**
     * 添加番剧
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult insert(ComicInstAndUpdtDto dto);

    /**
     * 批量删除番剧（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult deleteBatchByIds(List<Integer> ids);

    /**
     * 修改番剧
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult update(ComicInstAndUpdtDto dto);

}
