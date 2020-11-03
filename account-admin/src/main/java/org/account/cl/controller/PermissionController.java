package org.account.cl.controller;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/get/{id}.do")
    public JsonView get(@PathVariable int id) {
        checkId(id);
        return JsonRetFactory.getRet(permissionService.get(id));
    }

    @GetMapping("/getsByCondition.do")
    public JsonView getsByCondition(PermissionQuery query) {
        checkQuery(query);
        return JsonRetFactory.getRet(permissionService.getsByCondition(query));
    }

    @PostMapping("/add.do")
    public JsonView add(Permission permission) {
        permission.setStatus(0);
        checkPermission(permission);
        return JsonRetFactory.getRet(permissionService.add(permission));
    }

    @PostMapping("/batchAdd.do")
    public JsonView batchAdd(@RequestBody List<Permission> permissions) {
        for (Permission permission : permissions) {
            checkPermission(permission);
        }
        return JsonRetFactory.getRet(permissionService.batchAdd(permissions));
    }

    @PutMapping("/updatePermission.do")
    public JsonView updatePermission(int id, PermissionQuery query) {
        checkId(id);
        checkQuery(query);
        return JsonRetFactory.getRet(permissionService.updatePermission(id, query));
    }

    @PutMapping("/updatePermission2.do")
    public JsonView updatePermission2(int id, String name, int status) {
        checkId(id);
        // 参数校验 TODO
        return JsonRetFactory.getRet(permissionService.updatePermission2(id, name, status));
    }

    @DeleteMapping("del/{id}.do")
    public JsonView delPermission(@PathVariable int id) {
        checkId(id);
        return JsonRetFactory.getRet(permissionService.delPermission(id));
    }

    private void checkId(int id) {
        if (id < 1) {
            ExceptionEnum.INVALID_PARAMETER1.assertTrue(false, "id");
        }
    }

    private void checkPermission(Permission permission) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }

    private void checkQuery(PermissionQuery query) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }
}
