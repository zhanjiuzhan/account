package org.account.cl.impl.cache;

import org.account.cl.Project;
import org.account.cl.ProjectDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public class ProjectDaoCacheImpl implements ProjectDao {
    @Override
    public boolean add(Project project) {
        return false;
    }

    @Override
    public boolean del(String name) {
        return false;
    }

    @Override
    public boolean update(Project project) {
        return false;
    }

    @Override
    public Project get(String name) {
        return null;
    }

    @Override
    public List<Project> gets() {
        return null;
    }
}
