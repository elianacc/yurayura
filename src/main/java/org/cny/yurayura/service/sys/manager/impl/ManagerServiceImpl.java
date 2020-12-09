package org.cny.yurayura.service.sys.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.cny.yurayura.dao.sys.manager.ManagerMapper;
import org.cny.yurayura.dto.ManagerSelectDto;
import org.cny.yurayura.dto.MangerLoginDto;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.service.sys.manager.IManagerService;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public ApiResult getPage(ManagerSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        List<Manager> managerList = managerMapper.selectList(queryWrapper
                .like(!StringUtils.isEmpty(dto), "manager_name", dto.getManagerName()));
        PageInfo<Manager> pageInfo = new PageInfo<>(managerList, 5);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult insert(Manager manager) {
        manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
        manager.setManagerCreateTime(LocalDateTime.now());
        manager.setManagerUpdateTime(null);
        managerMapper.insert(manager);
        return ApiResult.success("添加成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult deleteBatchByIds(List<Integer> ids) {
        managerMapper.deleteBatchIds(ids);
        return ApiResult.success("删除成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult update(Manager manager) {
        // 修改密码为空时使用此管理员旧密码
        if (StringUtils.isEmpty(manager.getManagerPassword())) {
            Manager updateManager = managerMapper.selectById(manager.getId());
            manager.setManagerPassword(updateManager.getManagerPassword());
        } else {
            manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
        }
        managerMapper.updateById(manager);
        return ApiResult.success("修改成功");
    }

    @SneakyThrows
    @Override
    public ApiResult login(MangerLoginDto dto, HttpSession httpSession, HttpServletResponse response) {
        // 获取服务器生成验证码
        Object managerVerifyCode = httpSession.getAttribute("managerVerifyCode");
        // 验证码session失效
        if (StringUtils.isEmpty(managerVerifyCode)) {
            return ApiResult.warn("验证码过期，请刷新验证码");
        }
        if (managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode())) {
            // 封装用户登入数据(用户名+密码)为token
            UsernamePasswordToken token = new UsernamePasswordToken(dto.getManagerName(), DigestUtils.md5DigestAsHex(dto.getManagerPassword().getBytes()));
            // 获取当前用户
            Subject subject = SecurityUtils.getSubject();
            try {
                // 当前用户登入
                subject.login(token);
                Manager currentManger = (Manager) subject.getPrincipal();
                Session session = subject.getSession();
                // 存入shiro的session
                session.setAttribute("managerSession", currentManger);
                // 将sessionId存入浏览器对应sessionId的cookie
                Cookie cookie = new Cookie("JSESSIONID", URLEncoder.encode(String.valueOf(session.getId()), "utf-8"));
                cookie.setPath("/");
                // 设置sessionId cookie有效期为60分钟，关闭浏览器session也不会消失，记住登入
                cookie.setMaxAge(60 * 60);
                // js无法读取cookie，防止xss攻击
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                log.info("管理员：{}，登入成功", currentManger.getManagerName());
                return ApiResult.success("管理员登入成功");
            } catch (UnknownAccountException uae) {
                return ApiResult.warn("用户不存在");
            } catch (IncorrectCredentialsException ice) {
                return ApiResult.warn("密码错误");
            }

        } else {
            return ApiResult.warn("验证码错误");
        }
    }
}
