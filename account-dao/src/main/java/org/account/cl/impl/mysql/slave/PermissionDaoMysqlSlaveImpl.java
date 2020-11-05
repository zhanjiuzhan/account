package org.account.cl.impl.mysql.slave;

import org.account.cl.Permission;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.mysql.PermissionDaoMysqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface PermissionDaoMysqlSlaveImpl {

    String PERMISSION_TAB = "permission";

    /**
     * 根据Id 查询权限信息
     * @param id
     * @return
     */
    @Select("select * from " + PERMISSION_TAB + " where id = #{id}  limit 1")
    Permission get(int id);

    /**
     * 根据条件查询符合的权限信息的数量
     * @param query
     * @return
     */
    @SelectProvider(type = PermissionDaoMysqlProvider.class, method = "getsByConditionCount")
    int getsByConditionCount(PermissionQuery query);

    /**
     * 根据条件查询符合的权限信息
     * @param query
     * @return
     */
    @SelectProvider(type = PermissionDaoMysqlProvider.class, method = "getsByCondition")
    List<Permission> getsByCondition(PermissionQuery query);
}
