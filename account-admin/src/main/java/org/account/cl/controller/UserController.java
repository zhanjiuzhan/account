package org.account.cl.controller;

import org.account.cl.*;
import org.account.cl.condition.UserQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get.do")
    public JsonView getUser(String username) {
        checkUsername(username);

        User user = userService.getUserByUsername(username);
        return JsonRetFactory.getRet(user == null ? Collections.emptyMap() : user);
    }

    @PostMapping("/add.do")
    public JsonView add(String username, String password) {
        checkUsername(username);
        checkPassword(password);

        User user = new User(username, password);
        boolean op = userService.addUser(user);
        if (!op) {
            return JsonRetFactory.getRet(500, "用户：" + username + "添加失败！");
        }
        return JsonRetFactory.getRet(200, "用户：" + username + "添加成功！");
    }

    @GetMapping("/gets.do")
    public JsonView gets() {
        return JsonRetFactory.getRet(userService.gets());
    }

    @GetMapping("/getByCondition.do")
    public JsonView getByCondition(UserQuery query) {
        checkQueue(query);
        return JsonRetFactory.getRet(new JcPageUtils<>(query, userService.getsByConditionCount(query), userService.getsByCondition(query)));
    }

    @PutMapping("/update.do")
    public JsonView update(String username, UserQuery query) {
        checkUsername(username);
        if (query.getPassword() != null) {
            checkPassword(query.getPassword());
        }
        checkQueue(query);

        return JsonRetFactory.getRet(userService.updateUser(username, query));
    }

    @DeleteMapping("/delete/{username}.do")
    public JsonView delete(@PathVariable String username) {
        checkUsername(username);

        return JsonRetFactory.getRet(userService.deleteUser(username));
    }

    private void checkUsername(String username) {
        ExceptionEnum.INVALID_PARAMETER.assertNotNull(username, "username", username);
        // TODO 其它正则校验
    }

    private void checkPassword(String password) {
        ExceptionEnum.INVALID_PARAMETER1.assertNotNull(password, "password");
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(JcSecurityUtils.isBase64(password), "密码不符合规范");
        // TODO 或许还有其它校验
    }

    private void checkQueue(UserQuery query) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }
}
