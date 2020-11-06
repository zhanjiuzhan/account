package org.account.cl;

import java.util.List;

/**
 * 关系服务接口  用户-角色-权限
 * @author Administrator
 */
public interface RelationService {

    /**
     * 根据permission Id 查询是否在role_permission 中存在
     * @param permissionId
     * @return
     */
    boolean isExistPermissionInRelation(int permissionId);

    /**
     * 根据role Id 查询是否在 user_role 和 role_permission 中存在
     * @param roleId
     * @return
     */
    boolean isExistRoleInRelation(int roleId);

    /**
     * 根据用户名 查询是否存在于 user_role
     * @param username
     * @return
     */
    boolean isExistUserInRelation(String username);

    /**
     * 解绑用户和角色之间的关系
     * @param username
     * @return
     */
    boolean unBindUserRelation(String username);

    /**
     * 解绑用户和角色之间的关系
     * @param username
     * @param roleId
     * @return
     */
    boolean unBindUserRelation(String username, int roleId);

    /**
     * 添加用户和角色之间的关系
     * @param username
     * @param roleId
     * @return
     */
    boolean bindUserRelation(String username, int roleId);

    /**
     * 解绑角色和权限之间的关系
     * @param roleId
     * @return
     */
    boolean unBindRoleRelation(int roleId);

    /**
     * 解绑角色和权限之间的关系
     * @param roleId
     * @param permissionId
     * @return
     */
    boolean unBindRoleRelation(int roleId, int permissionId);

    /**
     * 添加角色和权限之间的关系
     * @param roleId
     * @param permissionId
     * @return
     */
    boolean bindRoleRelation(int roleId, int permissionId);

    /**
     * 删除role -> user_role
     * @param roleId
     * @return
     */
    boolean delRoleRelation(int roleId);

    /**
     * 删除permission -> role_permission
     * @param permissionId
     * @return
     */
    boolean delPermissionRelation(int permissionId);

    /**
     * 根据用户名查询所有的角色信息
     * @param username
     * @return
     */
    List<Role> getRoleByUser(String username);

    /**
     * 根据用户名查询所具有的权限信息
     * @param username
     * @return
     */
    List<Permission> getPermissionByUser(String username);

    /**
     * 根据角色查询所据有的权限信息
     * @param roleId
     * @return
     */
    List<Permission> getPermissionByRole(int roleId);

    /**
     * 查询所有的角色权限表
     * @return
     */
    List<RolePermission> gets();

    /**
     * 根据条件查询所有的角色权限信息
     * @return
     */
    List<RolePermission> getsByCondition();
}
