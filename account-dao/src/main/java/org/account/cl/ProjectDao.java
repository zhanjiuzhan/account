package org.account.cl;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProjectDao {

    /**
     * 添加一个项目描述
     * @param project
     * @return
     */
    boolean add(Project project);

    /**
     * 删除项目
     * @param name
     * @return
     */
    boolean del(String name);

    /**
     * 修改项目信息
     * @param project
     * @return
     */
    boolean update(Project project);

    /**
     * 根据名称取得信息
     * @param name
     * @return
     */
    Project get(String name);

    /**
     * 取得所有的项目信息
     * @return
     */
    List<Project> gets();
}
