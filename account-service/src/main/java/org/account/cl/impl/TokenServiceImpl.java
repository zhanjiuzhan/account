package org.account.cl.impl;

import org.account.cl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class TokenServiceImpl extends AbstractTokenService implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String DEFAULT_TOKEN_HEADER = "Authorization";
    private static final String DEFAULT_TOKEN_PREFIX = "account ";
    private final static String DEFAULT_KEY = "9f6d5c6b4519c4bba50862006a18f493";

    @Autowired
    private RelationService relationService;

    /**
     * 默认为30分钟 单位是秒
     */
    private final static long DEFAULT_EXPIRE = 1800;

    private Environment environment;

    @Override
    public boolean isAuthenticationUrl(HttpServletRequest request, String username) {
        if (JcStringUtils.isBlank(username)) {
            // 一般不会走到
            return false;
        }

        String projectName = (String)request.getAttribute("projectName");

        if (JcStringUtils.isBlank(projectName)) {
            projectName = "NONE";
        }

        logger.info("---开始鉴权---");
        String url = request.getRequestURI();
        logger.info("---project: " + projectName + " url: " + url);
        logger.info("---username: " + username);

        // 首先确认其是否是超管
        if (relationService.getPermissionByUserAndPro("ALL", username).size() == 1) {
            logger.info("---超级管理员");
            return true;
        }

        // 取得用户所具有的所有权限路径
        List<String> urlList = relationService.getPermissionByUserAndPro(projectName, username).stream().map(Permission::getUrl).collect(Collectors.toList());
        if (urlList.contains(url)) {
            // 权限路径中包含该url 鉴权通过
            logger.info("---用户具有该权限");
            return true;
        }
        logger.info("---用户不具有该权限");
        return false;
    }

    @Override
    protected String getKey() {
        return environment.getProperty("spring.token.key", DEFAULT_KEY);
    }

    @Override
    protected long getExpire() {
        return Long.parseLong(environment.getProperty("spring.token.expire", DEFAULT_EXPIRE + ""));
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
