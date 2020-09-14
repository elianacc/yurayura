package org.cny.yurayura.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.UserSelectDto;
import org.cny.yurayura.entity.user.User;
import org.cny.yurayura.vo.ApiResult;

/**
 * 用户 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IUserService extends IService<User> {

    /**
     * 分页查询用户（B端）
     *
	 * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getPage4B(UserSelectDto dto);

}
