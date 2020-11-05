package org.account.cl.condition;

/**
 * @author Administrator
 */
public class BaseQuery<T> {

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

    @Override
    public String toString() {
        return "BaseQuery{" +
                "createDateStart='" + createDateStart + '\'' +
                ", createDateEnd='" + createDateEnd + '\'' +
                ", updateDateStart='" + updateDateStart + '\'' +
                ", updateDateEnd='" + updateDateEnd + '\'' +
                '}';
    }
}
