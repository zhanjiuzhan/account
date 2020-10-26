package org.account.cl.permissions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 权限相关的其它配置
 * @author Administrator
 */
@Configuration
public class JwtAppConfig extends WebMvcConfigurationSupport {

    private static final Logger logger= LoggerFactory.getLogger(JwtAppConfig.class);

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
}
