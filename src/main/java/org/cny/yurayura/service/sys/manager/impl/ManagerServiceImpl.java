package org.cny.yurayura.service.sys.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cny.yurayura.dao.sys.manager.ManagerMapper;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.enumerate.ManagerStatusEnum;
import org.cny.yurayura.service.sys.manager.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理员 service impl
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public Manager getOneByNameAndPassword(Manager manager) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        return managerMapper.selectOne(queryWrapper
                .eq("manager_name", manager.getManagerName())
                .eq("manager_password", manager.getManagerPassword())
                .eq("manager_status", ManagerStatusEnum.ENABLE.getStatusId())
                .last("limit 1"));
    }
}
