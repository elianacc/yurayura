package pers.elianacc.yurayura.controller;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.lock.annotation.Lock4j;
import com.github.pagehelper.PageInfo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.elianacc.yurayura.dto.*;
import pers.elianacc.yurayura.entity.SysManager;
import pers.elianacc.yurayura.service.ISysManagerService;
import pers.elianacc.yurayura.utils.VerifyCodeUtil;
import pers.elianacc.yurayura.vo.ApiResult;
import pers.elianacc.yurayura.vo.SysManagerAndRoleVO;
import pers.elianacc.yurayura.vo.SysManagerMsgVO;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 系统管理员 controller
 *
 * @author ELiaNaCc
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/api/sys/manager")
@Api(tags = "系统管理员API")
public class SysManagerController {

    @Autowired
    private ISysManagerService iSysManagerService;

    @Value("${sa-token.timeout}")
    private Integer tokenTimeout;

    /**
     * 查询系统管理员（根据系统管理员id）
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.entity.sys.manager.SysManager>
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统管理员（根据系统管理员id）")
    public ApiResult<SysManager> getById(IdDTO dto) {
        return ApiResult.success("查询成功", iSysManagerService.getById(dto.getId()));
    }

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<com.github.pagehelper.PageInfo<pers.elianacc.yurayura.vo.SysManagerAndRoleVO>>
     */
    @PostMapping("/getPage")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()"}, autoRelease = false)
    @ApiOperation("分页查询系统管理员")
    public ApiResult<PageInfo<SysManagerAndRoleVO>> getPage(@Validated @RequestBody SysManagerSelectDTO dto) {
        return ApiResult.success("分页查询成功", iSysManagerService.getPage(dto));
    }

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/insert")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.managerName"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("添加系统管理员")
    public ApiResult<String> insert(@Validated @RequestBody SysManagerInsertDTO dto) {
        iSysManagerService.insert(dto);
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PutMapping("/update")
    @Lock4j(keys = {"T(cn.dev33.satoken.stp.StpUtil).getTokenValue()", "#dto.id"}, autoRelease = false)
    @GlobalTransactional(rollbackFor = Exception.class) // TM开启全局事务
    @ApiOperation("修改系统管理员")
    public ApiResult<String> update(@Validated @RequestBody SysManagerUpdateDTO dto) {
        iSysManagerService.update(dto);
        return ApiResult.success("修改成功");
    }

    /**
     * 获取系统管理员登入数字加英文验证码及图片
     *
     * @param response
     * @param session
     * @return void
     */
    @GetMapping("/getVerifyCode")
    @ApiOperation("获取系统管理员登入数字加英文验证码及图片")
    public void getVerifyCode(@ApiIgnore HttpServletResponse response, @ApiIgnore HttpSession session)
            throws IOException {
        // 利用图片工具生成图片
        // 第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyCodeUtil.createImage();
        // 将验证码存入Session
        session.setAttribute("managerVerifyCode", objs[0]);

        // 将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    /**
     * 系统管理员登入
     *
     * @param dto
     * @param session
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @PostMapping("/login")
    @Lock4j(keys = {"#dto.managerName"}, autoRelease = false)
    @ApiOperation("系统管理员登入")
    public ApiResult<String> login(@Validated @RequestBody SysManagerLoginDTO dto, @ApiIgnore HttpSession session) {
        // 获取服务器生成验证码
        Object managerVerifyCode = session.getAttribute("managerVerifyCode");
        // 验证码session失效
        Assert.isTrue(!ObjectUtils.isEmpty(managerVerifyCode), "验证码过期，请重新输入");
        Assert.isTrue(managerVerifyCode.toString().equalsIgnoreCase(dto.getVerifyCode()), "验证码错误");

        SysManager sysManager = iSysManagerService.getEnableManagerByName(dto.getManagerName());

        Assert.isTrue(!ObjectUtils.isEmpty(sysManager), "管理员不存在");
        Assert.isTrue(sysManager.getManagerPassword().equals(DigestUtils.md5DigestAsHex(dto.getManagerPassword().getBytes()))
                , "密码错误");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // token到期时间
        LocalDateTime tokenTimeoutDate = currentDateTime.plusSeconds(tokenTimeout);

        StpUtil.login(sysManager.getId(), SaLoginConfig
                .setExtra("managerName", sysManager.getManagerName())
                .setExtra("managerOrg", sysManager.getManagerOrg())
                // 存入token到期时间
                .setExtra("expiration", formatter.format(tokenTimeoutDate)));

        return ApiResult.success("管理员登入成功", StpUtil.getTokenValue());
    }

    /**
     * 系统管理员注销
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @GetMapping("/logout")
    @ApiOperation("系统管理员注销")
    public ApiResult<String> logout() {
        StpUtil.logout();
        return ApiResult.success("管理员注销成功");
    }

    /**
     * 判断系统管理员认证状态
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<java.lang.String>
     */
    @GetMapping("/judgeAuthen")
    @ApiOperation("判断系统管理员认证状态")
    public ApiResult<String> judgeAuthen() {
        Assert.isTrue(StpUtil.isLogin(), "管理员还未登入，请先登入！");
        return ApiResult.success("管理员已登入");
    }

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<pers.elianacc.yurayura.vo.SysManagerMsgVO>
     */
    @GetMapping("/getCurrentManagerMsg")
    @ApiOperation("获取当前登入管理员信息")
    public ApiResult<SysManagerMsgVO> getCurrentManagerMsg() {
        SysManagerMsgVO sysManagerMsgVO = new SysManagerMsgVO();
        sysManagerMsgVO.setManagerName(StpUtil.getExtra("managerName").toString());
        sysManagerMsgVO.setManagerOrg((Integer) StpUtil.getExtra("managerOrg"));

        List<String> managerRolePermission = iSysManagerService.getManagerRolePermission(StpUtil.getLoginIdAsInt());

        sysManagerMsgVO.setManagerPermission(String.join(",", managerRolePermission));
        return ApiResult.success("获取成功", sysManagerMsgVO);
    }

    /**
     * 获取当前会话的token信息
     *
     * @param
     * @return pers.elianacc.yurayura.vo.ApiResult<cn.dev33.yurayura.stp.SaTokenInfo>
     */
    @GetMapping("/getTokenInfo")
    @ApiOperation("获取当前会话的token信息")
    public ApiResult<SaTokenInfo> getTokenInfo() {
        return ApiResult.success(StpUtil.getTokenInfo());
    }

}
