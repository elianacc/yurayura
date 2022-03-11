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
import org.elianacc.yurayura.dao.sys.manager.SysManagerMapper;
import org.elianacc.yurayura.dao.sys.role.SysRoleMapper;
import org.elianacc.yurayura.dto.*;
import org.elianacc.yurayura.entity.sys.manager.SysManager;
import org.elianacc.yurayura.enumerate.EnableStatusEnum;
import org.elianacc.yurayura.service.sys.manager.ISysManagerService;
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
public class SysManagerServiceImpl extends ServiceImpl<SysManagerMapper, SysManager> implements ISysManagerService {

    @Autowired
    private SysManagerMapper sysManagerMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<Map<String, Object>> getPage(SysManagerSelectDto dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<Map<String, Object>> managerAndPermissionList = sysManagerMapper.getManagerAndRoleListBySelectDto(dto);
        return new PageInfo<>(managerAndPermissionList, 5);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insert(SysManagerInsertDto dto) {
        String warn = "";
        QueryWrapper<SysManager> queryWrapper = new QueryWrapper<>();
        List<SysManager> sysManagerList = sysManagerMapper.selectList(queryWrapper.eq("manager_name", dto.getManagerName()));
        if (sysManagerList.isEmpty()) {
            SysManager sysManager = new SysManager();
            BeanUtils.copyProperties(dto, sysManager);
            sysManager.setManagerPassword(DigestUtils.md5DigestAsHex(sysManager.getManagerPassword().getBytes()));
            sysManager.setManagerCreateTime(LocalDateTime.now());
            sysManager.setManagerUpdateTime(null);
            sysManagerMapper.insert(sysManager);
            if (!dto.getRoleIdArr().isEmpty()) {
                List<Integer> roleIdExistList = dto.getRoleIdArr().stream()
                        .filter(roleId -> !(sysRoleMapper.selectById(roleId).getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                        .collect(Collectors.toList());
                if (!roleIdExistList.isEmpty()) {
                    sysManagerMapper.insertBatchManagerRole(roleIdExistList, sysManager.getId());
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
        sysManagerMapper.deleteBatchIds(dto.getIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String update(SysManagerUpdateDto dto) {
        String warn = "";
        SysManager oldSysManager = sysManagerMapper.selectById(dto.getId());
        if (oldSysManager.getManagerName().equals("admin")) {
            warn = "管理员admin信息不允许被修改";
            return warn;
        }
        SysManager sysManager = new SysManager();
        BeanUtils.copyProperties(dto, sysManager);
        // 修改密码为空时使用此管理员旧密码
        if (ObjectUtils.isEmpty(sysManager.getManagerPassword())) {
            sysManager.setManagerPassword(oldSysManager.getManagerPassword());
        } else {
            sysManager.setManagerPassword(DigestUtils.md5DigestAsHex(sysManager.getManagerPassword().getBytes()));
        }
        sysManager.setManagerUpdateTime(LocalDateTime.now());
        sysManagerMapper.updateById(sysManager);
        sysManagerMapper.deleteManagerRoleByManagerId(sysManager.getId());
        if (!dto.getRoleIdArr().isEmpty()) {
            List<Integer> roleIdExistList = dto.getRoleIdArr().stream()
                    .filter(roleId -> !(sysRoleMapper.selectById(roleId).getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!roleIdExistList.isEmpty()) {
                sysManagerMapper.insertBatchManagerRole(roleIdExistList, sysManager.getId());
            }
        }
        return warn;
    }

    @SneakyThrows
    @Override
    public String login(SysManagerLoginDto dto, HttpSession httpSession) {
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
                SysManager currentSysManager = (SysManager) subject.getPrincipal();
                log.info("管理员：{}，登入成功", currentSysManager.getManagerName());
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
        SysManager currentSysManager = (SysManager) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> currentManagerMsg = new HashMap<>();
        currentManagerMsg.put("managerName", currentSysManager.getManagerName());
        currentManagerMsg.put("managerPermission", sysManagerMapper.getCurrentManagerRolePermission(currentSysManager.getId()));
        return currentManagerMsg;
    }
}
