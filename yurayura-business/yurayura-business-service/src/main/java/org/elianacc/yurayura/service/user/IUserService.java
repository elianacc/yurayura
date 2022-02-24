package org.elianacc.yurayura.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dto.IdDto;
import org.elianacc.yurayura.dto.UserSelectDto;
import org.elianacc.yurayura.dto.UserUpdateStatusDto;
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
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return void
     */
    public void updateStatus(UserUpdateStatusDto dto);

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return void
     */
    public void updateAvatarDefault(IdDto dto);

}
