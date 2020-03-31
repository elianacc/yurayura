package org.cny.yurayura.service.comic;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.ComicInstAndUpdtDTO;
import org.cny.yurayura.dto.ComicSelectDTO;
import org.cny.yurayura.entity.comic.Comic;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.web.multipart.MultipartFile;

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
	 * @param pageSize
	 * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getPageToB(Integer pageNum, Integer pageSize, ComicSelectDTO dto);

    /**
     * 添加番剧
     *
     * @param dto
	 * @param cmImgFile
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult insert(ComicInstAndUpdtDTO dto, MultipartFile cmImgFile);

    /**
     * 批量删除番剧（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult deleteBatchByIds(String ids);

    /**
     * 修改番剧
     *
     * @param dto
	 * @param cmImgFile
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult update(ComicInstAndUpdtDTO dto, MultipartFile cmImgFile);

}
