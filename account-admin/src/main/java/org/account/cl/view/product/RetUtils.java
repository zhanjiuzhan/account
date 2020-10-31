package org.account.cl.view.product;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * 反馈结果工具类
 * @author Administrator
 */
final public class RetUtils {
    public final static String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    public final static String CONTENT_TYPE_TEXT = "text/plain; charset=UTF-8";
    public final static String DEFAULT_VALUE = "";
    public final static String CHARACTER_CODE = "UTF-8";
    public final static int SUCCESS_CODE = 200;
    public final static int ERROR_CODE = 500;
    public final static String ERROR_MSG = "服务器繁忙， 请稍后再试。";

    public static void sendJsonResponse(JsonView.JsonRet jsonRet, HttpServletResponse response) {
        response.setContentType(RetUtils.CONTENT_TYPE_JSON);
        String res = JSON.toJSONString(jsonRet);
        try (Writer out = response.getWriter();) {
            response.setContentLength(res.getBytes(RetUtils.CHARACTER_CODE).length);
            out.write(res);
            out.flush();
        } catch (Exception e) {
            // 其实不会走到的
            e.printStackTrace();
        }
    }
}
