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
