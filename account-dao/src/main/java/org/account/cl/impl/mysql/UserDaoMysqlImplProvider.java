package org.account.cl.impl.mysql;

import org.account.cl.condition.UserQuery;
import org.account.cl.impl.mysql.slave.UserDaoMysqlSlaveImpl;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Administrator
 */
public class UserDaoMysqlImplProvider extends BaseProvider {

    private static final String USER_TAB = UserDaoMysqlSlaveImpl.USER_TAB;

    /**
     * 根据条件查询用户的信息 有分页
     * @param query
     * @return
     */
    public String getsByConditionCount(UserQuery query) {
        SQL sql = new SQL();
        sql.SELECT("count(username)").FROM(USER_TAB);
        sql = makeCommonQuery(sql, query);
        return sql.toString();
    }

    /**
     * 根据条件查询用户的信息 有分页 可以无分页限制
     * @param query
     * @return
     */
    public String getsByCondition(UserQuery query) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(USER_TAB);
        sql = makeCommonQuery(sql, query);
        return sql.toString() + " order by update_date desc " + getLimit(query.getPagePoint(), query.getPageSize());
    }

    private SQL makeCommonQuery(SQL sql, UserQuery query) {
        if (query.getCredentialsExpired() != null) {
            sql.WHERE(getNVal("credentials_expired", query.getCredentialsExpired() ? 1 : 0));
        }
        if (query.getEnable() != null) {
            sql.WHERE(getNVal("isEnable", query.getEnable() ? 1 : 0));
        }
        if (query.getExpired() != null) {
            sql.WHERE(getNVal("expired", query.getExpired() ? 1 : 0));
        }
        if (query.getLocked() != null) {
            sql.WHERE(getNVal("locked", query.getLocked() ? 1 : 0));
        }
        makeDateSql(sql, query);
        return sql;
    }

    public String updateUser(String username, UserQuery query) {
        SQL sql = new SQL();
        sql.UPDATE(USER_TAB);
        if (query.getPassword() != null && query.getPassword().trim().length() > 0) {
            sql.SET(getSVal("password", query.getPassword()));
        }
        if (query.getCredentialsExpired() != null) {
            sql.SET(getNVal("credentials_expired", query.getCredentialsExpired() ? 1 : 0));
        }
        if (query.getEnable()  != null) {
            sql.SET(getNVal("isEnable", query.getEnable() ? 1 : 0));
        }
        if (query.getLocked()  != null) {
            sql.SET(getNVal("locked", query.getLocked() ? 1 : 0));
        }
        if (query.getExpired() != null) {
            sql.SET(getNVal("expired", query.getExpired() ? 1 : 0));
        }
        sql.SET("update_date = now()");
        sql.WHERE(getSVal("username", username));
        return sql.toString();
    }

    public static void main(String[] args) {
        UserDaoMysqlImplProvider u = new UserDaoMysqlImplProvider();
        UserQuery q = new UserQuery();
        q.setCreateDateStart("2020-10-02")
         .setCreateDateEnd("2020-11-02")
         .setCredentialsExpired(true)
         .setEnable(true)
         .setExpired(true)
         .setLocked(false)
         .setPagePoint(1)
         .setPageSize(20)
         .setUpdateDateStart("2020-10-02")
         .setUpdateDateEnd("2020-11-02");
        System.out.println(u.getsByConditionCount(q));
        System.out.println(u.getsByCondition(q));
        System.out.println(u.updateUser("xx", q));
    }
}
