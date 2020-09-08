package org.account.cl.permissions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 * TODO 不懂 配置登陆的过滤器吧
 * @author Administrator
 */
public class JwtJsonLoginConfigure<T extends JwtJsonLoginConfigure<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private static final Logger logger= LoggerFactory.getLogger(JwtJsonLoginConfigure.class);

    private JwtLoginAuthenticationFilter loginFilter;
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;

    public JwtJsonLoginConfigure() {
        this.loginFilter = new JwtLoginAuthenticationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        //设置Filter使用的AuthenticationManager,这里取公共的即可
        loginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置失败的Handler
        loginFilter.setAuthenticationFailureHandler(failureHandler);
        //不将认证后的context放入session
        loginFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        //设置成功的Handler
        loginFilter.setAuthenticationSuccessHandler(successHandler);

        JwtLoginAuthenticationFilter filter = postProcess(loginFilter);
        //指定Filter的位置
        http.addFilterAfter(filter, LogoutFilter.class);
    }

    @Autowired
    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Autowired
    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }
}
