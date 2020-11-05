package org.account.cl;

import org.account.cl.condition.RoleQuery;

import java.util.List;

/**
 * @author Administrator
 */
public interface RoleDao {

    /**
     * 添加一个角色信息
     * @param role
     * @return
     */
    boolean add(Role role);

    /**
     * 删除一个角色信息 角色信息删除 麾下标记位 state -> 2
     * @param id
     * @return
     */
    boolean delete(int id);

    /**
     * 禁用角色信息
     * 自己state -> 1 麾下标记位 state -> 2
     * @param id
     * @param op true 禁用  false启用
     * @return
     */
    boolean isDisable(int id, boolean op);

    /**
     * 修改角色信息
     * @param id
     * @param query
     * @return
     */
    boolean updateRole(int id, RoleQuery query);

    /**
     * 根据Id 查询角色信息
     * @param id
     * @return
     */
    Role get(int id);

    /**
     * 查询所有的角色信息
     * @return
     */
    List<Role> gets();

    /**
     * 根据条件查询所有的角色信息
     * @param query
     * @return
     */
    List<Role> getsByCondition(RoleQuery query);
}
