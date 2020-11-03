package org.account.cl.condition;

/**
 * @author Administrator
 */
public class PermissionQuery {

    /**
     * 项目名
     */
    private String project;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求路径的 状态
     * 0 新增  1 已确认  2 已经没有了
     */
    private Integer status;

    /**
     * 请求的方式 GET POST等
     */
    private String method;

    private String updateDate;

    private String createDate;

    private Integer pagePoint;

    private Integer pageSize;

    public String getProject() {
        return project;
    }

    public PermissionQuery setProject(String project) {
        this.project = project;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PermissionQuery setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public PermissionQuery setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public PermissionQuery setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public PermissionQuery setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public PermissionQuery setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public Integer getPagePoint() {
        return pagePoint;
    }

    public PermissionQuery setPagePoint(Integer pagePoint) {
        this.pagePoint = pagePoint;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PermissionQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
