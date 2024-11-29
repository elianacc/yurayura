package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.SysDictMapper;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.dto.SysDictInsertDTO;
import pers.elianacc.yurayura.dto.SysDictSelectDTO;
import pers.elianacc.yurayura.dto.SysDictUpdateDTO;
import pers.elianacc.yurayura.entity.SysDict;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysDictService;
import pers.elianacc.yurayura.utils.RedisUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统数据字典 service impl
 *
 * @author ELiaNaCc
 * @since 2020-03-24
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public PageInfo<SysDict> getPage(SysDictSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysDict> sysDictList = sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                        .apply(!ObjectUtils.isEmpty(dto.getDictCode())
                                , "instr(dict_code, {0}) > 0", dto.getDictCode())
                        .eq(!ObjectUtils.isEmpty(dto.getDictStatus()), SysDict::getDictStatus, dto.getDictStatus())
                        .orderByAsc(SysDict::getDictCode, SysDict::getDictSeq));
        PageInfo<SysDict> pageInfo = new PageInfo<>(sysDictList, 5);
        Assert.isTrue(pageInfo.getTotal() != 0, "查询不到数据");
        return pageInfo;
    }

    @Override
    public void insert(SysDictInsertDTO dto) {
        List<SysDict> sysDictList = sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                        .eq(SysDict::getDictCode, dto.getDictCode())
                        .eq(SysDict::getDictVal, dto.getDictVal()));
        Assert.isTrue(sysDictList.isEmpty(), "此字典编码对应字典值已存在");
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDictMapper.insert(sysDict);
        if (sysDict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
            // 插入字典记录到redis
            RedisUtil.lSet(sysDict.getDictCode(), sysDict);
        }
    }

    @Override
    public void deleteBatchByIds(IdsDTO dto) {
        dto.getIds().forEach(id -> {
            SysDict sysDict = sysDictMapper.selectById(id);
            // 删除redis中的字典记录
            RedisUtil.lRemove(sysDict.getDictCode(), 0, sysDict);
        });
        sysDictMapper.deleteBatchIds(dto.getIds());
    }

    @Override
    public void update(SysDictUpdateDTO dto) {
        // 原字典记录
        SysDict oldSysDict = sysDictMapper.selectById(dto.getId());
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDictMapper.updateById(sysDict);
        // 先从redis删除原字典记录
        RedisUtil.lRemove(oldSysDict.getDictCode(), 0, oldSysDict);
        if (sysDict.getDictStatus().intValue() == EnableStatusEnum.ENABLE.getStatusId()) {
            sysDict.setDictCode(oldSysDict.getDictCode());
            sysDict.setDictVal(oldSysDict.getDictVal());
            // 再插入新的字典记录到redis
            RedisUtil.lSet(oldSysDict.getDictCode(), sysDict);
        }
    }

    @Override
    public List<SysDict> getByDictCode(String dictCode) {
        // 判断是否存在这个字典编码key对应的字典记录在redis，存在则直接获取，不存在从数据库查询
        if (RedisUtil.hasKey(dictCode) && !RedisUtil.lGet(dictCode, 0, -1).isEmpty()) {
            List<Object> objList = RedisUtil.lGet(dictCode, 0, -1);
            List<SysDict> sysDictList = JSONUtil.toList(JSONUtil.toJsonStr(objList), SysDict.class);
            return sysDictList
                    .stream()
                    .sorted(Comparator.comparing(SysDict::getDictSeq))
                    .collect(Collectors.toList());
        }
        List<SysDict> sysDictList = sysDictMapper
                .selectList(Wrappers.<SysDict>lambdaQuery()
                        .eq(SysDict::getDictCode, dictCode)
                        .eq(SysDict::getDictStatus, EnableStatusEnum.ENABLE.getStatusId())
                        .orderByAsc(SysDict::getDictSeq));
        Assert.isTrue(!sysDictList.isEmpty(), "字典编码：" + dictCode + "对应系统数据字典为空");
        return sysDictList;
    }

    @Override
    public List<SysDict> getAll() {
        Set<String> keys = RedisUtil.getListKey();
        List<SysDict> sysDictList = new ArrayList<>();
        if (!keys.isEmpty()) {
            keys.forEach(key -> {
                if (!key.contains("satoken") && !key.contains("lock4j")) {
                    List<Object> objList = RedisUtil.lGet(key, 0, -1);
                    List<SysDict> sysDictListForKey = JSONUtil.toList(JSONUtil.toJsonStr(objList), SysDict.class);
                    sysDictList.addAll(sysDictListForKey);
                }
            });
        }
        Assert.isTrue(!sysDictList.isEmpty(), "系统数据字典在redis中不存在，请添加");
        return sysDictList;
    }
}
