package org.account.cl.controller;

import org.account.cl.JcStringUtils;
import org.account.cl.Project;
import org.account.cl.ProjectService;
import org.account.cl.exception.exception.ExceptionEnum;
import org.account.cl.view.JsonRetFactory;
import org.account.cl.view.product.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/admin/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/get/{name}.do")
    public JsonView get(@PathVariable String name) {
        checkName(name);
        return JsonRetFactory.getRet(projectService.get(name));
    }

    @GetMapping("/gets.do")
    public JsonView gets() {
        return JsonRetFactory.getRet(projectService.gets());
    }

    @PostMapping("/add.do")
    public JsonView add(Project project) {
        checkProject(project);
        return JsonRetFactory.getRet(projectService.add(project));
    }

    @DeleteMapping("/del/{name}.do")
    public JsonView del(@PathVariable  String name) {
        checkName(name);
        return JsonRetFactory.getRet(projectService.del(name));
    }

    @PutMapping("/update.do")
    public JsonView update(Project project) {
        checkProject(project);
        return JsonRetFactory.getRet(projectService.update(project));
    }

    private void checkName(String name) {
        if (JcStringUtils.isBlank(name)) {
            ExceptionEnum.INVALID_PARAMETER1.assertTrue(false, "name");
        }
        // 其它校验 TODO
    }

    private void checkProject(Project permission) {
        // 校验查询条件的正确性
        // TODO 正则校验字段的正确性
    }
}
