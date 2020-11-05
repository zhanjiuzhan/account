package org.account.cl.impl.mysql.slave;

import org.account.cl.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface ProjectDaoMysqlSlaveImpl {

    String PROJECT_TAB = "project";

    /**
     * 根据名称取得信息
     * @param name
     * @return
     */
    @Select("select * from " + PROJECT_TAB + " where name = #{name}  limit 1")
    Project get(String name);

    /**
     * 取得所有的项目信息
     * @return
     */
    @Select("select * from " + PROJECT_TAB + " order by update_date desc")
    List<Project> gets();
}
