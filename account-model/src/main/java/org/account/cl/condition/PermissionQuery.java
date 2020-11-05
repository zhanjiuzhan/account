package org.account.cl.condition;

import org.account.cl.JcPageUtils;

/**
 * @author Administrator
 */
public class PermissionQuery extends BaseQuery<PermissionQuery> implements JcPageUtils.Page {

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

    public PermissionQuery setStatus(Integer status) {
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

    @Override
    public int getCurrentPage() {
        return getPagePoint() == null ? 0 : getPagePoint();
    }

    @Override
    public int getCurrentPageSize() {
        return getPageSize() == null ? 0 : getPageSize();
    }

    @Override
    public String toString() {
        return "PermissionQuery{" +
                "project='" + project + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", method='" + method + '\'' +
                ", pagePoint=" + pagePoint +
                ", pageSize=" + pageSize +
                "} " + super.toString();
    }
}
