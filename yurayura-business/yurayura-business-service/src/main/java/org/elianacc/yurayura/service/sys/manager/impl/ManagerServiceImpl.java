package org.elianacc.yurayura.service.sys.manager.impl;

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
import org.apache.shiro.subject.Subject;
import org.elianacc.yurayura.dao.sys.manager.ManagerMapper;
import org.elianacc.yurayura.dao.sys.permission.PermissionMapper;
import org.elianacc.yurayura.dto.*;
import org.elianacc.yurayura.entity.sys.manager.Manager;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.elianacc.yurayura.service.sys.manager.IManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统管理员 service impl
 *
 * @author ELiaNaCc
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
    public String insert(ManagerInsertDto dto) {
        String warn = "";
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        List<Manager> managerList = managerMapper.selectList(queryWrapper.eq("manager_name", dto.getManagerName()));
        if (managerList.isEmpty()) {
            Manager manager = new Manager();
            BeanUtils.copyProperties(dto, manager);
            manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
            manager.setManagerCreateTime(LocalDateTime.now());
            manager.setManagerUpdateTime(null);
            managerMapper.insert(manager);
            if (!dto.getPermissionIdArr().isEmpty()) {
                List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                        .filter(permissionId -> !(permissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                if (!permissionIdExistList.isEmpty()) {
                    managerMapper.insertBatchManagerPermission(permissionIdExistList, manager.getId());
                }
            }
        } else {
            warn = "管理员名已经被占用";
        }
        return warn;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByIds(IdsDto dto) {
        managerMapper.deleteBatchIds(dto.getIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(ManagerUpdateDto dto) {
        String warn = "";
        Manager oldManager = managerMapper.selectById(dto.getId());
        if (oldManager.getManagerName().equals("admin")) {
            warn = "管理员admin信息不允许被修改";
            return warn;
        }
        Manager manager = new Manager();
        BeanUtils.copyProperties(dto, manager);
        // 修改密码为空时使用此管理员旧密码
        if (ObjectUtils.isEmpty(manager.getManagerPassword())) {
            manager.setManagerPassword(oldManager.getManagerPassword());
        } else {
            manager.setManagerPassword(DigestUtils.md5DigestAsHex(manager.getManagerPassword().getBytes()));
        }
        manager.setManagerUpdateTime(LocalDateTime.now());
        managerMapper.updateById(manager);
        managerMapper.deleteManagerPermissionByManagerId(manager.getId());
        if (!dto.getPermissionIdArr().isEmpty()) {
            List<Integer> permissionIdExistList = dto.getPermissionIdArr().stream()
                    .filter(permissionId -> !(permissionMapper.selectById(permissionId).getPermissionStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!permissionIdExistList.isEmpty()) {
                managerMapper.insertBatchManagerPermission(permissionIdExistList, manager.getId());
            }
        }
        return warn;
    }

    @SneakyThrows
    @Override
    public String login(ManagerLoginDto dto, HttpSession httpSession) {
        String warn = "";
        // 获取服务器生成验证码
        Object managerVerifyCode = httpSession.getAttribute("managerVerifyCode");
        // 验证码session失效
        if (ObjectUtils.isEmpty(managerVerifyCode)) {
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
