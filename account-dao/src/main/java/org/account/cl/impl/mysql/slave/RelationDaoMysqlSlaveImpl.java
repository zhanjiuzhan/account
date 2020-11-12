package org.account.cl.impl.mysql.slave;

import org.account.cl.Permission;
import org.account.cl.Role;
import org.account.cl.RolePermission;
import org.account.cl.impl.mysql.master.PermissionDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.master.RoleDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.master.UserDaoMysqlMasterImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface RelationDaoMysqlSlaveImpl {

    String ROLE_PERMISSION = "role_permission";
    String USER_ROLE = "user_role";
    String ROLE = RoleDaoMysqlMasterImpl.ROLE_TAB;
    String USER = UserDaoMysqlMasterImpl.USER_TAB;
    String PERMISSION = PermissionDaoMysqlMasterImpl.PERMISSION_TAB;

    /**
     * 根据permission Id 查询是否在role_permission 中存在
     * @param permissionId
     * @return
     */
    @Select("select count(role_id) from " + ROLE_PERMISSION + " where permission_id = #{permissionId} limit 1")
    boolean isExistPermissionInRelation(int permissionId);

    /**
     * 根据role Id 查询是否在 user_role 和 role_permission 中存在
     * @param roleId
     * @return
     */
    @Select("select count(username) from " + USER_ROLE + " where role_id = #{roleId} limit 1")
    boolean isExistRoleInRelation1(int roleId);

    /**
     * 根据用户名 查询是否存在于 user_role
     * @param username
     * @return
     */
    @Select("select count(username) from " + USER_ROLE + " where username = #{username} limit 1")
    boolean isExistUserInRelation2(String username);

    /**
     * 根据用户名查询所有的角色信息 只取得对应的
     * @param username
     * @return
     */
    @Select("select r.* from " + USER_ROLE + " as ur left join " + ROLE + " as r on ur.role_id = r.sid where ur.username = #{username} order by r.update_date desc")
    List<Role> getRoleByUser(String username);

    /**
     * 根据用户名查询所具有的权限信息
     * @param username
     * @return
     */
    @Select("select DISTINCT p.* from " + ROLE_PERMISSION + " rp LEFT JOIN " + PERMISSION + " p on rp.permission_id = p.id where FIND_IN_SET(rp.role_id, (select GROUP_CONCAT(role_id) from " + USER_ROLE + " where username=#{username}')) order by p.update_date desc")
    List<Permission> getPermissionByUser(String username);

    /**
     * 根据用户名查询所具有的权限信息
     * @param projectName 项目名
     * @param username 用户名
     * @return
     */
    @Select("select DISTINCT p.* from " + ROLE_PERMISSION + " rp LEFT JOIN " + PERMISSION + " p on rp.permission_id = p.id where FIND_IN_SET(rp.role_id, (select GROUP_CONCAT(role_id) from " + USER_ROLE + " where username=#{username})) and p.project = #{projectName} order by p.update_date desc")
    List<Permission> getPermissionByUserAndPro(@Param("projectName") String projectName, @Param("username") String username);

    /**
     * 根据角色查询所据有的权限信息
     * @param roleId
     * @return
     */
    @Select("select distinct p.* from " + ROLE_PERMISSION + " as rp LEFT JOIN " + PERMISSION + " as p on rp.permission_id = p.id where rp.role_id in (select sid from role where FIND_IN_SET(sid,getSubRole(#{roleId}))) order by p.update_date desc")
    List<Permission> getPermissionByRole(int roleId);

    /**
     * 查询所有的角色权限表
     * @return
     */
    @Select("select * from " + ROLE_PERMISSION + " order by update_date desc")
    List<RolePermission> gets();

    /**
     * 根据角色查询所据有的子角色信息 + 它自己
     * @param roleId
     * @return
     */
    @Select("select * from role where FIND_IN_SET(sid,getSubRole(#{roleId}))")
    List<Role> getRoleByPRole(int roleId);
}
