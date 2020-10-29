package org.account.cl.controller;

import org.account.cl.JcSecurityUtils;
import org.account.cl.JcStringUtils;
import org.account.cl.User;
import org.account.cl.permissions.UserService;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        String errorMsg = checkUsername(username);
        if (errorMsg != null) {
            return JsonRetFactory.getRet(400, errorMsg);
        }
        User user = userService.getUserByUsername(username);
        return JsonRetFactory.getRet(user == null ? Collections.emptyMap() : user);
    }

    @PostMapping("/add.do")
    public JsonView add(String username, String password) {
        String errorMsg = checkUsername(username);
        if (errorMsg != null) {
            return JsonRetFactory.getRet(400, errorMsg);
        }

        errorMsg = checkPassword(password);
        if (errorMsg != null) {
            return JsonRetFactory.getRet(400, errorMsg);
        }
        User user = new User(username, password);
        boolean op = userService.addUser(user);
        if (!op) {
            return JsonRetFactory.getRet(500, "用户：" + username + "添加失败！");
        }
        return JsonRetFactory.getRet(200, "用户：" + username + "添加成功！");
    }

    private String checkUsername(String username) {
        // TODO 用户名需要有约束 最大长度 是关键字等
        if (JcStringUtils.isBlank(username)) {
            return "用户名不合法！";
        }
        return null;
    }

    private String checkPassword(String password) {
        // TODO 或许还有其它校验
        if (JcStringUtils.isBlank(password) || !JcSecurityUtils.isBase64(password)) {
            return "密码不合法！";
        }
        return null;
    }
}
