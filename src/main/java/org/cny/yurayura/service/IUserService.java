package org.cny.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.entity.User;

/**
 * <p>
 * 用户 service
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询所有用户（去除隐私字段）
     *
     * @param pageNum
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<org.cny.yurayura.entity.User>
     */
    public PageInfo<Object> getPageToUserManage(Integer pageNum);

}
