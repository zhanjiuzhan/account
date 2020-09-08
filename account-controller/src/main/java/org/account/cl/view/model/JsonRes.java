package org.account.cl.view.model;

/**
 * @author Administrator
 */
public class JsonRes {
    private int status;
    private Object data;
    private String msg;

    /**
     * 默认是200
     */
    public JsonRes() {
        this.status = 200;
        this.msg = "";
        this.data = "";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
