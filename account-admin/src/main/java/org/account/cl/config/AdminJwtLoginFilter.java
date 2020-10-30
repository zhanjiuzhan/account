package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.account.cl.JcStringUtils;
import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.permissions.UserService;
import org.account.cl.permissions.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 用户登陆的过滤器
 * @author Administrator
 */
@Order(10)
@Component
public class AdminJwtLoginFilter extends OncePerRequestFilter {

    private final static String LOGIN_URL = "/admin/login.do";
    private final static String REQUEST_TYPE = "POST";
    private final static int LOGIN_NUM = 3;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    @Qualifier("defaultPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * Content-Type application/json;charset=utf-8
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登陆请求处理
        end: if (LOGIN_URL.equals(request.getRequestURI()) && REQUEST_TYPE.equals(request.getMethod())) {
            try {
                // 取得用户信息
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String jsonStr = reader.readLine();

                if (JcStringUtils.isNotBlank(jsonStr)) {
                    JSONObject jsonObj = JSON.parseObject(jsonStr);
                    String username = jsonObj.getString("username");
                    if (JcStringUtils.isEmpty(username)) {
                        username = "";
                    }
                    String password = jsonObj.getString("password");
                    if (JcStringUtils.isEmpty(password)) {
                        password = "";
                    }

                    // 用户是否多次输入有误
                    long num = userService.loginNum(username, UserDao.USER_OP.GET);
                    if (num >= LOGIN_NUM) {
                        sendMsg(new JsonView.JsonRet(401, "用户名密码错误数大于" + LOGIN_NUM + ", 请一小时后再试！"), response);
                        break end;
                    }

                    // 进行用户身份认证 用户名和密码去掉首尾空格
                    if (attachUser(username.trim(), password.trim())) {
                        successHandler(username, response);
                        break end;
                    }
                }
            // 参数信息错误
            } catch (Exception ignored) {}
            sendMsg(new JsonView.JsonRet(401, "认证失败，用户名或密码有误！"), response);
        } else {
            // 其它请求放行
            filterChain.doFilter(request, response);
        }
    }

    private boolean attachUser(String username, String password) {
        // 对密码进行解密
        String original = userService.getDecodePassword(password);
        // 从数据库中取得user信息
        User user = userService.getUserByUsername(username);
        // 进行密码校验
        boolean flag = user != null && passwordEncoder.matches(original, user.getPassword());
        if (!flag) {
            // 输入密码有误 操作数添加
            userService.loginNum(username, UserDao.USER_OP.ADD);
        }
        return flag;
    }

    /**
     * 登陆认证成功的处理
     * @param username
     * @param response
     */
    private void successHandler(String username, HttpServletResponse response) {
        String token = tokenService.generateToken(username);
        sendMsg(new JsonView.JsonRet(token), response);
    }

    private void sendMsg(JsonView.JsonRet jsonRet, HttpServletResponse response) {
        response.setContentType(RetUtils.CONTENT_TYPE_JSON);
        String res = JSON.toJSONString(jsonRet);
        try (Writer out = response.getWriter();) {
            response.setContentLength(res.getBytes(RetUtils.CHARACTER_CODE).length);
            out.write(res);
            out.flush();
        } catch (Exception e) {
            // 其实不会走到的
            e.printStackTrace();
        }
    }
}
