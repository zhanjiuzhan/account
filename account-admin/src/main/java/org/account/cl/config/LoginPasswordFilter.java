package org.account.cl.config;

import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.UserService;
import org.account.cl.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
public class LoginPasswordFilter implements AdminJwtLoginFilter.VirtualFilter  {

    @Autowired
    @Qualifier("defaultPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    public void doFilter(Map<String, Object> user, HttpServletResponse response, AdminJwtLoginFilter.VirtualFilterChain filterChain) {
        String username = String.valueOf(user.get("username")).trim();
        String password = String.valueOf(user.get("password")).trim();

        // 进行用户身份认证 用户名和密码去掉首尾空格
        if (attachUser(username, password)) {
            String token = tokenService.generateToken(username);
            RetUtils.sendJsonResponse(new JsonView.JsonRet(token), response);
        } else {
            // 记录用户登录失败 防止用户一直登录
            userService.loginNum(username, UserDao.USER_OP.ADD);
            RetUtils.sendJsonResponse(new JsonView.JsonRet(401, "用户名或密码输入有误！"), response);
        }
    }

    private boolean attachUser(String username, String password) {
        // 对密码进行解密
        String original = userService.getDecodePassword(password);
        // 从数据库中取得user信息
        User user = userService.getUserByUsername(username);
        // 进行密码校验
        return user != null && passwordEncoder.matches(original, user.getPassword());
    }

    @Override
    @Autowired
    public void apply(AdminJwtLoginFilter loginFilter) {
        loginFilter.apply(this);
    }
}
