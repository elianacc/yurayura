package org.elianacc.yurayura.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dto.UserSelectDto;
import org.elianacc.yurayura.entity.user.User;

/**
 * 用户 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询用户
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<org.elianacc.yurayura.entity.user.User>
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
