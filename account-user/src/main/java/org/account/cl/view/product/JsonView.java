package org.account.cl.view.product;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @author chenglei
 */
public class JsonView extends ModelAndView {

    private JsonRet jsonRet;

    {
        super.setView(new JsonViewResolver());
    }

    public JsonView() {
        this.jsonRet = new JsonRet();
    }

    public JsonView(Object obj) {
        this.jsonRet = new JsonRet(obj);
    }

    public JsonView(int code, String msg) {
        this.jsonRet = new JsonRet(code, msg);
    }

    public JsonView(JsonRet jsonRet) {
        this.jsonRet = jsonRet;
    }

    private class JsonViewResolver extends JcAbstractViewResolver {

        @Override
        protected void makeResponse(HttpServletResponse response) throws Exception {
            String res = JSON.toJSONString(jsonRet);
            response.setContentLength(res.getBytes(RetUtils.CHARACTER_CODE).length);
            Writer out = response.getWriter();
            out.write(res);
            out.flush();
        }

        @Override
        protected String getContentSpType() {
            return RetUtils.CONTENT_TYPE_JSON;
        }
    }

    public static class JsonRet {
        private int code;
        private Object data;
        private String msg;

        public JsonRet() {
            this.code = RetUtils.SUCCESS_CODE;
            this.data = RetUtils.DEFAULT_VALUE;
            this.msg = RetUtils.DEFAULT_VALUE;
        }

        public JsonRet(Object obj) {
            this.code = RetUtils.SUCCESS_CODE;
            this.data = obj;
            this.msg = RetUtils.DEFAULT_VALUE;
        }

        public JsonRet(int code, String msg) {
            this.code = code;
            this.msg = msg;
            this.data = RetUtils.DEFAULT_VALUE;
        }

        public int getCode() {
            return code;
        }

        public JsonRet setCode(int code) {
            this.code = code;
            return this;
        }

        public Object getData() {
            return data;
        }

        public JsonRet setData(Object data) {
            this.data = data;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public JsonRet setMsg(String msg) {
            this.msg = msg;
            return this;
        }
    }
}
