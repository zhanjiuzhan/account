package org.account.cl;

/**
 * 角色信息
 * @author Administrator
 */
public class Role {

    /**
     * role 的主键 添加到数据库时 自动生成
     */
    private int sid;

    /**
     * 指向父级 的id 若没有的话 为0
     */
    private int pid;

    /**
     * 角色的名称 通常是代号
     */
    private String name;

    /**
     * 角色的描述 通常中文描述
     */
    private String description;

    /**
     * 角色是否可正常使用 2 父被删除或禁用 1 可以  0 已禁用
     */
    private int status;

    /**
     * 角色的创建时间
     */
    private String createDate;

    /**
     * 角色的修改时间
     */
    private String updateDate;

    {
        // 初始化 避免null
        this.sid = -1;
        this.pid = 0;
        this.name = "";
        this.description = "";
        this.status = 1;
        this.createDate = JcDateUtils.getToDay();
        this.updateDate = JcDateUtils.getToDay();
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
    public int hashCode() {
        if (this.sid < 0 || this.name == null) {
            return super.hashCode();
        } else {
            return (this.sid + this.pid + this.name).hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.sid < 0 || this.name == null) {
            return super.equals(obj);
        } else {
            Role tmp = (Role)obj;
            return this.sid == tmp.sid && this.name.equals(tmp.getName()) && this.pid == tmp.pid;
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "sid=" + sid +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
