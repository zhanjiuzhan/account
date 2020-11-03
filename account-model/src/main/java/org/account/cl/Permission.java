package org.account.cl;

/**
 * URL 需要的权限
 * @author Administrator
 */
public class Permission {

    /**
     * 权限的id
     */
    private int id;

    /**
     * 项目名
     */
    private String project;

    /**
     * 请求路径
     */
    private String url;


    /**
     * 权限的描述
     */
    private String name;

    /**
     * 请求路径的 状态
     * 0 新增  1 已确认  2 已经没有了
     */
    private int status;

    /**
     * 请求的方式 GET POST等
     */
    private String method;

    private String updateDate;

    private String createDate;

    {
        // 初始化 避免null
        this.url = "";
        this.name = "";
        this.method = "";
        this.createDate = JcDateUtils.getToDay();
        this.updateDate = JcDateUtils.getToDay();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public int hashCode() {
        if (this.url == null || this.project == null || this.method == null) {
            return super.hashCode();
        } else {
            return (this.url + this.method +this.project).hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.url == null || this.project == null || this.method == null) {
            return super.equals(obj);
        } else {
            Permission tmp = (Permission)obj;
            return this.url.equals(tmp.url) && this.method.equals(tmp.getMethod()) && this.project.equals(tmp.getProject());
        }
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", method='" + method + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
