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
     * @param pageNum
	 * @param dictSelectDTO
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.sys.dict.Dict>
     */
    public ApiResult getPage(Integer pageNum, DictSelectDTO dictSelectDTO);

}
