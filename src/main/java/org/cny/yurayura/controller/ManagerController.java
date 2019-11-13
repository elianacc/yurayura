package org.cny.yurayura.controller;


import lombok.extern.slf4j.Slf4j;
import org.cny.yurayura.annotation.Submit;
import org.cny.yurayura.entity.Manager;
import org.cny.yurayura.service.IManagerService;
import org.cny.yurayura.util.VerifyCodeUtil;
import org.cny.yurayura.vo.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 管理员 controller
 * </p>
 *
 * @author CNY
 * @since 2019-10-27
 */
@Slf4j
@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private IManagerService iManagerService;

    /**
     * 获取数字加英文验证码图片
     *
     * @param response
     * @param session
     * @return void
     */
    @GetMapping("/getVerifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyCodeUtil.createImage();
        //将验证码存入Session
        session.setAttribute("verifyImageCode", objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    /**
     * 管理员登入
     *
     * @param verifyCode
     * @param manager
     * @param session
     * @param response
     * @return org.cny.yurayura.vo.Msg
     */
    @Submit
    @PostMapping("/login")
    public Msg login(@RequestParam String verifyCode, Manager manager, HttpSession session,
                     HttpServletResponse response) throws UnsupportedEncodingException {

        // 获取服务器生成验证码
        Object verifyImageCode = session.getAttribute("verifyImageCode");
        if (verifyImageCode.toString().equalsIgnoreCase(verifyCode)) {
            // 查询有没有匹配管理员
            Manager aManager = iManagerService.getOneByNameAndPass(manager);
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
                log.info("管理员：" + aManager.getManagerName() + "，登入成功");
                return Msg.success("管理员登入成功");
            } else {
                return Msg.warn("用户名或密码错误");
            }
        } else {
            return Msg.warn("验证码错误");
        }
    }

    /**
     * 管理员注销
     *
     * @param session
     * @return org.cny.yurayura.vo.Msg
     */
    @PostMapping("/logout")
    public Msg logout(HttpSession session) {
        // 移除管理员session
        session.removeAttribute("managerSession");
        if (StringUtils.isEmpty(session.getAttribute("managerSession"))) {
            return Msg.success("管理员注销成功");
        } else {
            return Msg.fail("系统错误");
        }
    }

}

