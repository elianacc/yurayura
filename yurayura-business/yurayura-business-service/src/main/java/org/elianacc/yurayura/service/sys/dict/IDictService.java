package org.elianacc.yurayura.service.sys.dict;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dto.DictInsertDto;
import org.elianacc.yurayura.dto.DictSelectDto;
import org.elianacc.yurayura.dto.DictUpdateDto;
import org.elianacc.yurayura.dto.IdsDto;
import org.elianacc.yurayura.entity.sys.dict.Dict;

import java.util.List;

/**
 * 系统数据字典 service
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
public interface IDictService extends IService<Dict> {

    /**
     * 分页查询系统数据字典
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<org.elianacc.yurayura.entity.sys.dict.Dict>
     */
    public PageInfo<Dict> getPage(DictSelectDto dto);

    /**
     * 添加系统数据字典
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(DictInsertDto dto);

    /**
     * 批量删除系统数据字典（根据系统数据字典id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDto dto);

    /**
     * 修改系统数据字典
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(DictUpdateDto dto);

    /**
     * 查询系统数据字典（根据字典编码）
     *
     * @param dictCode
     * @return java.util.List<org.elianacc.yurayura.entity.sys.dict.Dict>
     */
    public List<Dict> getByDictCode(String dictCode);

    /**
     * 查询所有系统数据字典（只从redis获取）
     *
     * @param
     * @return java.util.List<org.elianacc.yurayura.entity.sys.dict.Dict>
     */
    public List<Dict> getAll();

}
