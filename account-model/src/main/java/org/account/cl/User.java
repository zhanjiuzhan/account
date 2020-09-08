package org.account.cl;

import org.account.cl.permissions.JwtUser;

/**
 * 用户信息
 * @author Administrator
 */
public class User extends JwtUser {

    @Override
    public String toString() {
        return "User{} " + super.toString();
    }
}
