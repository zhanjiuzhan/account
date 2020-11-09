package org.account.cl.controller;

import org.account.cl.LogUtils;
import org.account.cl.RelationService;
import org.account.cl.Role;
import org.account.cl.RoleService;
import org.account.cl.condition.RoleQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);
    private LogUtils logUtils = new LogUtils(logger);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RelationService relationService;

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

    /**
     * 添加一个角色信息
     * @param role
     * @return msg 或者 true 或则 false
     */
    @PostMapping("/add.do")
    public JsonView add(Role role) {
        long start = logUtils.start("添加一个角色信息, role: " + role.toString());
        checkRole(role);
        boolean flag = roleService.add(role);
        logUtils.end("添加一个角色信息, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
    }

    /**
     * 删除一个角色信息
     * TODO 暂时不提供强制删除吧
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}.do")
    public JsonView delete(@PathVariable int id) {
        long start = logUtils.start("删除一个角色信息, roleId: " + id);
        checkId(id);
        ExceptionEnum.INVALID_PARAMETER2.assertFalse(relationService.isExistRoleInRelation(id), "请解绑用户和角色以及角色和权限之间的关系！");
        boolean flag = roleService.delete(id);
        logUtils.end("删除一个角色信息, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
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

    private void checkId(int id) {
        if (id < 1) {
            ExceptionEnum.INVALID_PARAMETER1.assertTrue(false, "id");
        }
    }

    private void checkRole(Role role) {
        // 创建role 时其 sid是自动生成的 所以不要输入 还有参数名一般是个数据库字段名不一样的 这边懒得对应了
        role.setSid(-1);
        if (role.getPid() > 0) {
            Role pRole = roleService.get(role.getPid());
            ExceptionEnum.INVALID_PARAMETER2.assertTrue(pRole != null, "父角色信息不存在");
        }
        // 用户的代号还是必须要有的
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(role.getName(), "用户name不能为空");
        ExceptionEnum.INVALID_PARAMETER2.assertNotNull(role.getDescription(), "用户中文描述不能为空");
    }

    private void checkRoleQuery(RoleQuery query) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }
}
