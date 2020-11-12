package org.account.cl.impl.cache;

import org.account.cl.Permission;
import org.account.cl.RelationDao;
import org.account.cl.Role;
import org.account.cl.RolePermission;

import java.util.List;

public class RelationDaoCacheImpl implements RelationDao {
    @Override
    public boolean isExistPermissionInRelation(int permissionId) {
        return false;
    }

    @Override
    public boolean isExistRoleInRelation(int roleId) {
        return false;
    }

    @Override
    public boolean isExistUserInRelation(String username) {
        return false;
    }

    @Override
    public boolean unBindUserRelation(String username) {
        return false;
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
        return null;
    }

    @Override
    public List<Permission> getPermissionByUser(String username) {
        return null;
    }

    @Override
    public List<Permission> getPermissionByUserAndPro(String projectName, String username) {
        return null;
    }

    @Override
    public List<Permission> getPermissionByRole(int roleId) {
        return null;
    }

    @Override
    public List<RolePermission> gets() {
        return null;
    }

    @Override
    public List<RolePermission> getsByCondition() {
        return null;
    }
}
