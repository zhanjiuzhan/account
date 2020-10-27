package org.account.cl.permissions.impl;

import org.account.cl.permissions.AbstractTokenService;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Service
public class TokenServiceImpl extends AbstractTokenService implements EnvironmentAware {

    private static final String DEFAULT_TOKEN_HEADER = "Authorization";
    private static final String DEFAULT_TOKEN_PREFIX = "account ";
    private final static String DEFAULT_KEY = "9f6d5c6b4519c4bba50862006a18f493";

    /**
     * 默认为30分钟 单位是秒
     */
    private final static long DEFAULT_EXPIRE = 1800;

    private Environment environment;

    @Override
    public boolean isAuthenticationUrl(HttpServletRequest request, String username) {
        return true;
    }

    @Override
    protected String getKey() {
        return environment.getProperty("spring.token.key", DEFAULT_KEY);
    }

    @Override
    protected long getExpire() {
        return Long.valueOf(environment.getProperty("spring.token.expire", DEFAULT_EXPIRE + ""));
    }

    @Override
    protected String getTokenHeader() {
        return environment.getProperty("spring.token.header", DEFAULT_TOKEN_HEADER);
    }

    @Override
    protected String getTokenPrefix() {
        return environment.getProperty("spring.token.header.prefix", DEFAULT_TOKEN_PREFIX);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }
}
