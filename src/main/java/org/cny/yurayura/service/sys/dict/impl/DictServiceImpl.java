package org.cny.yurayura.service.sys.dict.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.dao.sys.dict.DictMapper;
import org.cny.yurayura.service.sys.dict.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统数据字典 service impl
 *
 * @author CNY
 * @since 2020-03-24
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public PageInfo<Dict> getPage(Integer pageNum, DictSelectDTO dictSelectDTO) {
        // 设置分页
        PageHelper.startPage(pageNum, 20);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dictSelectDTO.getSelectDictCode())
                        , "dict_code", dictSelectDTO.getSelectDictCode())
                .orderByAsc("dict_code", "id"));
        return new PageInfo<>(dictList, 5);
    }
}
