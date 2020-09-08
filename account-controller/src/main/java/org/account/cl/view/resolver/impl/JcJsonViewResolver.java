package org.account.cl.view.resolver.impl;

import com.alibaba.fastjson.JSON;
import org.account.cl.view.model.JsonRes;
import org.account.cl.view.resolver.JcAbstractViewResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @author Administrator
 */
public class JcJsonViewResolver extends JcAbstractViewResolver {

    private JsonRes jsonObject;

    public JcJsonViewResolver(JsonRes obj) {
        this.jsonObject = obj;
    }

    @Override
    protected void makeResponse(HttpServletResponse response) throws Exception {
        Object dataObj = this.jsonObject.getData();
        String res = JSON.toJSONString(jsonObject);
        response.setContentLength(res.getBytes("UTF-8").length);
        Writer out = response.getWriter();
        out.write(res);
        out.flush();
    }

    @Override
    protected String getContentSpType() {
        return "application/json; charset=utf-8";
    }
}
