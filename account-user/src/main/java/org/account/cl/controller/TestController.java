package org.account.cl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author Administrator
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello, Welcome to the account management system!";
    }
}
