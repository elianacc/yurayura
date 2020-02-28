package org.cny.yurayura.component.interceptor;

import org.cny.yurayura.entity.sys.manager.Manager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台页面 interceptor
 *
 * @author CNY
 * @since 2019-1-15
 */
@Component
public class BusinessPageInterceptor implements HandlerInterceptor {

    /**
     * 渲染视图之后被调用. 释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) {

    }

    /**
     * 调用目标方法之后, 但渲染视图之前.
     * 可以对请求域中的属性或视图做出修改.
     */
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) {

    }

    /**
     * 该方法在目标方法之前被调用.
     * 若返回值为 true, 则继续调用后续的拦截器和目标方法.
     * 若返回值为 false, 则不会再调用后续的拦截器和目标方法.
     * 可以考虑做权限. 日志, 事务等.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

        HttpSession session = request.getSession();
        // 获取管理员session
        Manager aManager = (Manager) session.getAttribute("managerSession");
        if (StringUtils.isEmpty(aManager)) {
            // 如果管理员没登入跳转登入提醒页
            request.getRequestDispatcher("/business/manager_unlogin").forward(request, response);
            return false;
        } else {
            return true;
        }
    }

}
