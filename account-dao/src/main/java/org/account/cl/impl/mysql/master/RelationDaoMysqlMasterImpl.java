package org.account.cl.impl.mysql.master;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
@Mapper
public interface RelationDaoMysqlMasterImpl {

    String ROLE_PERMISSION = "role_permission";
    String USER_ROLE = "user_role";
    /*String ROLE = RoleDaoMysqlMasterImpl.ROLE_TAB;
    String USER = UserDaoMysqlMasterImpl.USER_TAB;
    String PERMISSION = PermissionDaoMysqlMasterImpl.PERMISSION_TAB;*/

    /**
     * 解绑用户和角色之间的关系
     * @param username
     * @return
     */
    @Delete("delete from " + USER_ROLE + " where username = #{username}")
    boolean unBindUserRelation1(String username);

    /**
     * 解绑用户和角色之间的关系
     * @param username
     * @param roleId
     * @return
     */
    @Delete("delete from " + USER_ROLE + " where username = #{username} and role_id = #{roleId}")
    boolean unBindUserRelation2(@Param("username") String username, @Param("roleId") int roleId);

    /**
     * 添加用户和角色之间的关系
     * @param username
     * @param roleId
     * @return
     */
    @Insert("insert into " + USER_ROLE + " values (#{username}, #{roleId}, now(), now())")
    boolean bindUserRelation(@Param("username") String username, @Param("roleId") int roleId);

    /**
     * 解绑角色和权限之间的关系
     * @param roleId
     * @return
     */
    @Delete("delete from " + ROLE_PERMISSION + " where role_id = #{roleId}")
    boolean unBindRoleRelation1(int roleId);

    /**
     * 解绑角色和权限之间的关系
     * @param roleId
     * @param permissionId
     * @return
     */
    @Delete("delete from " + ROLE_PERMISSION + " where role_id = #{roleId} and permissionId = #{permissionId}")
    boolean unBindRoleRelation2(@Param("roleId") int roleId, @Param("permissionId") int permissionId);

    /**
     * 添加角色和权限之间的关系
     * @param roleId
     * @param permissionId
     * @return
     */
    @Insert("insert into " + ROLE_PERMISSION + " values (#{roleId}, #{permissionId}, now(), now())")
    boolean bindRoleRelation(@Param("roleId") int roleId, @Param("permissionId") int permissionId);

    /**
     * 删除role -> user_role
     * @param roleId
     * @return
     */
    @Delete("delete from " + USER_ROLE + " where role_id = #{roleId}")
    boolean delRoleRelation(int roleId);

    /**
     * 删除permission -> role_permission
     * @param permissionId
     * @return
     */
    @Delete("delete from " + ROLE_PERMISSION + " where permission_id = #{permissionId}")
    boolean delPermissionRelation(int permissionId);
}
