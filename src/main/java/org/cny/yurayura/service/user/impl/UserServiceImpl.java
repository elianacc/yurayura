package org.cny.yurayura.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dao.user.UserMapper;
import org.cny.yurayura.dto.UserSelectDTO;
import org.cny.yurayura.entity.user.User;
import org.cny.yurayura.service.user.IUserService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户 service impl
 *
 * @author CNY
 * @since 2019-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResult getPageToB(UserSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper
                .select("id", "user_avatar_url", "user_name", "user_sex", "user_status", "user_reg_time"
                        , "user_current_edit_time")
                .like(!StringUtils.isEmpty(dto.getUserName()), "user_name", dto.getUserName())
                .eq(!StringUtils.isEmpty(dto.getUserSex()), "user_sex", dto.getUserSex())
                .eq(!StringUtils.isEmpty(dto.getUserStatus()), "user_status", dto.getUserStatus())
                .orderByDesc("id"));
        PageInfo<Object> pageInfo = new PageInfo<>(JSON.parseArray(JSON.toJSONString(userList)), 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }
}
