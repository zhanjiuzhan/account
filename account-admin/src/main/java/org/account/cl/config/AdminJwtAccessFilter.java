package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import org.account.cl.ApplicationConst;
import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 除过登陆的请求基本都要在这里进行验证授权
 * @author Administrator
 */
@Component
@Order(20)
//@WebFilter(urlPatterns = "/", filterName = "admAccessFilter")
public class AdminJwtAccessFilter extends OncePerRequestFilter implements ApplicationContextAware, SmartInitializingSingleton {

    private final static Logger logger = LoggerFactory.getLogger(AdminJwtAccessFilter.class);

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private PermissionService permissionService;

    @Value("${application.type}")
    private String applicationType;

    @Value("${application.name}")
    private String projectName;

    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(ApplicationConst.APP_ENV_WIN.equals(applicationType) || tokenService.isAuthentication(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType(RetUtils.CONTENT_TYPE_JSON);
            String res = JSON.toJSONString(new JsonView.JsonRet(403, "用户没有权限！"));
            try (Writer out = response.getWriter();) {
                response.setContentLength(res.getBytes(RetUtils.CHARACTER_CODE).length);
                out.write(res);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                Permission permission = new Permission();
                permission.setUrl(url);
                permission.setMethod(methods != null && methods.size() > 0 ? methods.iterator().next().toString() : "");
                permission.setStatus(0);
                permission.setProject(projectName);
                if (url.equals("/error")) {
                    permission.setName("系统定义");
                    permission.setStatus(1);
                    permission.setMethod("SYSTEM");
                }
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
