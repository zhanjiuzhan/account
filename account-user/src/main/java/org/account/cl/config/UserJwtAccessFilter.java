package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import org.account.cl.ApplicationConst;
import org.account.cl.JcSecurityUtils;
import org.account.cl.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 除过登陆的请求基本都要在这里进行验证授权
 * @author Administrator
 */
@Component
@Order(20)
//@WebFilter(urlPatterns = "/", filterName = "admAccessFilter")
public class UserJwtAccessFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(UserJwtAccessFilter.class);

    @Autowired
    private TokenServiceImpl tokenService;

    @Value("${spring.application.type}")
    private String applicationType;

    /**
     * persist-user 项目的key
     */
    private String PROJECT_USER_KEY = "shitupojie@liangxinhezai ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 本地测试不进行现行
        end : if(ApplicationConst.APP_ENV_WIN.equals(applicationType)) {
            filterChain.doFilter(request, response);
        } else {
            // 校验是否来源于远程调用
            String url= request.getRequestURL().toString();
            if (url.contains("/feign")) {
                String time = request.getHeader("timesnap");
                String project = request.getHeader("project");
                String sign = request.getHeader("sign");
                if (isValidSign(time, project, sign)) {
                    filterChain.doFilter(request, response);
                    break end;
                }
            }

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

    private boolean isValidSign(String time, String project, String sign) {
        switch (project) {
            case "persist-user":
                return JcSecurityUtils.md5(time + PROJECT_USER_KEY + project).equals(sign);
            default:
                return false;
        }
    }
}
