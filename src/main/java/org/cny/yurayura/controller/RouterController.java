package org.cny.yurayura.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 页面路由 controller
 *
 * @author CNY
 * @date 2019年10月28日 21:56
 */
@Controller
@ApiIgnore
public class RouterController {

    @RequestMapping("/manage/manager_login")
    public String manager_loginView() {
        return "manager_login";
    }

    @RequestMapping("/manage/manager_unlogin")
    public String manager_unloginView() {
        return "manager_unlogin";
    }

    @RequestMapping("/manage/comic_info")
    public String comic_infoView() {
        return "comic_info";
    }

}