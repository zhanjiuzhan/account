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
}
