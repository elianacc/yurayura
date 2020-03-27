package org.cny.yurayura.service.sys.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.entity.sys.manager.Manager;

/**
 * 系统管理员 service
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IManagerService extends IService<Manager> {
    /**
     * 查询管理员（根据管理员名和密码）
     *
     * @param manager
     * @return org.cny.yurayura.entity.sys.manager.Manager
     */
    public Manager getByNameAndPassword(Manager manager);
}
