package org.account.cl.reqlog;

import org.apache.tomcat.util.buf.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Administrator
 */
@Aspect
@Component
public class ControllerLogAspect {
    private final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    @Pointcut("@annotation(org.account.cl.reqlog.ControllerLog)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation annotation = method.getAnnotation(RequestMapping.class);
        if (annotation == null) {
            annotation = method.getAnnotation(GetMapping.class);
        }
        if (annotation == null) {
            annotation = method.getAnnotation(PostMapping.class);
        }
        if (annotation == null) {
            annotation = method.getAnnotation(PutMapping.class);
        }
        if (annotation == null) {
            annotation = method.getAnnotation(DeleteMapping.class);
        }
        if (annotation == null) {
            annotation = method.getAnnotation(PatchMapping.class);
        }

        String[] paths = null;
        Class clazz = annotation.annotationType();
        if (clazz == RequestMapping.class) {
            paths = ((RequestMapping)annotation).value();
        }
        if (clazz == GetMapping.class) {
            paths = ((GetMapping)annotation).value();
        }
        if (clazz == PostMapping.class) {
            paths = ((PostMapping)annotation).value();
        }
        if (clazz == PutMapping.class) {
            paths = ((PutMapping)annotation).value();
        }
        if (clazz == DeleteMapping.class) {
            paths = ((DeleteMapping)annotation).value();
        }
        if (clazz == PatchMapping.class) {
            paths = ((PatchMapping)annotation).value();
        }

        Object[] args = joinPoint.getArgs();
        logger.info("请求路径为: " + StringUtils.join(paths));
        StringBuilder par = new StringBuilder();
        for (Object tmp : args) {
            par.append(tmp.toString());
            par.append(", ");
        }
        logger.info("请求参数为: " + par.substring(0, par.length() > 0 ? par.length() - 1 : 0));
    }
}
