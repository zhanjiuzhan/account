package org.account.cl.condition;

import org.account.cl.JcPageUtils;

/**
 * 条件查询用户信息
 * @author Administrator
 */
public class UserQuery implements JcPageUtils.Page {

    private String password;

    private Boolean enable;

    private Boolean expired;

    private Boolean locked;

    private Boolean credentialsExpired;

    private String createDate;

    private String updateDate;

    private Integer pagePoint;

    private Integer pageSize;

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

    public String getCreateDate() {
        return createDate;
    }

    public UserQuery setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public UserQuery setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Integer getPagePoint() {
        return pagePoint;
    }

    public UserQuery setPagePoint(Integer pagePoint) {
        this.pagePoint = pagePoint;
        return this;
    }

    @Override
    public int getCurrentPage() {
        return getPagePoint() == null ? 0 : getPagePoint();
    }

    @Override
    public int getCurrentPageSize() {
        return getPageSize() == null ? 0 : getPageSize();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public UserQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "password='" + password + '\'' +
                ", enable=" + enable +
                ", expired=" + expired +
                ", locked=" + locked +
                ", credentialsExpired=" + credentialsExpired +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", pagePoint=" + pagePoint +
                ", pageSize=" + pageSize +
                '}';
    }
}
