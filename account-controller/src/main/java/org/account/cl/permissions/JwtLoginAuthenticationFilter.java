package org.account.cl.permissions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.account.cl.JcStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 验权
 * @author Administrator
 */
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger= LoggerFactory.getLogger(JwtLoginAuthenticationFilter.class);

    /**
     * 拦截url为 "/login" 的POST请求
     */
    public JwtLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    /**
     * 从请求中获取到登录的信息 做成认证对象
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String username = null, password = null;
        if(StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            username = jsonObj.getString("username");
            password = jsonObj.getString("password");
        }

        if (!JcStringUtils.isAllNotEmpty(username, password)) {
            username = password = "";
        }

        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
    }
}
