package org.account.cl.config;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.UserService;
import org.account.cl.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
public class LoginPasswordFilter implements UserJwtLoginFilter.VirtualFilter  {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public void doFilter(Map<String, Object> user, HttpServletResponse response, UserJwtLoginFilter.VirtualFilterChain filterChain) {
        String username = String.valueOf(user.get("username")).trim();
        String password = String.valueOf(user.get("password")).trim();

        // 进行用户身份认证 用户名和密码去掉首尾空格
        if (userService.isOneUser(username, password) && userService.isValidUser(username)) {
            String token = tokenService.generateToken(username);
            RetUtils.sendJsonResponse(new JsonView.JsonRet(token), response);
        } else {
            User tmp = userService.getUserByUsername(username);
            String msg = "用户名或密码输入有误！";
            if (tmp != null) {
               if (tmp.getIsEnable() == 0) {
                   msg = "用户已经不可用！";
               } else if(tmp.getLocked() == 1) {
                   msg = "用户已经被锁！";
               } else if(tmp.getExpired() == 1) {
                   msg = "用户已经过期！";
               } else if(tmp.getCredentialsExpired() == 1) {
                   msg = "用户凭证已经过期！";
               }
            } else {
                // 记录用户登录失败 防止用户一直登录
                userService.loginNum(username, UserDao.USER_OP.ADD);
            }
            RetUtils.sendJsonResponse(new JsonView.JsonRet(401, msg), response);
        }
    }

    @Override
    @Autowired
    public void apply(UserJwtLoginFilter loginFilter) {
        loginFilter.apply(this);
    }
}
