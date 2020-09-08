package org.account.cl.controller;

import org.account.cl.User;
import org.account.cl.view.JcJsonView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 取得所以的用户信息
     * @return
     */
    @GetMapping("/gets")
    public JcJsonView getUsers() {
        User user = new User();
        user.setUsername("dw_chenglei");
        return new JcJsonView(user);
    }
}
