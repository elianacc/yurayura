package org.cny.yurayura.service.sys.dict.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.sys.dict.DictMapper;
import org.cny.yurayura.dto.DictSelectDto;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.enumerate.DictStatusEnum;
import org.cny.yurayura.service.sys.dict.IDictService;
import org.cny.yurayura.system.util.RedisUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ApiResult getPage(DictSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto.getDictCode()), "dict_code", dto.getDictCode())
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
        if (dict.getDictStatus().intValue() == DictStatusEnum.ENABLE.getStatusId()) {
            // 插入字典记录到redis
            redisUtil.lSet(dict.getDictCode(), dict);
        }
        return ApiResult.success("添加成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult deleteBatchByIds(List<Integer> ids) {
        for (Integer id : ids) {
            Dict dict = dictMapper.selectById(id);
            // 删除redis中的字典记录
            redisUtil.lRemove(dict.getDictCode(), 0, dict);
        }
        dictMapper.deleteBatchIds(ids);
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult update(Dict dict) {
        Dict oldDict = dictMapper.selectById(dict.getId()); // 原字典记录
        // 先从redis删除原字典记录
        redisUtil.lRemove(oldDict.getDictCode(), 0, oldDict);
        if (dict.getDictStatus().intValue() == DictStatusEnum.ENABLE.getStatusId()) {
            // 再插入新的字典记录到redis
            redisUtil.lSet(dict.getDictCode(), dict);
        }
        dictMapper.updateById(dict);
        return ApiResult.success("修改成功");
    }

    @Override
    public ApiResult getByDictCode(String dictCode) {
        // 判断是否存在这个字典编码key对应的字典记录在redis，存在则直接获取，不存在从数据库查询
        if (redisUtil.hasKey(dictCode) && !redisUtil.lGet(dictCode, 0, -1).isEmpty()) {
            return ApiResult.success("查询成功", redisUtil.lGet(dictCode, 0, -1));
        }
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
