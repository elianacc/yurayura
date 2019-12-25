package org.cny.yurayura.controller.router;

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

    @RequestMapping("/business/manager_login")
    public String manager_loginView() {
        return "business/manager_login";
    }

    @RequestMapping("/business/manager_unlogin")
    public String manager_unloginView() {
        return "business/manager_unlogin";
    }

    @RequestMapping("/business/comic_info")
    public String comic_infoView() {
        return "business/comic_info";
    }

}
