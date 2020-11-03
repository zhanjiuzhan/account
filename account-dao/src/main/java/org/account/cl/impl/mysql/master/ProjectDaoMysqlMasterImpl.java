package org.account.cl.impl.mysql.master;

import org.account.cl.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author Administrator
 */
@Mapper
public interface ProjectDaoMysqlMasterImpl {

    String PROJECT_TAB = "project";

    /**
     * 添加一个项目描述
     * @param project
     * @return
     */
    @Insert("insert into " + PROJECT_TAB + "(name, url, description, update_date, create_date) values " +
            "(#{name}, #{url}, #{description}, now(), now())")
    boolean add(Project project);

    /**
     * 删除项目
     * @param name
     * @return
     */
    @Delete("delete from " + PROJECT_TAB + " where name = #{name}")
    boolean del(String name);

    /**
     * 修改项目信息
     * @param project
     * @return
     */
    @Update("update " + PROJECT_TAB + " set url = #{url}, description = #{description}, update_date=now() where name =#{name}")
    boolean update(Project project);
}
