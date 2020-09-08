package org.account.cl.exception.exception;

import org.account.cl.exception.ExceptionAssert;
import org.account.cl.exception.ExceptionResponse;
import org.account.cl.exception.MySelfException;

import java.text.MessageFormat;

/**
 * @author Administrator
 */

public enum ExceptionEnum implements ExceptionResponse, ExceptionAssert {
    /**
     * 内部方法调用参数不合法
     */
    INTERNAL_INVALID_PARAMETER(500, "内部方法参数传递错误.  类名.方法名: {0}, 参数名: {1}, 传递值: {2}"),

    /**
     * 主要针对于对外接口调用参数异常
     */
    INVALID_PARAMETER(400, "参数[{0}]信息错误: {1}, {0}: {2}"),
    ;

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public MySelfException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new MySelfException(this, msg);
    }

    @Override
    public MySelfException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new MySelfException(this, msg, t);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
