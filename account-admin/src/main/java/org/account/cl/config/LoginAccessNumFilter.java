package org.account.cl.config;

import org.account.cl.UserDao;
import org.account.cl.permissions.UserService;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录访问数过滤器
 * @author Administrator
 */
@Order(1)
@Component
public class LoginAccessNumFilter implements AdminJwtLoginFilter.VirtualFilter {

    private final static int LOGIN_NUM = 3;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(Map<String, Object> user, HttpServletResponse response, AdminJwtLoginFilter.VirtualFilterChain filterChain) {
        // 用户是否多次输入有误
        long num = userService.loginNum(String.valueOf(user.get("username")), UserDao.USER_OP.GET);
        if (num >= LOGIN_NUM) {
            RetUtils.sendJsonResponse(new JsonView.JsonRet(401, "用户登录失败数大于" + LOGIN_NUM + ", 请一小时后再试！"), response);
        } else {
            filterChain.doFilter(user, response);
        }
    }

    @Override
    @Autowired
    public void apply(AdminJwtLoginFilter loginFilter) {
        loginFilter.apply(this);
    }
}
