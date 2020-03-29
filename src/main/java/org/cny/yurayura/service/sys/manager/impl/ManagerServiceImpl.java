package org.cny.yurayura.service.sys.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.cny.yurayura.dao.sys.manager.ManagerMapper;
import org.cny.yurayura.dto.MangerLoginDTO;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.enumerate.ManagerStatusEnum;
import org.cny.yurayura.service.sys.manager.IManagerService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 系统管理员 service impl
 *
 * @author CNY
 * @since 2019-10-27
 */
@Slf4j
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @SneakyThrows
    @Override
    public ApiResult login(MangerLoginDTO dto, HttpSession session, HttpServletResponse response) {
        // 获取服务器生成验证码
        Object managerVerifyCode = session.getAttribute("managerVerifyCode");
        if (managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode())) {
            Manager manager = new Manager();
            BeanUtils.copyProperties(dto, manager);
            // MD5加密管理员密码
            manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
            // 查询有没有匹配管理员
            QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
            Manager aManager = managerMapper.selectOne(queryWrapper
                    .eq("manager_name", manager.getManagerName())
                    .eq("manager_password", manager.getManagerPassword())
                    .eq("manager_status", ManagerStatusEnum.ENABLE.getStatusId()));
            if (!StringUtils.isEmpty(aManager)) {
                // 登入成功存管理员session
                session.setAttribute("managerSession", aManager);
                // 以秒为单位，即在没有活动60分钟后，管理员session将失效
                session.setMaxInactiveInterval(60 * 60);
                // 将sessionId存入浏览器对应sessionId的cookie
                Cookie cookie = new Cookie("JSESSIONID", URLEncoder.encode(session.getId(), "utf-8"));
                cookie.setPath("/");
                // 设置sessionId cookie有效期为60分钟，关闭浏览器session也不会消失，记住登入
                cookie.setMaxAge(60 * 60);
                // js无法读取cookie，防止xss攻击
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                log.info("管理员：{}，登入成功", aManager.getManagerName());
                return ApiResult.success("管理员登入成功");
            } else {
                return ApiResult.warn("用户名或密码错误");
            }
        } else {
            return ApiResult.warn("验证码错误");
        }
    }
}
