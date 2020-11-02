package org.account.cl.impl.mysql.slave;

import org.account.cl.Permission;
import org.account.cl.condition.PermissionQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface PermissionDaoMysqlSlaveImpl {

    String PERMISSION_TAB = "permission";

    Permission get(int id);

    int getsByConditionCount(PermissionQuery query);

    List<Permission> getsByCondition(PermissionQuery query);
}
