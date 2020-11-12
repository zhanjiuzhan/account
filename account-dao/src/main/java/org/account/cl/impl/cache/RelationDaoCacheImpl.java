package org.account.cl.impl.cache;

import org.account.cl.Permission;
import org.account.cl.RelationDao;
import org.account.cl.Role;
import org.account.cl.RolePermission;
import org.account.cl.impl.mysql.master.RelationDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.RelationDaoMysqlSlaveImpl;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public class RelationDaoCacheImpl implements RelationDao {

    @Resource
    private RelationDaoMysqlMasterImpl relationDaoMysqlMasterImpl;

    @Resource
    private RelationDaoMysqlSlaveImpl relationDaoMysqlSlaveImpl;

    @Override
    public boolean isExistPermissionInRelation(int permissionId) {
        return relationDaoMysqlSlaveImpl.isExistPermissionInRelation(permissionId);
    }

    @Override
    public boolean isExistRoleInRelation(int roleId) {
        return relationDaoMysqlSlaveImpl.isExistRoleInRelation1(roleId);
    }

    @Override
    public boolean isExistUserInRelation(String username) {
        return false;
    }

    @Override
    public boolean unBindUserRelation(String username) {
        return relationDaoMysqlMasterImpl.unBindUserRelation1(username);
    }

    @Override
    public boolean unBindUserRelation(String username, int roleId) {
        return false;
    }

    @Override
    public boolean bindUserRelation(String username, int roleId) {
        return false;
    }

    @Override
    public boolean unBindRoleRelation(int roleId) {
        return false;
    }

    @Override
    public boolean unBindRoleRelation(int roleId, int permissionId) {
        return false;
    }

    @Override
    public boolean bindRoleRelation(int roleId, int permissionId) {
        return false;
    }

    @Override
    public boolean delRoleRelation(int roleId) {
        return false;
    }

    @Override
    public boolean delPermissionRelation(int permissionId) {
        return false;
    }

    @Override
    public List<Role> getRoleByUser(String username) {
        return relationDaoMysqlSlaveImpl.getRoleByUser(username);
    }

    @Override
    public List<Permission> getPermissionByUser(String username) {
        return relationDaoMysqlSlaveImpl.getPermissionByUser(username);
    }

    @Override
    public List<Permission> getPermissionByUserAndPro(String projectName, String username) {
        return relationDaoMysqlSlaveImpl.getPermissionByUserAndPro(projectName, username);
    }

    @Override
    public List<Permission> getPermissionByRole(int roleId) {
        return relationDaoMysqlSlaveImpl.getPermissionByRole(roleId);
    }

    @Override
    public List<RolePermission> gets() {
        return relationDaoMysqlSlaveImpl.gets();
    }

    @Override
    public List<RolePermission> getsByCondition() {
        return null;
    }
}
