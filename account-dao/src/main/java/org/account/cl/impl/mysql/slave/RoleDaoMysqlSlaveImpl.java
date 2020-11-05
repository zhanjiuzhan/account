package org.account.cl.impl.mysql.slave;

import org.account.cl.Role;
import org.account.cl.condition.RoleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface RoleDaoMysqlSlaveImpl {

    String ROLE_TAB = "role";

    /**
     * 根据Id 查询角色信息
     * @param id
     * @return
     */
    @Select("select * from " + ROLE_TAB + " where sid = #{id} limit 1")
    Role get(int id);

    /**
     * 查询所有的角色信息
     * @return
     */
    @Select("select * from " + ROLE_TAB + " order by update_date desc")
    List<Role> gets();

    /**
     * 根据条件查询所有的角色信息
     * @param query
     * @return
     */
    @Select("<script>" +
            "select * from " + ROLE_TAB +
            " where sid is not null " +
            "<when test='pid != null'>" +
            " and find_in_set(sid, getSubRole(#{pid}))" +
            "</when>" +
            "<when test='name != null'>" +
            " and name=#{name}" +
            "</when>" +
            "<when test='description != null'>" +
            " and description=#{description}" +
            "</when>" +
            "<when test='status != null'>" +
            " and status=#{status}" +
            "</when>" +
            "<when test='createDateStart != null and createDateEnd != null'>" +
            " and date_format(create_date, '%Y-%m-%d') &gt;= #{createDateStart} and date_format(create_date, '%Y-%m-%d') &lt;= #{createDateEnd} " +
            "</when>" +
            "<when test='updateDateStart != null and updateDateEnd != null'>" +
            " and date_format(update_date, '%Y-%m-%d') &gt;= #{updateDateStart} and update_date(create_date, '%Y-%m-%d') &lt;= #{updateDateEnd} " +
            "</when>" +
            "order by update_date desc" +
            "</script>")
    List<Role> getsByCondition(RoleQuery query);
}
