package org.account.cl.impl;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.cache.PermissionDaoCacheImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDaoCacheImpl permissionDaoCache;

    @Override
    public Permission get(int id) {
        return permissionDaoCache.get(id);
    }

    @Override
    public int getsByConditionCount(PermissionQuery query) {
        return permissionDaoCache.getsByConditionCount(query);
    }

    @Override
    public List<Permission> getsByCondition(PermissionQuery query) {
        return permissionDaoCache.getsByCondition(query);
    }

    @Override
    public boolean add(Permission permission) {
        return permissionDaoCache.add(permission);
    }

    @Override
    public boolean batchAdd(List<Permission> permissions) {
        return permissionDaoCache.batchAdd(permissions);
    }

    @Override
    public boolean updatePermission(int id, PermissionQuery query) {
        return permissionDaoCache.updatePermission(id, query);
    }

    @Override
    public boolean updatePermission2(int id, String name, int status) {
        return permissionDaoCache.updatePermission2(id, name, status);
    }

    @Override
    public boolean delPermission(int id) {
        return permissionDaoCache.delPermission(id);
    }
}
