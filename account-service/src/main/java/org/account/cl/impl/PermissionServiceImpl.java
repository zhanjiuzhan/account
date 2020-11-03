package org.account.cl.impl;

import org.account.cl.Permission;
import org.account.cl.PermissionService;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.cache.PermissionDaoCacheImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

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

    /**
     * 发生异常需要回滚的
     * @param project
     * @param permissions
     */
    @Override
    @Transactional(transactionManager="masterTransactionManager", rollbackFor= Exception.class)
    public boolean refreshPermissions(String project, final List<Permission> permissions) {
        try {
            final List<Permission> oldPermissions = permissionDaoCache.getsByCondition(new PermissionQuery().setProject(project));
            // 存在则不变 ignore
            // 原来有 现在不存在则标记 status 为 2 推荐删除的意思
            oldPermissions.stream().filter(oldP -> permissions.stream().noneMatch(oldP::equals)).forEach(oldP -> {
                permissionDaoCache.updatePermission2(oldP.getId(), oldP.getName(), 2);
            });
            // 原来没有 现在有了 则添加 status 0
            permissions.stream().filter(newP -> oldPermissions.stream().noneMatch(newP::equals)).forEach(newP -> {
                permissionDaoCache.add(newP);
            });
            return true;
        } catch (RuntimeException e) {
            logger.error("更新项目权限失败: " + e.getMessage());
            // 进行回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
