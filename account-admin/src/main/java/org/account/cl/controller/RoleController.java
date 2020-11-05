package org.account.cl.controller;

import org.account.cl.Role;
import org.account.cl.RoleService;
import org.account.cl.condition.RoleQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add.do")
    public JsonView add(Role role) {
        checkRole(role);
        return JsonRetFactory.getRet(roleService.add(role));
    }

    @DeleteMapping("/del/{id}.do")
    public JsonView delete(@PathVariable int id) {
        checkId(id);
        return JsonRetFactory.getRet(roleService.delete(id));
    }

    @PutMapping("/isDisable.do")
    public JsonView isDisable(int id, boolean op) {
        checkId(id);
        return JsonRetFactory.getRet(roleService.isDisable(id, op));
    }

    @PutMapping("/updateRole.do")
    public JsonView updateRole(int id, RoleQuery query) {
        checkId(id);
        checkRoleQuery(query);
        return JsonRetFactory.getRet(roleService.updateRole(id, query));
    }

    @GetMapping("/get/{id}.do")
    public JsonView get(@PathVariable int id) {
        checkId(id);
        return JsonRetFactory.getRet(roleService.get(id));
    }

    @GetMapping("/gets.do")
    public JsonView gets() {
        return JsonRetFactory.getRet(roleService.gets());
    }

    @GetMapping("/getsByCondition.do")
    public JsonView getsByCondition(RoleQuery query) {
        checkRoleQuery(query);
        return JsonRetFactory.getRet(roleService.getsByCondition(query));
    }

    private void checkId(int id) {
        if (id < 1) {
            ExceptionEnum.INVALID_PARAMETER1.assertTrue(false, "id");
        }
    }

    private void checkRole(Role role) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }

    private void checkRoleQuery(RoleQuery query) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }
}
