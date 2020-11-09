package org.account.cl.controller;

import org.account.cl.*;
import org.account.cl.condition.UserQuery;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 用户控制器 提供用户相关的操作
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private LogUtils logUtils = new LogUtils(logger);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RelationService relationService;

    /**
     * 取得一个用户的信息 目前来说就是取得用户的状态的 以后可以取得详细
     * TODO 应该为取得用户的详细信息
     * @param username
     * @return
     */
    @GetMapping("/get/{username}.do")
    public JsonView getUser(@PathVariable String username) {
        long start = logUtils.start("查询一个用户的详细信息, username: " + username);
        checkUsername(username);

        // 只能取到用户的状态信息
        User user = userService.getUserByUsername(username);
        // 取得用户的详细信息

        logUtils.end("查询一个用户的详细信息完成", start);
        return JsonRetFactory.getRet(user == null ? Collections.emptyMap() : user);
    }

    /**
     * 取得所有的用户信息 管理员使用
     * @return
     */
    @GetMapping("/gets.do")
    public JsonView gets() {
        long start = logUtils.start("查询所有的用户信息");
        List<User> users = userService.gets();
        logUtils.end("查询所有的用户信息完成", start);
        return JsonRetFactory.getRet(users);
    }

    /**
     * 根据条件来查询用户的信息 可以分页也可以不分页 取决于参数的设置
     * @param query
     * @return
     */
    @GetMapping("/getByCondition.do")
    public JsonView getByCondition(UserQuery query) {
        long start = logUtils.start("依据条件查询用户信息, query:" + query.toString());
        JcPageUtils<User> pages = new JcPageUtils<>(query, userService.getsByConditionCount(query), userService.getsByCondition(query));
        logUtils.end("依据条件查询用户信息完成", start);
        return JsonRetFactory.getRet();
    }

    /**
     * 根据用户名 取得所具有的角色信息
     * @param username
     * @return
     */
    @GetMapping("/getRole/{username}.do")
    public JsonView getRoles(@PathVariable String username) {
        long start = logUtils.start("依据用户名查询用户所具有的角色信息, username: " + username);
        checkUsername(username);
        List<Role> roles = relationService.getRoleByUser(username);
        logUtils.end("依据用户名查询用户所具有的角色信息完成", start);
        return JsonRetFactory.getRet(roles);
    }

    /**
     * 根据用户名 取得所具有的权限信息
     * @param username
     * @return
     */
    @GetMapping("/getPermission/{username}.do")
    public JsonView getPermissions(@PathVariable String username) {
        long start = logUtils.start("依据用户名查询用户所具有的权限信息, username: " + username);
        checkUsername(username);
        List<Permission> roles = relationService.getPermissionByUser(username);
        logUtils.end("依据用户名查询用户所具有的权限信息", start);
        return JsonRetFactory.getRet(roles);
    }

    /**
     * 添加一个用户信息 通常用于用户注册 管理员添加用户
     * @param username 必须 < 32
     * @param password 必须 RSA 公钥加密 Base64转码
     * @return msg 或者 true 或则 false
     */
    @PostMapping("/add.do")
    public JsonView add(String username, String password) {
        long start = logUtils.start("添加一个用户信息, username: " + username + " password: " + password);
        checkUsername(username);
        checkPassword(password);
        // 用户名必须没有被使用
        User user = userService.getUserByUsername(username);
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(user == null, "用户名已经被使用");
        user = new User(username, password);
        boolean flag = userService.addUser(user);
        logUtils.end("添加一个用户信息完成, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
    }

    /**
     * 根据用户名 进行用户信息的修该 通常用于管理员修改用户信息 用户修改密码 已经其它渠道对用户账号的信息修改
     * @param username 用户名 必须
     * @param query 用户修改后的参数 TODO 查询的时候算是拼接字符串 需要特别注意 当字符串的sql 注入问题
     * @return msg 或者 true 或则 false
     */
    @PutMapping("/update.do")
    public JsonView update(String username, UserQuery query) {
        long start = logUtils.start("修改一个用户信息, username: " + username + " query: " + query.toString());
        checkUsername(username);
        // 检查该用户是否存在
        User user = userService.getUserByUsername(username);
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(user != null, "用户不存在");

        // 可能修改密码
        // 如果有password就要校验其是否base64 修改密码 两次重新输入前端自行对比 并且要校验旧密码是否正确
        if (query.getPassword() != null && query.getOldPassword() != null) {
            checkPassword(query.getOldPassword());
            ExceptionEnum.INVALID_PARAMETER2.assertTrue(
                userService.isOneUser(username, query.getOldPassword()), "旧密码有误");
            checkPassword(query.getPassword());
        }
        boolean flag = userService.updateUser(username, query);
        logUtils.end("修改一个用户信息, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
    }

    /**
     * 删除一个用户信息 通常是管理员来删除 当然也有系统任务定时 删除很久不上线的用户
     * @param username 必须
     * @return msg 或者 true 或则 false
     */
    @DeleteMapping("/delete/{username}.do")
    public JsonView delete(@PathVariable String username) {
        long start = logUtils.start("删除一个用户信息, username: " + username);
        checkUsername(username);
        // 删除时要确保用户没有其它的角色关系绑定 否则删除失败
        ExceptionEnum.INVALID_PARAMETER2.assertFalse(relationService.isExistUserInRelation(username), "请解绑用户和角色之间的关系");
        // 第一次删除 修改其为不可用 第二次才会真正删除
        boolean flag = userService.deleteUser(username);
        logUtils.end("删除一个用户信息, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
    }

    /**
     * 给用户分配角色 通常注册的用户应该分配为 普通用户
     * @param username 必须 < 32
     * @param sid
     * @return msg 或者 true 或则 false
     */
    @PostMapping("/add/role.do")
    public JsonView addRole(String username, int sid) {
        long start = logUtils.start("给用户分配一个角色信息, username: " + username + " sid: " + sid);
        checkUsername(username);
        // 检查该用户是否存在
        User user = userService.getUserByUsername(username);
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(user != null, "用户不存在");

        // 检查role 是否存在
        Role role = roleService.get(sid);
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(role != null, "角色不存在");

        // 绑定用户和角色的信息
        boolean flag = relationService.bindUserRelation(username, sid);
        logUtils.end("给用户分配一个角色信息, flag: " + flag, start);
        return JsonRetFactory.getRet(flag);
    }

    /**
     * 校验用户名 必须 < 32
     * @param username
     */
    private void checkUsername(String username) {
        ExceptionEnum.INVALID_PARAMETER.assertNotNull(username, "username", username);
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(username.length() < 32, "用户名必须小于32位");
    }

    /**
     * 校验密码 必须 RSA 公钥加密 Base64转码
     * @param password
     */
    private void checkPassword(String password) {
        ExceptionEnum.INVALID_PARAMETER1.assertNotNull(password, "password");
        ExceptionEnum.INVALID_PARAMETER2.assertTrue(JcSecurityUtils.isBase64(password), "密码不符合规范");
    }
}
