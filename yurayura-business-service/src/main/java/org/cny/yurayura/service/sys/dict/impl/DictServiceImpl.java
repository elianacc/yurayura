package org.cny.yurayura.service.sys.dict.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.sys.dict.DictMapper;
import org.cny.yurayura.dto.DictSelectDto;
import org.cny.yurayura.entity.sys.dict.Dict;
import org.cny.yurayura.enumerate.EnableStatusEnum;
import org.cny.yurayura.service.sys.dict.IDictService;
import org.cny.yurayura.system.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public PageInfo<Dict> getPage(DictSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto.getDictCode()), "dict_code", dto.getDictCode())
                .eq(!StringUtils.isEmpty(dto.getDictStatus()), "dict_status", dto.getDictStatus())
                .orderByAsc("dict_code", "dict_seq"));
        return new PageInfo<>(dictList, 5);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(Dict dict) {
        String warn = "";
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(queryWrapper
                .eq("dict_code", dict.getDictCode())
                .eq("dict_val", dict.getDictVal()));
        if (dictList.isEmpty()) {
            dictMapper.insert(dict);
            if (dict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
                // 插入字典记录到redis
                redisUtil.lSet(dict.getDictCode(), dict);
            }
        } else {
            warn = "此字典编码对应字典值已存在";
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByIds(List<Integer> ids) {
        ids.forEach(id -> {
            Dict dict = dictMapper.selectById(id);
            // 删除redis中的字典记录
            redisUtil.lRemove(dict.getDictCode(), 0, dict);
        });
        dictMapper.deleteBatchIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(Dict dict) {
        String warn = "";
        // 原字典记录
        Dict oldDict = dictMapper.selectById(dict.getId());
        if (oldDict.getDictCode().equals(dict.getDictCode()) && oldDict.getDictVal().equals(dict.getDictVal())) {
            // 先从redis删除原字典记录
            redisUtil.lRemove(oldDict.getDictCode(), 0, oldDict);
            if (dict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
                // 再插入新的字典记录到redis
                redisUtil.lSet(dict.getDictCode(), dict);
            }
            dictMapper.updateById(dict);
        } else {
            warn = "字典编码和字典值确定后无法修改";
        }
        return warn;
    }

    @Override
    public List<Dict> getByDictCode(String dictCode) {
        // 判断是否存在这个字典编码key对应的字典记录在redis，存在则直接获取，不存在从数据库查询
        if (redisUtil.hasKey(dictCode) && !redisUtil.lGet(dictCode, 0, -1).isEmpty()) {
            List<Object> objList = redisUtil.lGet(dictCode, 0, -1);
            List<Dict> dictList = JSON.parseArray(JSON.toJSONString(objList), Dict.class);
            return dictList.stream().sorted(Comparator.comparing(Dict::getDictSeq)).collect(Collectors.toList());
        }
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        return dictMapper.selectList(queryWrapper
                .eq("dict_code", dictCode)
                .eq("dict_status", EnableStatusEnum.ENABLE.getStatusId())
                .orderByAsc("dict_seq"));
    }

    @Override
    public List<Dict> getAll() {
        Set<String> keys = redisUtil.getListKey();
        List<Dict> dictList = new ArrayList<>();
        if (!keys.isEmpty()) {
            keys.forEach(key -> {
                if (!key.contains("yurayura-business-service-session")) {
                    List<Object> objList = redisUtil.lGet(key, 0, -1);
                    List<Dict> dictListForKey = JSON.parseArray(JSON.toJSONString(objList), Dict.class);
                    dictList.addAll(dictListForKey);
                }
            });
        }
        return dictList;
    }
}
