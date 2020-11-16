package org.account.cl.controller.feign;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.RelationService;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.impl.TokenServiceImpl;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 访问相关控制器
 * @author Administrator
 */
@Controller
@RequestMapping("/feign")
public class AccessController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TokenServiceImpl tokenServiceImpl;

    @Autowired
    private RelationService relationService;

    @PostMapping("/upPermissions.do")
    public JsonView upPermissions(@RequestBody List<Permission> permissions) {
        String projectName = "";
        for (Permission permission : permissions) {
            ExceptionEnum.INVALID_PARAMETER2.assertNotNull(permission.getProject(), "项目名要有");
            ExceptionEnum.INVALID_PARAMETER2.assertNotNull(permission.getUrl(), "url要有");
            ExceptionEnum.INVALID_PARAMETER2.assertNotNull(permission.getMethod(), "method要有");
            permission.setStatus(0);

            if ("".equals(projectName)) {
                projectName = permission.getProject();
            } else if (!projectName.equals(permission.getProject())) {
                ExceptionEnum.INVALID_PARAMETER2.assertTrue(false, "不是一个项目！");
            }
        }

        // 说明permission信息没有成员
        if ("".equals(projectName)) {
            return JsonRetFactory.getRet(true);
        }
        return JsonRetFactory.getRet(permissionService.refreshPermissions(projectName, permissions));
    }

    /**
     * 用户是否已经登陆了
     * @param token
     * @param username
     * @return
     */
    @GetMapping("/isLogin.do")
    public JsonView isLogin(String username, String token) {
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(username, "username必须是有效的");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(token, "token必须是有效的");
        String name = tokenServiceImpl.getUsernameByToken(token);
        if (name != null && name.equals(username)) {
            return JsonRetFactory.getRet(true);
        }
        return JsonRetFactory.getRet(false);
    }

    /**
     * 用户是否具有权限信息
     * @param username
     * @param token
     * @param project
     * @param url
     * @param method
     * @return
     */
    @GetMapping("/isPermission.do")
    public JsonView isPermission(String username, String token, String project, String url, String method) {
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(username, "username必须是有效的");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(token, "token必须是有效的");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(project, "project必须是有效的");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(url, "url必须是有效的");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(method, "method必须是有效的");
        String name = tokenServiceImpl.getUsernameByToken(token);
        if (name != null && name.equals(username)) {
            // 用户有效
            List<Permission> permissions = relationService.getPermissionByUserAndPro(project, username);
            Permission tmp = new Permission().setMethod(method).setProject(project).setUrl(url);
            if (permissions.contains(tmp)) {
                return JsonRetFactory.getRet(true);
            }
        }
        return JsonRetFactory.getRet(false);
    }
}
