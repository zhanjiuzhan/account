package org.account.cl.config;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
@Configuration
public class AdminConfig implements ApplicationContextAware, SmartInitializingSingleton {

    private final static Logger logger = LoggerFactory.getLogger(AdminConfig.class);

    @Value("${spring.application.name}")
    private String projectName;

    @Autowired
    private PermissionService permissionService;

    private ApplicationContext context;

    @Bean("defaultPasswordEncoder")
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 所有的Bean加载完成后的回调
     * 主要用于把所有的Controller的url放置到权限表中 更好的去控制权限
     */
    @Override
    public void afterSingletonsInstantiated() {
        RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Permission> permissions = new ArrayList<>();
        for (RequestMappingInfo info : map.keySet()){
            // 获取url的Set集合，一个方法可能对应多个url
            Set<String> patterns = info.getPatternsCondition().getPatterns();

            // 获取请求方式 Get,Post等等
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            for (String url : patterns){
                if (url.equals("/error")) {
                    continue;
                }
                Permission permission = new Permission();
                permission.setUrl(url);
                permission.setMethod(methods != null && methods.size() > 0 ? methods.iterator().next().toString() : "");
                permission.setStatus(0);
                permission.setProject(projectName);
                permissions.add(permission);
            }
        }
        upPermissionsToDb(permissions);
    }

    /**
     * 将权限信息更新到DB 中, 项目启动时会做这一步
     * @param permissions
     */
    private void upPermissionsToDb(List<Permission> permissions) {
        if (permissions.size() > 0) {
            boolean flag = permissionService.refreshPermissions(permissions.get(0).getProject(), permissions);
            if (flag) {
                logger.info("项目权限初始化完成!");
            } else {
                logger.info("项目权限初始化失败!");
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
