package org.account.cl.condition;

/**
 * @author Administrator
 */
public class RoleQuery {

    private Integer pid;

    private String name;

    private String description;

    private Integer status;

    private String createDate;

    private String updateDate;

    public Integer getPid() {
        return pid;
    }

    public RoleQuery setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RoleQuery setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public RoleQuery setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public RoleQuery setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public RoleQuery setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    @Override
    public String toString() {
        return "RoleQuery{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
