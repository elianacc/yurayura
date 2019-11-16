package org.cny.yurayura.controller;


import com.github.pagehelper.PageInfo;
import org.cny.yurayura.entity.User;
import org.cny.yurayura.service.IUserService;
import org.cny.yurayura.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 controller
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 分页查询全部用户（剔除了隐私字段）
     *
     * @param pageNum
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/getPageToNoPvt")
    public Msg getPageToNoPvt(@RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum) {
        PageInfo<User> userPageInfo = iUserService.getPageToNoPvt(pageNum);
        if (userPageInfo.getTotal() != 0) {
            return Msg.success("分页查询成功", userPageInfo);
        } else {
            return Msg.warn("系统数据为空");
        }
    }

}

