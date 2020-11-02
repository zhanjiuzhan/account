package org.account.cl.impl.mysql;

/**
 * @author Administrator
 */
public class BaseProvider {
    protected String getLimit(Integer currentPage, Integer pageSize) {
        if (currentPage == null || pageSize == null) {
            return "";
        }
        return " limit " + (currentPage - 1) * pageSize + ", " + pageSize;
    }

    protected String getDataVal(String filed, String sql) {
        return filed + "= DATE_FORMAT(" + sql +  ", '%Y-%m-%d')";
    }

    protected String getDataValF(String filed, String sql, String format) {
        return filed + "= DATE_FORMAT(" + sql +  ", '" + format + "')";
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
