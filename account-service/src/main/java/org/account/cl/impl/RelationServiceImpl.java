package org.account.cl.impl;

import org.account.cl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class RelationServiceImpl implements RelationService {

    @Autowired
    private RelationDao relationDao;

    @Override
    public boolean isExistPermissionInRelation(int permissionId) {
        return relationDao.isExistPermissionInRelation(permissionId);
    }

    @Override
    public boolean isExistRoleInRelation(int roleId) {
        return relationDao.isExistRoleInRelation(roleId);
    }

    @Override
    public boolean isExistUserInRelation(String username) {
        return relationDao.isExistUserInRelation(username);
    }

    @Override
    public boolean unBindUserRelation(String username) {
        return relationDao.unBindUserRelation(username);
    }

    @Override
    public boolean unBindUserRelation(String username, int roleId) {
        return relationDao.unBindUserRelation(username, roleId);
    }

    @Override
    public boolean bindUserRelation(String username, int roleId) {
        return relationDao.bindUserRelation(username, roleId);
    }

    @Override
    public boolean unBindRoleRelation(int roleId) {
        return relationDao.unBindRoleRelation(roleId);
    }

    @Override
    public boolean unBindRoleRelation(int roleId, int permissionId) {
        return relationDao.unBindRoleRelation(roleId, permissionId);
    }

    @Override
    public boolean bindRoleRelation(int roleId, int permissionId) {
        return relationDao.bindRoleRelation(roleId, permissionId);
    }

    @Override
    public boolean delRoleRelation(int roleId) {
        return relationDao.delRoleRelation(roleId);
    }

    @Override
    public boolean delPermissionRelation(int permissionId) {
        return relationDao.delPermissionRelation(permissionId);
    }

    @Override
    public List<Role> getRoleByUser(String username) {
        return relationDao.getRoleByUser(username);
    }

    @Override
    public List<Permission> getPermissionByUser(String username) {
        return relationDao.getPermissionByUser(username);
    }

    @Override
    public List<Permission> getPermissionByUserAndPro(String projectName, String username) {
        return relationDao.getPermissionByUserAndPro(projectName, username);
    }

    @Override
    public List<Permission> getPermissionByRole(int roleId) {
        return relationDao.getPermissionByRole(roleId);
    }

    @Override
    public List<RolePermission> gets() {
        return relationDao.gets();
    }

    @Override
    public List<RolePermission> getsByCondition() {
        return relationDao.getsByCondition();
    }
}
