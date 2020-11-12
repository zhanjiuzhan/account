package org.account.cl.impl.mysql;

import org.account.cl.condition.BaseQuery;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Administrator
 */
public class BaseProvider {

    public static final String ORDER_BY_UPDATE_DESC = " order by update_date desc ";
    public static final String REFRESH_UPDATE = " update_date = now() ";

    protected String getLimit(BaseQuery query) {
        if (query.getPagePoint() == null || query.getPageSize() == null) {
            return "";
        }
        return " limit " + (query.getPagePoint() - 1) * query.getPageSize() + ", " + query.getPageSize();
    }

    protected String getDataBtwVal(String filed, String start, String end) {
        return getDataBtwVal(filed, start, end, "%Y-%m-%d");
    }

    protected String getDataBtwVal(String filed, String start, String end, String format) {
        return "DATE_FORMAT(" + filed +  ", '" + format + "') >= '" + start + "' and DATE_FORMAT(" + filed +  ", '" + format + "') <= '" + end +"'";
    }

    protected String getDataVal(String filed, String value) {
        return getDataValF(filed, value, "%Y-%m-%d");
    }

    protected String getDataValF(String filed, String value, String format) {
        return "DATE_FORMAT(" + filed +  ", '" + format + "') = '" + value + "'";
    }

    protected String getSVal(String filed, final String value) {
        return filed + "= '" + value + "'";
    }

    protected String getNVal(String filed, final long value) {
        return filed + "= " + value;
    }

    protected String getSVal(final String value) {
        return "'" + value + "'";
    }

    protected void makeDateSql(SQL sql, BaseQuery query) {
        if (query.getCreateDateStart() != null && query.getCreateDateEnd() != null) {
            sql.WHERE(getDataBtwVal("create_date", query.getCreateDateStart(), query.getCreateDateEnd()));
        }
        if (query.getUpdateDateStart() != null && query.getUpdateDateEnd() != null) {
            sql.WHERE(getDataBtwVal("update_date", query.getUpdateDateStart(), query.getUpdateDateEnd()));
        }
    }

    protected String getInVal(final String field, final String[] arrays) {
        if (arrays.length == 1) {
            return getSVal(field, arrays[0]);
        } else {
            StringBuilder sbf = new StringBuilder(field + " in (");
            for (int i = 0; i < arrays.length; i++) {
                if (i != arrays.length-1) {
                    sbf.append(getSVal(arrays[i]) + ", ");
                } else {
                    sbf.append(getSVal(arrays[i]));
                }
            }
            sbf.append(") ");
            return sbf.toString();
        }
    }
}
