package org.account.cl;

/**
 * @author Administrator
 */
public class UserRole {

    private String username;

    private int roleId;

    private String createDate;

    private String updateDate;

    {
        this.createDate = JcDateUtils.getToDay();
        this.updateDate = JcDateUtils.getToDay();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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
        return "UserRole{" +
                "username='" + username + '\'' +
                ", roleId=" + roleId +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
