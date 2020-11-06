package org.account.cl;

/**
 * 项目信息
 * @author Administrator
 */
public class Project {
    /**
     * 项目名
     */
    private String name;

    /**
     * 项目请求路径
     */
    private String url;

    /**
     * 项目描述地址
     */
    private String description;

    private String updateDate;

    private String createDate;

    {
        // 初始化 避免null
        this.url = "";
        this.name = "";
        this.description = "";
        this.createDate = JcDateUtils.getToDay();
        this.updateDate = JcDateUtils.getToDay();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        if (this.name == null || this.url == null) {
            return super.hashCode();
        } else {
            return (this.url + this.name).hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.name == null || this.url == null) {
            return super.equals(obj);
        } else {
            Project tmp = (Project)obj;
            return this.url.equals(tmp.url) && this.name.equals(tmp.getName());
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
