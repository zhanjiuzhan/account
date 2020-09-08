package org.account.cl.view.init;

import org.account.cl.view.JcJsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * @author Administrator
 */
@Component
public class ResCodeInit implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ResCodeInit.class);

    @Autowired
    private Environment env;

    /**
     * 默认在classPath下
     */
    private final String DEF_PATH = "res-code.properties";

    /**
     * 容器启动中执行的方法
     */
    private void initialize() {
        String resCodePath = env.getProperty("res.code.path");
        if (resCodePath == null || resCodePath.trim().length() < 0) {
            getResCode(DEF_PATH);
        } else {
            getResCode(resCodePath);
        }
    }

    /**
     * 根据路径读取properties文件中的内容
     * @param path
     */
    private void getResCode(String path) {
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            File pro = classPathResource.getFile();
            if (!pro.exists()) {
                throw new IOException();
            }
            Properties properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pro));
            properties.load(bufferedReader);
            initRetCode(properties, path);
        } catch (IOException e) {
            logger.warn("返回码文件[{}]不存在，使用初始化配置。", path);
        }
    }

    private void initRetCode(Properties map, String path) {
        try {
            Field retCode = JcJsonView.class.getDeclaredField("code");
            retCode.setAccessible(true);
            Map<String, String> code = (Map<String, String>) retCode.get(JcJsonView.class);
            for (Map.Entry tmp : map.entrySet()) {
                String key = (String)tmp.getKey();
                String val = (String)tmp.getValue();
                if (key.length() < 3 || val == null || val.trim().length() == 0) {
                    logger.error("无效code[{}={}]", key, val);
                    continue;
                }
                try {
                    Integer.parseInt(key);
                }  catch (NumberFormatException e) {
                    logger.error("无效code[{}={}]", key, val);
                    continue;
                }
                code.put(key, val);
            }
            logger.info("返回码文件[{}]加载成功。", path);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 容器初始化之后被回调执行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialize();
    }
}
