package org.cny.yurayura.service.sys.dict.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.DictSelectDTO;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.dao.sys.dict.DictMapper;
import org.cny.yurayura.enumerate.DictStatusEnum;
import org.cny.yurayura.service.sys.dict.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public ApiResult getPage(Integer pageNum, DictSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(pageNum, 20);
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto.getSelectDictCode()), "dict_code", dto.getSelectDictCode())
                .orderByAsc("dict_code", "id"));
        PageInfo<Dict> pageInfo = new PageInfo<>(dictList, 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult insert(Dict dict) {
        dictMapper.insert(dict);
        return ApiResult.success("添加成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult deleteBatchByIds(String ids) {
        List<Integer> delIdList = new ArrayList<>();
        String[] delIdArr = ids.split(",");
        for (String delIdStr : delIdArr) {
            delIdList.add(Integer.parseInt(delIdStr));
        }
        dictMapper.deleteBatchIds(delIdList);
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult update(Dict dict) {
        dictMapper.updateById(dict);
        return ApiResult.success("修改成功");
    }

    @Override
    public ApiResult getByDictCode(String dictCode) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .eq("dict_code", dictCode)
                .eq("dict_status", DictStatusEnum.ENABLE.getStatusId())
                .orderByAsc("id"));
        if (dictList.isEmpty()) {
            return ApiResult.warn("字典编码：" + dictCode + "对应系统数据字典为空");
        }
        return ApiResult.success("查询成功", dictList);
    }
}
