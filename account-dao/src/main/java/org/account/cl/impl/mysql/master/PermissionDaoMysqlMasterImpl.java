package org.account.cl.impl.mysql.master;

import org.account.cl.Permission;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface PermissionDaoMysqlMasterImpl {

    String PERMISSION_TAB = "permission";

    /**
     * 添加一个权限信息
     * @param permission
     * @return
     */
    @Insert("insert into " + PERMISSION_TAB + "(name, url, method, project, status, update_date, create_date) values " +
            "(#{name}, #{url}, #{method}, #{project}, #{status}, now(), now())")
    boolean add(Permission permission);

    boolean batchAdd(List<Permission> permissions);

    boolean updatePermission(Permission permission);

    boolean updatePermission2(int id, String name, int status);

    boolean delPermission(int id);
}
