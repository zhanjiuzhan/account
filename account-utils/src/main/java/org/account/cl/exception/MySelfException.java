package org.account.cl.exception;

import org.account.cl.exception.exception.ExceptionEnum;

/**
 * @author Administrator
 */
public class MySelfException extends RuntimeException implements ExceptionResponse {

    private int code;
    private String message;
    private ExceptionEnum ex;

    public MySelfException(final ExceptionEnum ex, final String message) {
        super(message);
        init(ex, message);
    }

    public MySelfException(final ExceptionEnum ex, final String message, final Throwable cause) {
        super(message, cause);
        init(ex, message);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ExceptionEnum getExceptionEnum() {
        return this.ex;
    }

    private void init(ExceptionEnum ex, String message) {
        this.code = ex.getCode();
        this.message = message;
        this.ex = ex;
    }
}
