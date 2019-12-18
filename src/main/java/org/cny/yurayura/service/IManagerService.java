package org.cny.yurayura.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cny.yurayura.entity.Manager;

/**
 * <p>
 * 管理员 service
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
public interface IManagerService extends IService<Manager> {
    /**
     * 查询管理员（根据管理员名和密码）
     *
     * @param manager
     * @return org.cny.yurayura.entity.Manager
     */
    public Manager getOneByNameAndPassword(Manager manager);
}
