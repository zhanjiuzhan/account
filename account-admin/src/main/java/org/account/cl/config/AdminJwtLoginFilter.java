package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.account.cl.JcStringUtils;
import org.account.cl.User;
import org.account.cl.permissions.UserService;
import org.account.cl.permissions.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
@Component
@Order(10)
public class AdminJwtLoginFilter extends OncePerRequestFilter {

    private final static String LOGIN_URL = "/admin/login.do";
    private final static String REQUEST_TYPE = "POST";

    @Autowired
    private UserService userService;

    @Autowired
    private TokenServiceImpl tokenService;

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
        if (LOGIN_URL.equals(request.getRequestURI()) && REQUEST_TYPE.equals(request.getMethod())) {
            // 取得用户信息
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonStr = reader.readLine();
            String username = null;
            if (JcStringUtils.isNotBlank(jsonStr)) {
                JSONObject jsonObj = JSON.parseObject(jsonStr);
                username = jsonObj.getString("username");
                if (JcStringUtils.isEmpty(username)) {
                    username = "";
                }
                String password = jsonObj.getString("password");
                if (JcStringUtils.isEmpty(password)) {
                    password = "";
                }

                // 进行用户身份认证
                if (attachUser(username, password)) {
                    successHandler(username, response);
                }
            }
            failureHandler(username, response);
        } else {
            // 其它请求放行
            filterChain.doFilter(request, response);
        }
    }

    private boolean attachUser(String username, String password) {
        User user = userService.getUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    /**
     * 登陆认证成功的处理
     * @param username
     * @param response
     */
    private void successHandler(String username, HttpServletResponse response) {
        String token = tokenService.generateToken(username);
        sendMsg(token, response);
    }

    /**
     * 登陆认证失败的处理
     * @param username
     * @param response
     */
    private void failureHandler(String username, HttpServletResponse response) {
        String msg = username + "用户认证失败!";
        sendMsg(msg, response);
    }

    private void sendMsg(String msg, HttpServletResponse response) {
        response.setContentType(RetUtils.CONTENT_TYPE_JSON);
        JsonView.JsonRet jsonRet = new JsonView.JsonRet(msg);
        String res = JSON.toJSONString(jsonRet);
        try (Writer out = response.getWriter();) {
            response.setContentLength(res.getBytes(RetUtils.CHARACTER_CODE).length);
            out.write(res);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
