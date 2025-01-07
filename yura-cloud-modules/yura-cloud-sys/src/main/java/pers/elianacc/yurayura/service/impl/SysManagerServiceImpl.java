package pers.elianacc.yurayura.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.dao.SysManagerMapper;
import pers.elianacc.yurayura.dao.SysRoleMapper;
import pers.elianacc.yurayura.dto.IdsDTO;
import pers.elianacc.yurayura.dto.SysManagerInsertDTO;
import pers.elianacc.yurayura.dto.SysManagerSelectDTO;
import pers.elianacc.yurayura.dto.SysManagerUpdateDTO;
import pers.elianacc.yurayura.entity.SysManager;
import pers.elianacc.yurayura.enumerate.AdminOrgEnum;
import pers.elianacc.yurayura.enumerate.EnableStatusEnum;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVO;

import java.time.LocalDateTime;
import java.util.List;
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
    public PageInfo<SysManagerAndRoleVO> getPage(SysManagerSelectDTO dto) {
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<SysManagerAndRoleVO> managerAndPermissionList = sysManagerMapper.getManagerAndRoleListBySelectDTO(dto);
        return new PageInfo<>(managerAndPermissionList, 5);
    }

    @Override
    public void insert(SysManagerInsertDTO dto) {
        List<SysManager> sysManagerList = sysManagerMapper
                .selectList(Wrappers.<SysManager>lambdaQuery()
                        .eq(SysManager::getManagerName, dto.getManagerName()));
        Assert.isTrue(sysManagerList.isEmpty(), "管理员名已经被占用");
        SysManager sysManager = new SysManager();
        BeanUtils.copyProperties(dto, sysManager);
        sysManager.setManagerPassword(DigestUtils.md5DigestAsHex(sysManager.getManagerPassword().getBytes()));
        sysManager.setManagerCreateTime(LocalDateTime.now());
        sysManager.setManagerUpdateTime(null);
        sysManagerMapper.insert(sysManager);
        if (!dto.getRoleIdArr().isEmpty()) {
            List<Integer> roleIdExistList = dto.getRoleIdArr()
                    .stream()
                    .filter(roleId -> !(sysRoleMapper
                            .selectById(roleId).getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!roleIdExistList.isEmpty()) {
                sysManagerMapper.insertBatchManagerRole(roleIdExistList, sysManager.getId());
            }
        }
    }

    @Override
    public void deleteBatchByIds(IdsDTO dto) {
        sysManagerMapper.deleteBatchIds(dto.getIds());
    }

    @Override
    public void update(SysManagerUpdateDTO dto) {
        SysManager oldSysManager = sysManagerMapper.selectById(dto.getId());
        Assert.isTrue(!oldSysManager.getManagerOrg().equals(AdminOrgEnum.ADMIN_ORG.getOrg())
                , "管理员admin信息不允许被修改");
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
            List<Integer> roleIdExistList = dto.getRoleIdArr()
                    .stream()
                    .filter(roleId -> !(sysRoleMapper
                            .selectById(roleId).getRoleStatus() == EnableStatusEnum.DISABLE.getStatusId().intValue()))
                    .collect(Collectors.toList());
            if (!roleIdExistList.isEmpty()) {
                sysManagerMapper.insertBatchManagerRole(roleIdExistList, sysManager.getId());
            }
        }
    }

    @Override
    public List<String> getManagerRolePermission(Integer managerId) {
        return sysManagerMapper.getManagerRolePermission(managerId);
    }

    @Override
    public SysManager getEnableManagerByName(String managerName) {
        return sysManagerMapper
                .selectOne(Wrappers.<SysManager>lambdaQuery()
                        .eq(SysManager::getManagerName, managerName)
                        .eq(SysManager::getManagerStatus, EnableStatusEnum.ENABLE.getStatusId()));
    }

}
