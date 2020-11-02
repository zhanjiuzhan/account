package org.account.cl.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.account.cl.JcStringUtils;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 用户登陆的过滤器
 * @author Administrator
 */
@Order(10)
@Component
//@WebFilter(urlPatterns = "/", filterName = "admLoginFilter")
public class AdminJwtLoginFilter extends OncePerRequestFilter {

    private List<AdminJwtLoginFilter.VirtualFilter> filters;
    private final static String LOGIN_URL = "/admin/login.do";
    private final static String REQUEST_TYPE = "POST";

    /**
     * Content-Type application/json;charset=utf-8
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 登陆请求处理
        if (LOGIN_URL.equals(request.getRequestURI()) && REQUEST_TYPE.equals(request.getMethod())) {
            String username = "";
            try {
                // 取得用户信息
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String jsonStr = reader.readLine();

                if (JcStringUtils.isNotBlank(jsonStr)) {
                    JSONObject jsonObj = JSON.parseObject(jsonStr);
                    username = jsonObj.getString("username");
                    if (JcStringUtils.isEmpty(username)) {
                        username = "";
                    }
                    String password = jsonObj.getString("password");
                    if (JcStringUtils.isEmpty(password)) {
                        password = "";
                    }

                    Map<String, Object> userMap = new HashMap<>(2);
                    userMap.put("username", username);
                    userMap.put("password", password);

                    // 用户认证
                    VirtualFilterChain virtualChain = new VirtualFilterChain(filters);
                    virtualChain.doFilter(userMap, response);
                }
            } catch (Exception ignored) {
                RetUtils.sendJsonResponse(new JsonView.JsonRet(400, "客户端参数传递有误！"), response);
            }
        } else {
            // 其它请求放行
            filterChain.doFilter(request, response);
        }
    }

    public void apply(AdminJwtLoginFilter.VirtualFilter filter) {
        if (filter == null) {
            return;
        }
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
        Collections.sort(filters, AdminJwtLoginFilter.LoginOrderComparator.COMPARATOR);
    }

    public interface VirtualFilter {

        /**
         * 过滤
         * @param user
         * @param response
         * @param filterChain
         */
        void doFilter(Map<String, Object> user, HttpServletResponse response, VirtualFilterChain filterChain);

        /**
         * 添加自定义login过滤器到login过滤器
         * @param loginFilter
         */
        void apply(AdminJwtLoginFilter loginFilter);
    }

    public static class VirtualFilterChain {

        private List<VirtualFilter> filters;
        private int currentPoint;

        public VirtualFilterChain(List<VirtualFilter> filters) {
            this.filters = filters;
            this.currentPoint = 0;
        }

        public void doFilter(Map<String, Object> user, HttpServletResponse response) {
            if (filters != null && currentPoint < filters.size()) {
                this.currentPoint++;
                filters.get(currentPoint-1).doFilter(user, response, this);
            }
        }
    }

    private static class LoginOrderComparator extends OrderComparator {
        public static final LoginOrderComparator COMPARATOR = new LoginOrderComparator();

        @Override
        protected int getOrder(Object obj) {
            if (obj instanceof Ordered) {
                return ((Ordered) obj).getOrder();
            }
            if (obj != null) {
                Class<?> clazz = (obj instanceof Class ? (Class<?>) obj : obj.getClass());
                Order order = AnnotationUtils.findAnnotation(clazz, Order.class);
                if (order != null) {
                    return order.value();
                }
            }
            return Ordered.LOWEST_PRECEDENCE;
        }
    }
}
