package org.account.cl;

import org.account.cl.condition.PermissionQuery;

import java.util.List;

/**
 * @author Administrator
 */
public interface PermissionDao {

    /**
     * 根据Id 取得一个Permission信息
     * @param id
     * @return
     */
    Permission get(int id);

    /**
     * 根据条件取得查询信息 分页用
     * @param query
     * @return
     */
    int getsByConditionCount(PermissionQuery query);

    /**
     * 根据条件取得查询信息
     * @param query
     * @return
     */
    List<Permission> getsByCondition(PermissionQuery query);

    /**
     * 添加一个权限信息
     * @param permission
     * @return
     */
    boolean add(Permission permission);

    /**
     * 批量添加权限信息
     * @param permissions
     * @return
     */
    boolean batchAdd(List<Permission> permissions);

    /**
     * 修改权限信息
     * @param id
     * @param query
     * @return
     */
    boolean updatePermission(int id, PermissionQuery query);

    /**
     * 修改权限的相关信息
     * @param id
     * @param name
     * @param status
     * @return
     */
    boolean updatePermission2(int id, String name, int status);

    /**
     * 根据Id 删除一个信息
     * @param id
     * @return
     */
    boolean delPermission(int id);
}
