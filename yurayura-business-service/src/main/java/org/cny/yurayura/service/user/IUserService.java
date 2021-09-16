package org.cny.yurayura.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.cny.yurayura.dto.UserSelectDto;
import org.cny.yurayura.entity.user.User;

/**
 * 用户 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询用户
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<org.cny.yurayura.entity.user.User>
     */
    public PageInfo<User> getPage(UserSelectDto dto);

    /**
     * 修改状态（根据id）
     *
     * @param user
     * @return void
     */
    public void updateStatus(User user);

    /**
     * 修改头像为默认图片（根据id）
     *
     * @param user
     * @return void
     */
    public void updateAvatarDefault(User user);

}
