package org.cny.yurayura.service.sys.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.MangerLoginDto;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.vo.ApiResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * 系统管理员 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IManagerService extends IService<Manager> {

    /**
     * 管理员登入
     *
     * @param dto
	 * @param session
	 * @param response
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult login(MangerLoginDto dto, HttpSession session, HttpServletResponse response) throws UnsupportedEncodingException;
}
