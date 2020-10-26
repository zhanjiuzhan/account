package org.account.cl.view;


import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;

/**
 * @author Administrator
 */
public interface JsonRetFactory {

    /**
     * 200 的返回结果就不用一直new对象了
     */
    JsonView JSON_VIEW = new JsonView();

    /**
     * 返回一个Json结果集
     * @return
     */
    static JsonView getRet() {
        return JSON_VIEW;
    }

    /**
     * 返回一个Json结果集
     * @param code
     * @return
     */
    static JsonView getRet(int code) {
        return new JsonView(RetUtils.ERROR_CODE, RetUtils.ERROR_MSG);
    }

    /**
     * 返回一个Json结果集
     * @param data
     * @return
     */
    static JsonView getRet(Object data) {
        if (data == null) {
            throw new RuntimeException("传值错误!");
        }
        return new JsonView(data);
    }

    /**
     * 返回一个Json结果集
     * @param code
     * @param msg
     * @return
     */
    static JsonView getRet(int code, String msg) {
        if (msg == null) {
            throw new RuntimeException("传值错误!");
        }
        return new JsonView(code, msg);
    }

    /**
     * 返回一个Json结果集
     * @param ret
     * @return
     */
    static JsonView getRet(JsonView.JsonRet ret) {
        if (ret == null) {
            throw new RuntimeException("传值错误!");
        }
        return new JsonView(ret);
    }
}
