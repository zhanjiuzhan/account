package org.account.cl.view;

import org.account.cl.view.model.JsonRes;
import org.account.cl.view.resolver.impl.JcJsonViewResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class JcJsonView extends ModelAndView {

    private JsonRes jsonRes;
    private static final int SUCCESS_CODE = 200;
    private static final String ERROR_CODE = "500";
    public static final String ERROR_MSG = "服务器繁忙， 请稍后再试。";

    /**
     * 返回的code
     */
    private static Map<String, String> code = new HashMap() {
        {
            put(ERROR_CODE, ERROR_MSG);
        }
    };

    {
        jsonRes = new JsonRes();
        super.setView(new JcJsonViewResolver(jsonRes));
    }

    public JcJsonView() {}

    public JcJsonView(JsonRes jsonRes) {
        this.jsonRes.setData(jsonRes.getData());
        this.jsonRes.setMsg(jsonRes.getMsg());
        this.jsonRes.setStatus(jsonRes.getStatus());
    }

    public JcJsonView(Object data) {
        if (data == null) {
            return;
        }
        if (data instanceof String || data instanceof Integer) {
            if (data instanceof Integer) {
                data = String.valueOf(data);
            }

            if (code.containsKey(data)) {
                errorCode((String) data);
                return;
            }
        }
        this.jsonRes.setData(data);
    }

    private void errorCode(String code) {
        int retCode = Integer.valueOf(code.substring(0, 3));
        if (retCode != SUCCESS_CODE) {
            this.jsonRes.setStatus(retCode);
            this.jsonRes.setMsg(JcJsonView.code.getOrDefault(code, ERROR_MSG));
        }
    }
}
