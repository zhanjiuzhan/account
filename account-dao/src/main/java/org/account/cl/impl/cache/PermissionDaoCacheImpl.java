package org.account.cl.impl.cache;

import org.account.cl.Permission;
import org.account.cl.PermissionDao;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.mysql.master.PermissionDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.PermissionDaoMysqlSlaveImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO 缓存技术
 * @author Administrator
 */
@Repository
public class PermissionDaoCacheImpl implements PermissionDao {

    @Resource
    private PermissionDaoMysqlMasterImpl permissionDaoMysqlMasterImpl;

    @Resource
    private PermissionDaoMysqlSlaveImpl permissionDaoMysqlSlaveImpl;

    @Override
    public Permission get(int id) {
        return permissionDaoMysqlSlaveImpl.get(id);
    }

    @Override
    public int getsByConditionCount(PermissionQuery query) {
        return permissionDaoMysqlSlaveImpl.getsByConditionCount(query);
    }

    @Override
    public List<Permission> getsByCondition(PermissionQuery query) {
        return permissionDaoMysqlSlaveImpl.getsByCondition(query);
    }

    @Override
    public boolean add(Permission permission) {
        return permissionDaoMysqlMasterImpl.add(permission);
    }

    @Override
    public boolean batchAdd(List<Permission> permissions) {
        return permissionDaoMysqlMasterImpl.batchAdd(permissions);
    }

    @Override
    public boolean updatePermission(int id, PermissionQuery query) {
        return permissionDaoMysqlMasterImpl.updatePermission(id, query);
    }


    @Override
    public boolean updatePermission2(int id, String name, int status) {
        return permissionDaoMysqlMasterImpl.updatePermission2(id, name, status);
    }

    @Override
    public boolean delPermission(int id) {
        return permissionDaoMysqlMasterImpl.delPermission(id);
    }
}
