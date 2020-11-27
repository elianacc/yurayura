package org.cny.yurayura.controller.sys.manager;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.cny.yurayura.system.annotation.PreventRepeatSubmit;
import org.cny.yurayura.dto.MangerLoginDto;
import org.cny.yurayura.service.sys.manager.IManagerService;
import org.cny.yurayura.system.util.VerifyCodeUtil;
import org.cny.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 系统管理员 controller
 *
 * @author CNY
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/sys/manager")
@Api(tags = "系统管理员API")
public class ManagerController {

    @Autowired
    private IManagerService iManagerService;

    /**
     * 获取管理员登入数字加英文验证码及图片
     *
     * @param response
     * @param session
     * @return void
     */
    @GetMapping("/getVerifyCode")
    @ApiOperation("获取管理员登入数字加英文验证码及图片")
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
     * 管理员登入
     *
     * @param dto
     * @param session
     * @param response
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PreventRepeatSubmit
    @PostMapping("/login")
    @ApiOperation("管理员登入")
    public ApiResult login(@RequestBody MangerLoginDto dto, @ApiIgnore HttpSession session
            , @ApiIgnore HttpServletResponse response) {

        if (StringUtils.isEmpty(dto.getManagerName().trim())) {
            return ApiResult.warn("用户名不能为空");
        } else if (StringUtils.isEmpty(dto.getManagerPassword().trim())) {
            return ApiResult.warn("密码不能为空");
        } else if (StringUtils.isEmpty(dto.getVerifyCode().trim())) {
            return ApiResult.warn("密码不能为空");
        }
        return iManagerService.login(dto, session, response);
    }

    /**
     * 管理员注销
     *
     * @param session
     * @return org.cny.yurayura.vo.ApiResult
     */
    @PostMapping("/logout")
    @ApiOperation("管理员注销")
    public ApiResult logout(@ApiIgnore HttpSession session) {
        // 移除管理员session
        session.removeAttribute("managerSession");
        return ApiResult.success("管理员注销成功");
    }

}

