package org.account.cl;

/**
 * 用户信息
 * @author Administrator
 */
public class User  {
    /**
     * 用户标志
     */
    private String username;

    /**
     * 用户凭证
     */
    private String password;

    /**
     * 用户是否可用
     */
    private int isEnable;

    /**
     * 用户是否被锁住
     */
    private int locked;

    /**
     * 用户是否过期
     */
    private int expired;

    /**
     * 用户凭证是否过期
     */
    private int credentialsExpired;

    /**
     * 用户记录创建时间
     */
    private String createDate;

    /**
     * 用户记录的修改时间
     */
    private String updateDate;

    {
        // 初始值
        this.username = "";
        this.password = "";
        this.isEnable = 1;
        this.locked = 0;
        this.expired = 0;
        this.credentialsExpired = 0;
        this.createDate = JcDateUtils.getToDay();
        this.updateDate = JcDateUtils.getToDay();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public int getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(int credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnable=" + isEnable +
                ", locked=" + locked +
                ", expired=" + expired +
                ", credentialsExpired=" + credentialsExpired +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
