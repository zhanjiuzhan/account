package org.account.cl.impl.mysql.master;

import org.account.cl.Role;
import org.account.cl.condition.RoleQuery;
import org.account.cl.impl.mysql.RoleDaoMysqlImplProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author Administrator
 */
@Mapper
public interface RoleDaoMysqlMasterImpl {

    String ROLE_TAB = "role";

    /**
     * 添加一个角色信息
     * @param role
     * @return
     */
    @Insert("insert into " + ROLE_TAB + "(pid, name, description, status, update_date, create_date) values " +
            "(#{pid}, #{name}, #{description}, #{status}, now(), now())")
    boolean add(Role role);

    /**
     * 删除一个角色信息 角色信息删除 麾下标记位 state -> 2
     * @param id
     * @return
     */
    @Delete("delete from " + ROLE_TAB + " where sid = #{id}")
    boolean delete(int id);

    /**
     * 修改角色信息
     * @param id
     * @param query
     * @return
     */
    @UpdateProvider(type = RoleDaoMysqlImplProvider.class, method = "updateRole")
    boolean updateRole(int id, RoleQuery query);
}
