package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.account.cl.JcSecurityUtils;
import org.account.cl.JcStringUtils;
import org.account.cl.User;
import org.account.cl.permissions.UserService;
import org.account.cl.permissions.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

/**
 * 用户登陆的过滤器
 * @author Administrator
 */
@Configuration
@Order(10)
public class AdminJwtLoginFilter extends OncePerRequestFilter {

    private final static String LOGIN_URL = "/admin/login.do";
    private final static String REQUEST_TYPE = "POST";

    @Autowired
    private UserService userService;

    @Autowired
    private TokenServiceImpl tokenService;

    @Value("${security.my.private-key}")
    private String privateKey;

    @Value("${security.my.public-key}")
    private String publicKey;

    {
        this.privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsFC8TxOaiqiNwi+PFUWMU5YOqllzThHyQg2j27Viw0BNDCBUA2vVxTR/tgGjjluSCaqjMzHraA7ZtQinvtD0jR7wwqmIUqtnlgkDAi62pTF3AANnuLRmRGdXX/Qy8x7T+1S6zm4215R+i3oMwM5FNJkr+z0If1nVtuEsoxyGnjAgMBAAECgYBfHle8K7Tg07YKsR8VuDl40FagliZMNxAgnx3UCR7f5cO5tlbzZcDQr+bbdxjZ/0xYo5p6p6qHAtYQY94tBrlifTYn5HbmQacaVQ2eHLYi4IhrzomH6CerSYL6KJPM8uccwOsPmTjYbrYO1Q5ewmmNau20WGYBb2PGwe9OARPMMQJBANF+hnpBceUi/dboFZCARleJlpMrz/T8Nf/jKTNuZqKapq5WZRTVgGPg6G2KumwV4TjD3iqzixddILpSYwi+VTkCQQCp4Xsp2lzIwcEtC2gAhxsbX+x3RThIcA7P33W3MXMtROjOMDNotVFXy8HavcGmskctK4gjBwsHtAOQ0UYKNLP7AkAqCPc2xLbzwSep3XumOPbkTak28o2RnKHBLHVx6m8RpXJYfOFfYs+WOuSoRjPNyD4ew75qVwhLsdYFTM6uTHKRAkEAlmdKawCoWxmn1SCfILB9YFwp+GLxdLi5dHNsPhfq2C6FS1/DdDXr4aZFaEvOcq6uc6Bx9EkdC+HlskaGEy0cFwJAA8LXayt346misCrkG3ZORUEFHoTCwTdFmqArzBBDiHmiUXddZaDmQsMyX3yVaYBCltl/n3tu7H5Gffht242oqA==";
        this.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLBQvE8TmoqojcIvjxVFjFOWDqpZc04R8kINo9u1YsNATQwgVANr1cU0f7YBo45bkgmqozMx62gO2bUIp77Q9I0e8MKpiFKrZ5YJAwIutqUxdwADZ7i0ZkRnV1/0MvMe0/tUus5uNteUfot6DMDORTSZK/s9CH9Z1bbhLKMchp4wIDAQAB";
    }

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

                    // 进行用户身份认证 用户名和密码去掉首尾空格
                    if (attachUser(username.trim(), password.trim())) {
                        successHandler(username, response);
                        break end;
                    }
                }
            // 参数信息错误
            } catch (Exception ignored) {}
            failureHandler(response);
        } else {
            // 其它请求放行
            filterChain.doFilter(request, response);
        }
    }

    private boolean attachUser(String username, String password) {
        // password 需要进行Base64解码
        byte[] pwd = Base64.getDecoder().decode(password);
        // 进行RSA校验
        String original = JcSecurityUtils.decryptByPrivate(new String(pwd), JcSecurityUtils.getPrivateKey(privateKey));
        // 从数据库中取得user信息
        User user = userService.getUserByUsername(username);
        // 进行密码校验
        return user != null && getPasswordEncoder().matches(original, user.getPassword());
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

    /**
     * 登陆认证失败的处理
     * @param response
     */
    private void failureHandler(HttpServletResponse response) {
        sendMsg(new JsonView.JsonRet(401, "认证失败，用户名或密码有误！"), response);
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

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 将密码变成数据库中存储的形式
     * @param password
     * @return
     */
    private String getBCryptPassword(String password) {
        return getPasswordEncoder().encode(password);
    }

    /**
     * 将密码变成前端发送过来的形式
     * @param password
     * @return
     */
    private String getWebPassword(String password) {
        password = JcSecurityUtils.encryptByPublic(password.getBytes(), JcSecurityUtils.getPublicKey(publicKey));
        password = Base64.getEncoder().encodeToString(password.getBytes());
        return password;
    }

    public static void main(String[] args) {
        AdminJwtLoginFilter obj = new AdminJwtLoginFilter();
        String pwd = "123456";
        String publicPassword = obj.getWebPassword(pwd);
        System.out.println("前端加密后是: " + publicPassword);

        String pt = new String(Base64.getDecoder().decode(publicPassword));
        String password = JcSecurityUtils.decryptByPrivate(pt, JcSecurityUtils.getPrivateKey(obj.privateKey));
        System.out.println("原密码是: " + password);

        if (password.equals(pwd)) {
            String dbPassowrd = obj.getBCryptPassword(pwd);
            System.out.println("保存在数据库中的是: " + dbPassowrd);
        }
    }
}
