package org.account.cl.impl.cache;

import org.account.cl.Role;
import org.account.cl.RoleDao;
import org.account.cl.condition.RoleQuery;
import org.account.cl.impl.mysql.master.RoleDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.RoleDaoMysqlSlaveImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Repository
public class RoleDaoCacheImpl implements RoleDao {

    @Resource
    private RoleDaoMysqlMasterImpl roleDaoMysqlMasterImpl;

    @Resource
    private RoleDaoMysqlSlaveImpl roleDaoMysqlSlaveImpl;

    @Override
    public boolean add(Role role) {
        return roleDaoMysqlMasterImpl.add(role);
    }

    @Override
    public boolean delete(int id) {
        return roleDaoMysqlMasterImpl.delete(id);
    }

    @Override
    public boolean isDisable(int id, boolean op) {
        return false;
    }

    @Override
    public boolean updateRole(int id, RoleQuery query) {
        return roleDaoMysqlMasterImpl.updateRole(id, query);
    }

    @Override
    public Role get(int id) {
        return roleDaoMysqlSlaveImpl.get(id);
    }

    @Override
    public List<Role> gets() {
        return roleDaoMysqlSlaveImpl.gets();
    }

    @Override
    public List<Role> getsByCondition(RoleQuery query) {
        return roleDaoMysqlSlaveImpl.getsByCondition(query);
    }
}
