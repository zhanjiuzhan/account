package org.account.cl.impl;

import org.account.cl.Role;
import org.account.cl.RoleDao;
import org.account.cl.RoleService;
import org.account.cl.condition.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public boolean add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public boolean delete(int id) {
        return roleDao.delete(id);
    }

    @Override
    public boolean isDisable(int id, boolean op) {
        return roleDao.isDisable(id, op);
    }

    @Override
    public boolean updateRole(int id, RoleQuery query) {
        return roleDao.updateRole(id, query);
    }

    @Override
    public Role get(int id) {
        return roleDao.get(id);
    }

    @Override
    public List<Role> gets() {
        return roleDao.gets();
    }

    @Override
    public List<Role> getsByCondition(RoleQuery query) {
        return roleDao.getsByCondition(query);
    }
}
