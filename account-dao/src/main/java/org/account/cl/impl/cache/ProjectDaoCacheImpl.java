package org.account.cl.impl.cache;

import org.account.cl.Project;
import org.account.cl.ProjectDao;
import org.account.cl.impl.mysql.master.ProjectDaoMysqlMasterImpl;
import org.account.cl.impl.mysql.slave.ProjectDaoMysqlSlaveImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO 缓存技术
 * @author Administrator
 */
@Repository
public class ProjectDaoCacheImpl implements ProjectDao {

    @Resource
    private ProjectDaoMysqlMasterImpl projectDaoMysqlMasterImpl;

    @Resource
    private ProjectDaoMysqlSlaveImpl projectDaoMysqlSlaveImpl;

    @Override
    public boolean add(Project project) {
        return projectDaoMysqlMasterImpl.add(project);
    }

    @Override
    public boolean del(String name) {
        return projectDaoMysqlMasterImpl.del(name);
    }

    @Override
    public boolean update(Project project) {
        return projectDaoMysqlMasterImpl.update(project);
    }

    @Override
    public Project get(String name) {
        return projectDaoMysqlSlaveImpl.get(name);
    }

    @Override
    public List<Project> gets() {
        return projectDaoMysqlSlaveImpl.gets();
    }
}
