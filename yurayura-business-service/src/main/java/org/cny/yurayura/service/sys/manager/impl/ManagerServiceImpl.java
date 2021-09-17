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
import org.cny.yurayura.dao.sys.permission.PermissionMapper;
import org.cny.yurayura.dto.ManagerLoginDto;
import org.cny.yurayura.dto.ManagerSelectDto;
import org.cny.yurayura.entity.sys.manager.Manager;
import org.cny.yurayura.enumerate.EnableStatusEnum;
import org.cny.yurayura.service.sys.manager.IManagerService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public PageInfo<Map<String, Object>> getPage(ManagerSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Map<String, Object>> managerAndPermissionList = managerMapper.getManagerAndPermissionListBySelectDto(dto);
        return new PageInfo<>(managerAndPermissionList, 5);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(Manager manager, List<Integer> permissionIdArr) {
        String warn = "";
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        List<Manager> managerList = managerMapper.selectList(queryWrapper.eq("manager_name", manager.getManagerName()));
        if (managerList.isEmpty()) {
            manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
            manager.setManagerCreateTime(LocalDateTime.now());
            manager.setManagerUpdateTime(null);
            managerMapper.insert(manager);
            if (permissionIdArr != null) {
                List<Integer> permissionIdExistList = permissionIdArr.stream()
                        .filter(permissionId -> !(permissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                managerMapper.insertBatchManagerPermission(permissionIdExistList, manager.getId());
            }
        } else {
            warn = "管理员名已经被占用";
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByIds(List<Integer> ids) {
        managerMapper.deleteBatchIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(Manager manager, List<Integer> permissionIdArr) {
        String warn = "";
        Manager oldManager = managerMapper.selectById(manager.getId());
        if (oldManager.getManagerName().equals("admin")) {
            warn = "管理员admin信息不允许被修改";
            return warn;
        }
        if (oldManager.getManagerName().equals(manager.getManagerName())) {
            // 修改密码为空时使用此管理员旧密码
            if (StringUtils.isEmpty(manager.getManagerPassword())) {
                manager.setManagerPassword(oldManager.getManagerPassword());
            } else {
                manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
            }
            manager.setManagerUpdateTime(LocalDateTime.now());
            managerMapper.updateById(manager);
            managerMapper.deleteManagerPermissionByManagerId(manager.getId());
            if (permissionIdArr != null) {
                List<Integer> permissionIdExistList = permissionIdArr.stream()
                        .filter(permissionId -> !(permissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                managerMapper.insertBatchManagerPermission(permissionIdExistList, manager.getId());
            }
        } else {
            warn = "管理员名确定后无法修改";
        }
        return warn;
    }

    @SneakyThrows
    @Override
    public String login(ManagerLoginDto dto, HttpSession httpSession, HttpServletResponse response) {
        String warn = "";
        // 获取服务器生成验证码
        Object managerVerifyCode = httpSession.getAttribute("managerVerifyCode");
        // 验证码session失效
        if (StringUtils.isEmpty(managerVerifyCode)) {
            warn = "验证码过期，请重新输入";
            return warn;
        }
        if (managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode())) {
            // 封装用户登入数据(用户名+密码)为token
            UsernamePasswordToken token = new UsernamePasswordToken(dto.getManagerName(), DigestUtils.md5DigestAsHex(dto.getManagerPassword().getBytes()));
            // 获取当前用户
            Subject subject = SecurityUtils.getSubject();
            try {
                // 当前用户登入
                subject.login(token);
                Manager currentManager = (Manager) subject.getPrincipal();
                Session session = subject.getSession();
                // 存入shiro的session
                session.setAttribute("managerSession", currentManager);
                // 设置session过期时间为24小时
                session.setTimeout(1000 * 60 * 60 * 24);
                // 将sessionId存入浏览器对应sessionId的cookie
                Cookie cookie = new Cookie("JSESSIONID", URLEncoder.encode(String.valueOf(session.getId()), "utf-8"));
                cookie.setPath("/");
                // 设置sessionId cookie有效期为24小时，关闭浏览器session也不会消失，记住登入
                cookie.setMaxAge(60 * 60 * 24);
                // js无法读取cookie，防止xss攻击
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                log.info("管理员：{}，登入成功", currentManager.getManagerName());
            } catch (UnknownAccountException uae) {
                warn = "用户不存在";
            } catch (IncorrectCredentialsException ice) {
                warn = "密码错误";
            }

        } else {
            warn = "验证码错误";
        }
        return warn;
    }

    @Override
    public Map<String, Object> getCurrentManagerMsg() {
        Manager currentManager = (Manager) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> currentManagerMsg = new HashMap<>();
        currentManagerMsg.put("managerName", currentManager.getManagerName());
        currentManagerMsg.put("managerPermission", managerMapper.getCurrentManagerPermission(currentManager.getId()));
        return currentManagerMsg;
    }
}
