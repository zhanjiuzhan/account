package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import org.account.cl.permissions.impl.TokenServiceImpl;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminJwtAccessFilter extends OncePerRequestFilter {
    @Autowired
    private TokenServiceImpl tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(tokenService.isAuthentication(request)) {
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

}
