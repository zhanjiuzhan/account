package org.account.cl.impl.cache;

import org.account.cl.Permission;
import org.account.cl.PermissionDao;
import org.account.cl.condition.PermissionQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public class PermissionDaoCacheImpl implements PermissionDao {


    @Override
    public Permission get(int id) {
        return null;
    }

    @Override
    public int getsByConditionCount(PermissionQuery query) {
        return 0;
    }

    @Override
    public List<Permission> getsByCondition(PermissionQuery query) {
        return null;
    }

    @Override
    public boolean add(Permission permission) {
        return false;
    }

    @Override
    public boolean batchAdd(List<Permission> permissions) {
        return false;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return false;
    }

    @Override
    public boolean updatePermission2(int id, String name, int status) {
        return false;
    }

    @Override
    public boolean delPermission(int id) {
        return false;
    }
}
