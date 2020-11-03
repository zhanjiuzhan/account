package org.account.cl.impl.mysql.master;

import org.account.cl.Permission;
import org.account.cl.condition.PermissionQuery;
import org.account.cl.impl.mysql.PermissionDaoMysqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
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

    /**
     * 批量添加权限信息
     * @param permissions
     * @return
     */
    @Insert({
            "<script>",
            "insert into " + PERMISSION_TAB + "(name, url, method, project, status, update_date, create_date) values ",
            "<foreach collection='permissions' item='permission' index='index' separator=','>",
            "(#{permission.name}, #{permission.url}, #{permission.method}, #{permission.project}, #{permission.status}, now(), now())",
            "</foreach>",
            "</script>"
    })
    boolean batchAdd(@Param("permissions") List<Permission> permissions);

    /**
     * 更新一个权限信息
     * @param id
     * @param query
     * @return
     */
    @UpdateProvider(type = PermissionDaoMysqlProvider.class, method = "updatePermission")
    boolean updatePermission(int id, PermissionQuery query);

    /**
     * 更新一个权限信息
     * @param id
     * @param name
     * @param status
     * @return
     */
    @Update("update " + PERMISSION_TAB + " set name = #{name}, status = #{status}, update_date=now() where id =#{id}")
    boolean updatePermission2(int id, String name, int status);

    /**
     * 删除一个权限信息
     * @param id
     * @return
     */
    @Delete("delete from " + PERMISSION_TAB + " where id =#{id}")
    boolean delPermission(int id);
}
