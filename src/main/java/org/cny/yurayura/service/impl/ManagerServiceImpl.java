package org.cny.yurayura.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cny.yurayura.dao.ManagerMapper;
import org.cny.yurayura.entity.Manager;
import org.cny.yurayura.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 service impl
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    public Manager getOneByNameAndPass(Manager manager) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        return managerMapper.selectOne(queryWrapper.nested(i -> i.eq("manager_name", manager.getManagerName()).eq("manager_password", manager.getManagerPassword())).last("limit 1"));
    }

}
