package org.elianacc.yurayura.service.sys.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.elianacc.yurayura.dto.*;
import org.elianacc.yurayura.entity.sys.manager.SysManager;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 系统管理员 service
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
public interface ISysManagerService extends IService<SysManager> {

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public PageInfo<Map<String, Object>> getPage(SysManagerSelectDto dto);

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return java.lang.String
     */
    public String insert(SysManagerInsertDto dto);

    /**
     * 批量删除系统管理员（根据系统管理员id组）
     *
     * @param dto
     * @return void
     */
    public void deleteBatchByIds(IdsDto dto);

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return java.lang.String
     */
    public String update(SysManagerUpdateDto dto);

    /**
     * 系统管理员登入
     *
     * @param dto
     * @param session
     * @return java.lang.String
     */
    public String login(SysManagerLoginDto dto, HttpSession session);

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> getCurrentManagerMsg();
}
