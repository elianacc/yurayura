package pers.elianacc.yurayura.service.impl;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.bo.UserExportBO;
import pers.elianacc.yurayura.dao.UserMapper;
import pers.elianacc.yurayura.dto.IdDTO;
import pers.elianacc.yurayura.dto.UserSelectDTO;
import pers.elianacc.yurayura.dto.UserUpdateStatusDTO;
import pers.elianacc.yurayura.entity.User;
import pers.elianacc.yurayura.service.IUserService;
import pers.elianacc.yurayura.utils.EasyPoiUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 service impl
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${yurayura.default-upload.user-avatar}")
    private String defaultUplUserAvatar;
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public PageInfo<User> getPage(UserSelectDTO dto) {
        Integer managerOrg = (Integer) StpUtil.getExtra("managerOrg");
        // 设置分页
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<User> userList = userMapper
                .selectList(Wrappers.<User>lambdaQuery()
                        .select(User.class, i -> !i.getColumn().equals("user_password"))
                        .nested(!ObjectUtils.isEmpty(dto.getUserNameKeyword()), i -> i
                                .apply("instr(user_name, {0}) > 0", dto.getUserNameKeyword())
                                .or()
                                .apply("instr(user_nickname, {0}) > 0", dto.getUserNameKeyword())
                        )
                        .eq(!ObjectUtils.isEmpty(dto.getUserSex()), User::getUserSex, dto.getUserSex())
                        .eq(!ObjectUtils.isEmpty(dto.getUserStatus()), User::getUserStatus, dto.getUserStatus())
                        .eq(!ObjectUtils.isEmpty(dto.getUserPhoneNumber())
                                , User::getUserPhoneNumber, dto.getUserPhoneNumber())
                        .eq(!managerOrg.equals(0), User::getUserOrg, managerOrg)
                        .orderByDesc(User::getId));
        PageInfo<User> pageInfo = new PageInfo<>(userList, 5);
        Assert.isTrue(pageInfo.getTotal() != 0, "查询不到数据");
        return pageInfo;
    }

    @Override
    public void updateStatus(UserUpdateStatusDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserStatus(dto.getUserStatus());
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatarDefault(IdDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUserAvatarUrl(defaultUplUserAvatar);
        userMapper.updateById(user);
    }

    @Override
    public void exportExcel(UserSelectDTO dto, HttpServletResponse response) throws IOException {
        List<User> userList = userMapper
                .selectList(Wrappers.<User>lambdaQuery()
                        .select(User.class, i -> !i.getColumn().equals("user_password"))
                        .nested(!ObjectUtils.isEmpty(dto.getUserNameKeyword()), i -> i
                                .apply("instr(user_name, {0}) > 0", dto.getUserNameKeyword())
                                .or()
                                .apply("instr(user_nickname, {0}) > 0", dto.getUserNameKeyword())
                        )
                        .eq(!ObjectUtils.isEmpty(dto.getUserSex()), User::getUserSex, dto.getUserSex())
                        .eq(!ObjectUtils.isEmpty(dto.getUserStatus()), User::getUserStatus, dto.getUserStatus())
                        .eq(!ObjectUtils.isEmpty(dto.getUserPhoneNumber())
                                , User::getUserPhoneNumber, dto.getUserPhoneNumber())
                        .orderByDesc(User::getId));

        List<UserExportBO> exportBOList = userList
                .stream()
                .map(user -> {
                    UserExportBO userExportBO = new UserExportBO();
                    BeanUtils.copyProperties(user, userExportBO);
                    userExportBO.setBelongProvCity(user.getUserProvince() + " " + user.getUserCity());

                    String imgUrl;
                    if (user.getUserAvatarUrl().equals(defaultUplUserAvatar)) {
                        imgUrl = "static" + defaultUplUserAvatar;
                    } else {
                        imgUrl = uploadPath + user.getUserAvatarUrl();
                    }

                    userExportBO.setUserAvatar(imgUrl);

                    return userExportBO;
                })
                .collect(Collectors.toList());

        ExportParams exportParams = new ExportParams(null, null);
        exportParams.setType(ExcelType.XSSF);

        EasyPoiUtil.exportExcel("用户信息" + System.currentTimeMillis(), exportBOList
                , UserExportBO.class, exportParams, response);

    }

}
