package org.cny.yurayura.service.sys.dict;

import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

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
    public PageInfo<Dict> getPage(Integer pageNum, DictSelectDTO dictSelectDTO);

}
