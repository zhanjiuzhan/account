package org.account.cl.controller.feign;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
