package org.account.cl.impl;

import org.account.cl.Project;
import org.account.cl.ProjectDao;
import org.account.cl.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public boolean add(Project project) {
        return projectDao.add(project);
    }

    @Override
    public boolean del(String name) {
        return projectDao.del(name);
    }

    @Override
    public boolean update(Project project) {
        return projectDao.update(project);
    }

    @Override
    public Project get(String name) {
        return projectDao.get(name);
    }

    @Override
    public List<Project> gets() {
        return projectDao.gets();
    }
}
