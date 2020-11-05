package org.account.cl.impl.mysql;

import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.mysql.master.PermissionDaoMysqlMasterImpl;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Administrator
 */
public class PermissionDaoMysqlProvider extends BaseProvider {

    private static final String PERMISSION_TAB = PermissionDaoMysqlMasterImpl.PERMISSION_TAB;

    /**
     * 根据条件查询用户的信息 有分页
     * @param query
     * @return
     */
    public String getsByConditionCount(PermissionQuery query) {
        SQL sql = new SQL();
        sql.SELECT("count(id)").FROM(PERMISSION_TAB);
        sql = makeCommonQuery(sql, query);
        return sql.toString();
    }

    /**
     * 根据条件查询用户的信息 有分页 可以无分页限制
     * @param query
     * @return
     */
    public String getsByCondition(PermissionQuery query) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(PERMISSION_TAB);
        sql = makeCommonQuery(sql, query);
        return sql.toString() + ORDER_BY_UPDATE_DESC + getLimit(query);
    }

    private SQL makeCommonQuery(SQL sql, PermissionQuery query) {
        if (query.getProject() != null) {
            sql.WHERE(getSVal("project", query.getProject()));
        }
        if (query.getMethod() != null) {
            sql.WHERE(getSVal("method", query.getMethod()));
        }
        if (query.getUrl() != null) {
            sql.WHERE(getSVal("url", query.getUrl()));
        }
        if (query.getStatus() != null) {
            sql.WHERE(getNVal("status", query.getStatus()));
        }
        makeDateSql(sql, query);
        return sql;
    }

    public String updatePermission(int id, PermissionQuery query) {
        SQL sql = new SQL();
        sql.UPDATE(PERMISSION_TAB);

        if (query.getProject() != null) {
            sql.SET(getSVal("project", query.getProject()));
        }
        if (query.getUrl() != null) {
            sql.SET(getSVal("url", query.getUrl()));
        }
        if (query.getMethod() != null) {
            sql.SET(getSVal("method", query.getMethod()));
        }
        if (query.getStatus() != null) {
            sql.SET(getNVal("status", query.getStatus()));
        }
        sql.SET(REFRESH_UPDATE);
        sql.WHERE(getNVal("id", id));
        return sql.toString();
    }
}
