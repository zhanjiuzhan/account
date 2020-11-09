package org.account.cl.impl.mysql;

import org.account.cl.condition.RoleQuery;
import org.account.cl.impl.mysql.master.RoleDaoMysqlMasterImpl;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Administrator
 */
public class RoleDaoMysqlImplProvider extends BaseProvider {

    private static final String ROLE_TAB = RoleDaoMysqlMasterImpl.ROLE_TAB;

    // TODO 存在sql注入问题
    public String updateRole(int id, RoleQuery query) {
        SQL sql = new SQL();
        sql.UPDATE(ROLE_TAB);

        if (query.getPid() != null) {
            sql.SET(getNVal("pid", query.getPid()));
        }

        if (query.getName() != null) {
            sql.SET(getSVal("name", query.getName()));
        }

        if (query.getDescription() != null) {
            sql.SET(getSVal("description", query.getDescription()));
        }

        if (query.getStatus() != null) {
            sql.SET(getNVal("status", query.getStatus()));
        }

        sql.SET(REFRESH_UPDATE);
        sql.WHERE(getNVal("sid", id));
        return sql.toString();
    }
}
