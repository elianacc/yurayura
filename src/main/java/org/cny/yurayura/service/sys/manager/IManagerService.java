package org.cny.yurayura.service.sys.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.dto.ManagerSelectDto;
import org.cny.yurayura.dto.MangerLoginDto;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.vo.ApiResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 系统管理员 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IManagerService extends IService<Manager> {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult getPage(ManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param manager
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult insert(Manager manager);

    /**
     * 批量删除系统管理员（根据id组）
     *
     * @param ids
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult deleteBatchByIds(List<Integer> ids);

    /**
     * 修改系统管理员
     *
     * @param manager
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult update(Manager manager);

    /**
     * 管理员登入
     *
     * @param dto
     * @param session
     * @param response
     * @return org.cny.yurayura.vo.ApiResult
     */
    public ApiResult login(MangerLoginDto dto, HttpSession session, HttpServletResponse response);
}
