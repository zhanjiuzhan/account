package org.account.cl.controller;

import org.account.cl.User;
import org.account.cl.impl.mysql.UserDaoMysqlImpl;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserDaoMysqlImpl userDaoMysqlImpl;


    @GetMapping("/get.do")
    public JsonView getUser(String username) {
        return JsonRetFactory.getRet(userDaoMysqlImpl.getUserByUsername(username));
    }
}
