package org.cny.yurayura.service.sys.dict;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.vo.ApiResult;

/**
 * 系统数据字典 service
 *
 * @author CNY
 * @since 2020-03-24
 */
public interface IDictService extends IService<Dict> {

    /**
     * 分页查询系统数据字典
     *
	 * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getPage(DictSelectDTO dto);

    /**
     * 添加系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult insert(Dict dict);

    /**
     * 批量删除系统数据字典（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult deleteBatchByIds(String ids);

    /**
     * 修改系统数据字典
     *
     * @param dict
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult update(Dict dict);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getByDictCode(String dictCode);

}
