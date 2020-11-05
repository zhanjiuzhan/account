package org.account.cl.condition;

/**
 * @author Administrator
 */
public class RoleQuery extends BaseQuery<RoleQuery> {

    private Integer pid;

    private String name;

    private String description;

    private Integer status;

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

    @Override
    public String toString() {
        return "RoleQuery{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}
