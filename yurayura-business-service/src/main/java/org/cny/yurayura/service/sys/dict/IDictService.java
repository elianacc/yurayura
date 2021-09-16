package org.cny.yurayura.service.sys.dict;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.DictSelectDto;
import org.cny.yurayura.entity.sys.dict.Dict;

import java.util.List;

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
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.sys.dict.Dict>
     */
    public PageInfo<Dict> getPage(DictSelectDto dto);

    /**
     * 添加系统数据字典
     *
     * @param dict
     * @return java.lang.String
     */
    public String insert(Dict dict);

    /**
     * 批量删除系统数据字典（根据id组）
     *
     * @param ids
     * @return void
     */
    public void deleteBatchByIds(List<Integer> ids);

    /**
     * 修改系统数据字典
     *
     * @param dict
     * @return java.lang.String
     */
    public String update(Dict dict);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return java.util.List<org.cny.yurayura.entity.sys.dict.Dict>
     */
    public List<Dict> getByDictCode(String dictCode);

}