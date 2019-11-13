package org.cny.yurayura.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.UserMapper;
import org.cny.yurayura.entity.User;
import org.cny.yurayura.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户 service impl
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> getPageToNoPvt(Integer pageNum) {
        // 设置分页
        PageHelper.startPage(pageNum, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper.select("id", "user_image", "user_name", "user_status", "user_reg_time").orderByDesc("id"));
        List list = JSON.parseArray(JSON.toJSONString(userList));
        PageInfo<User> userPageInfo = new PageInfo<>(list, 5);
        return userPageInfo;
    }
}
