package org.elianacc.yurayura.service.sys.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dto.ManagerLoginDto;
import org.elianacc.yurayura.dto.ManagerSelectDto;
import org.elianacc.yurayura.entity.sys.manager.Manager;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 系统管理员 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface IManagerService extends IService<Manager> {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public PageInfo<Map<String, Object>> getPage(ManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param manager
	 * @param permissionIdArr
     * @return java.lang.String
     */
    public String insert(Manager manager, List<Integer> permissionIdArr);

    /**
     * 批量删除系统管理员（根据id组）
     *
     * @param ids
     * @return void
     */
    public void deleteBatchByIds(List<Integer> ids);

    /**
     * 修改系统管理员
     *
     * @param manager
	 * @param permissionIdArr
     * @return java.lang.String
     */
    public String update(Manager manager, List<Integer> permissionIdArr);

    /**
     * 系统管理员登入
     *
     * @param dto
     * @param session
     * @return java.lang.String
     */
    public String login(ManagerLoginDto dto, HttpSession session);

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> getCurrentManagerMsg();
}
