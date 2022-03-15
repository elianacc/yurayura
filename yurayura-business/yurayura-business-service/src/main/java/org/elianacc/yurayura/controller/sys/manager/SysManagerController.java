package org.elianacc.yurayura.controller.sys.manager;


import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.elianacc.yurayura.dto.*;
import org.elianacc.yurayura.service.sys.manager.ISysManagerService;
import org.elianacc.yurayura.system.util.VerifyCodeUtil;
import org.elianacc.yurayura.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * 系统管理员 controller
 *
 * @author ELiaNaCc
 * @since 2019-10-27
 */
@RestController
@RequestMapping("/api/sys/manager")
@Api(tags = "系统管理员API")
public class SysManagerController {

    @Autowired
    private ISysManagerService iSysManagerService;

    /**
     * 查询系统管理员（根据系统管理员id）
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getById")
    @ApiOperation("查询系统管理员（根据系统管理员id）")
    public ApiResult getById(IdDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        }
        return ApiResult.success("查询成功", iSysManagerService.getById(dto.getId()));
    }

    /**
     * 分页查询系统管理员
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/getPage")
    @ApiOperation("分页查询系统管理员")
    public ApiResult getPage(@RequestBody SysManagerSelectDto dto) {
        if (ObjectUtils.isEmpty(dto.getPageNum())) {
            return ApiResult.warn("页码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getPageSize())) {
            dto.setPageSize(10); // 页记录数默认10
        }
        PageInfo<Map<String, Object>> pageInfo = iSysManagerService.getPage(dto);
        if (pageInfo.getTotal() == 0) {
            return ApiResult.warn("查询不到数据");
        }
        return ApiResult.success("分页查询成功", pageInfo);
    }

    /**
     * 添加系统管理员
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/insert")
    @ApiOperation("添加系统管理员")
    public ApiResult insert(@RequestBody SysManagerInsertDto dto) {
        if (ObjectUtils.isEmpty(dto.getManagerName())) {
            return ApiResult.warn("管理员名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getManagerPassword())) {
            return ApiResult.warn("管理员密码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getManagerStatus())) {
            return ApiResult.warn("状态不能为空");
        } else if (dto.getManagerName().length() > 20) {
            return ApiResult.warn("管理员名不能超过20个字符");
        }
        String warn = iSysManagerService.insert(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("添加成功");
    }

    /**
     * 修改系统管理员
     *
     * @param dto
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PutMapping("/update")
    @ApiOperation("修改系统管理员")
    public ApiResult update(@RequestBody SysManagerUpdateDto dto) {
        if (ObjectUtils.isEmpty(dto.getId())) {
            return ApiResult.warn("id不能为空");
        } else if (ObjectUtils.isEmpty(dto.getManagerStatus())) {
            return ApiResult.warn("状态不能为空");
        }
        String warn = iSysManagerService.update(dto);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
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
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @PostMapping("/login")
    @ApiOperation("系统管理员登入")
    public ApiResult login(@RequestBody SysManagerLoginDto dto, @ApiIgnore HttpSession session) {

        if (ObjectUtils.isEmpty(dto.getManagerName())) {
            return ApiResult.warn("用户名不能为空");
        } else if (ObjectUtils.isEmpty(dto.getManagerPassword())) {
            return ApiResult.warn("密码不能为空");
        } else if (ObjectUtils.isEmpty(dto.getVerifyCode())) {
            return ApiResult.warn("验证码不能为空");
        }
        String warn = iSysManagerService.login(dto, session);
        if (!ObjectUtils.isEmpty(warn)) {
            return ApiResult.warn(warn);
        }
        return ApiResult.success("管理员登入成功");
    }

    /**
     * 系统管理员注销
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/logout")
    @ApiOperation("系统管理员注销")
    public ApiResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ApiResult.success("管理员注销成功");
    }

    /**
     * 判断系统管理员认证状态
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/judgeAuthen")
    @ApiOperation("判断系统管理员认证状态")
    public ApiResult judgeAuthen() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return ApiResult.warn("管理员还未登入，请先登入！");
        }
        return ApiResult.success("管理员已登入");
    }

    /**
     * 获取当前登入管理员信息
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @GetMapping("/getCurrentManagerMsg")
    @ApiOperation("获取当前登入管理员信息")
    public ApiResult getCurrentManagerMsg() {
        Map<String, Object> currentManagerMsg = iSysManagerService.getCurrentManagerMsg();
        return ApiResult.success("获取成功", currentManagerMsg);
    }

    /**
     * 未认证
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @RequestMapping("/notAuthentication")
    @ApiIgnore
    public ApiResult notAuthentication() {
        return ApiResult.notAuthentication();
    }

    /**
     * 未授权
     *
     * @param
     * @return org.elianacc.yurayura.vo.ApiResult
     */
    @RequestMapping("/notAuthorization")
    @ApiIgnore
    public ApiResult notAuthorization() {
        return ApiResult.notAuthorization();
    }
}

