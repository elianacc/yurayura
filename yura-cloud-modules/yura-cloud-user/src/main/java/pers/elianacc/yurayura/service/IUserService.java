package pers.elianacc.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.UserSelectDTO;
import pers.elianacc.yurayura.dto.UserUpdateStatusDTO;
import pers.elianacc.yurayura.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @return com.github.pagehelper.PageInfo<pers.elianacc.yurayura.entity.user.User>
     */
    public PageInfo<User> getPage(UserSelectDTO dto);

    /**
     * 修改状态（根据用户id）
     *
     * @param dto
     * @return void
     */
    public void updateStatus(UserUpdateStatusDTO dto);

    /**
     * 重置为默认头像（根据用户id）
     *
     * @param dto
     * @return void
     */
    public void updateAvatarDefault(IdDTO dto);

    /**
     * 导出excel
     *
     * @param dto
	 * @param response
     * @return void
     */
    public void exportExcel(UserSelectDTO dto, HttpServletResponse response) throws IOException;

}
