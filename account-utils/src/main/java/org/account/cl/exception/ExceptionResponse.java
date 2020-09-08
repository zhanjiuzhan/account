package org.account.cl.exception;

/**
 * @author Administrator
 */
public interface ExceptionResponse {
    /**
     * 取得错误码
     * @return
     */
    int getCode();

    /**
     * 取得错误信息
     * @return
     */
    String getMessage();
}
