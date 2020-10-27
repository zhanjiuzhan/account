package org.account.cl.condition;

/**
 * 条件查询用户信息
 * @author Administrator
 */
public class UserQuery {

    private String username;

    private Boolean isEnable;

    private Boolean expired;

    private Boolean locked;

    private Boolean credentialsExpired;

    private String createDate;

    private String updateDate;

    private Integer pagePoint;

    private Integer pageSize;

    public String getUsername() {
        return username;
    }

    public UserQuery setUsername(String username) {
        this.username = username;
        return this;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public UserQuery setEnable(Boolean enable) {
        isEnable = enable;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public UserQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
