package org.account.cl.condition;

import org.account.cl.JcPageUtils;

/**
 * @author Administrator
 */
public class BaseQuery<T> implements JcPageUtils.Page {

    private Integer pagePoint;

    private Integer pageSize;

    private String createDateStart;

    private String createDateEnd;

    private String updateDateStart;

    private String updateDateEnd;


    public String getCreateDateStart() {
        return createDateStart;
    }

    public T setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
        return (T) this;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public T setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
        return (T) this;
    }

    public String getUpdateDateStart() {
        return updateDateStart;
    }

    public T setUpdateDateStart(String updateDateStart) {
        this.updateDateStart = updateDateStart;
        return (T) this;
    }

    public String getUpdateDateEnd() {
        return updateDateEnd;
    }

    public T setUpdateDateEnd(String updateDateEnd) {
        this.updateDateEnd = updateDateEnd;
        return (T) this;
    }

    public Integer getPagePoint() {
        return pagePoint;
    }

    public T setPagePoint(Integer pagePoint) {
        this.pagePoint = pagePoint;
        return (T) this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public T setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return (T) this;
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
        return "BaseQuery{" +
                "pagePoint=" + pagePoint +
                ", pageSize=" + pageSize +
                ", createDateStart='" + createDateStart + '\'' +
                ", createDateEnd='" + createDateEnd + '\'' +
                ", updateDateStart='" + updateDateStart + '\'' +
                ", updateDateEnd='" + updateDateEnd + '\'' +
                '}';
    }
}
