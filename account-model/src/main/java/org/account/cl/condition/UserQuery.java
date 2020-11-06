package org.account.cl.condition;

import org.account.cl.JcPageUtils;

/**
 * 条件查询用户信息
 * @author Administrator
 */
public class UserQuery extends BaseQuery<UserQuery> implements JcPageUtils.Page {

    private String oldPassword;

    private String password;

    private Boolean enable;

    private Boolean expired;

    private Boolean locked;

    private Boolean credentialsExpired;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public UserQuery setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public Boolean getExpired() {
        return expired;
    }

    public UserQuery setExpired(Boolean expired) {
        this.expired = expired;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public UserQuery setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public UserQuery setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
        return this;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserQuery setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpired=" + credentialsExpired +
                "} " + super.toString();
    }
}
