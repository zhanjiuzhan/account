package org.account.cl.permissions;

import com.alibaba.fastjson.JSON;
import org.account.cl.permissions.impl.TokenServiceImpl;
import org.account.cl.view.model.JsonRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 权限控制配置 用户扩展配置
 * @author Administrator
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled =true)
public class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtWebSecurityConfig.class);

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private UserDetailsTkService userDetailsTkService;
    /**
     * 授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Security init ...");
        //http.cors();

        //http.csrf().disable();

        http.authorizeRequests()
            .antMatchers("/user/**").authenticated()
            .and();

        http.formLogin().usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login");
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    /**
     * 注入自定义的userDetailService
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsTkService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 密码加密 可以自定义实现PasswordEncoder接口
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean(name = "successHandler")
    public AuthenticationSuccessHandler getSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            /**
             * 登录成功的处理
             * @param request
             * @param response
             * @param authResult 认证后用户的信息
             * @throws IOException
             * @throws ServletException
             */
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
                // 调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
                JwtUser jwtUser = (JwtUser) authResult.getPrincipal();

                String token = tokenService.generateToken(jwtUser.getUsername());
                // 返回创建成功的token
                JsonRes jsonRes = new JsonRes();
                jsonRes.setStatus(200);
                jsonRes.setData(token);
                String res = JSON.toJSONString(jsonRes);
                response.setContentLength(res.getBytes("UTF-8").length);
                Writer out = response.getWriter();
                out.write(res);
                out.flush();
            }
        };
    }

    @Bean(name = "failHandler")
    public AuthenticationFailureHandler getFailureHandler() {
        return new AuthenticationFailureHandler(){
            /**
             * 登录失败的处理 403登陆失败
             * @param request
             * @param response
             * @param e
             * @throws IOException
             * @throws ServletException
             */
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                JsonRes jsonRes = new JsonRes();
                jsonRes.setStatus(403);
                jsonRes.setMsg(e.getMessage());
                String res = JSON.toJSONString(jsonRes);
                response.setContentLength(res.getBytes("UTF-8").length);
                Writer out = response.getWriter();
                out.write(res);
                out.flush();
            }
        };
    }
}
