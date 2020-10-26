package org.account.cl.controller;

import org.account.cl.exception.MySelfException;
import org.account.cl.view.product.JsonView;
import org.account.cl.view.product.RetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理类
 * @author Administrator
 */
@ControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnMissingBean(ExceptionController.class)
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * 业务异常
     * @param ex 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = MySelfException.class)
    public JsonView handleMySelfException(MySelfException ex) {
        JsonView.JsonRet res = new JsonView.JsonRet();
        switch (ex.getExceptionEnum()) {
            case INVALID_PARAMETER:
                return exeInvalidParameterException(res, ex);
            case INTERNAL_INVALID_PARAMETER:
                return exeInternalInvalidParameterException(res, ex);
            default:
                logger.error("未知类型异常");
                return new JsonView(500);
        }
    }

    private JsonView exeInvalidParameterException(JsonView.JsonRet res, MySelfException ex) {
        res.setCode(ex.getCode());
        res.setMsg(ex.getMessage());
        logger.error("外部调用接口传递参数异常: " + ex.getMessage());
        return new JsonView(res);
    }

    private JsonView exeInternalInvalidParameterException(JsonView.JsonRet res, MySelfException ex) {
        res.setCode(ex.getCode());
        res.setMsg(RetUtils.ERROR_MSG);
        logger.error("内部调用接口传递参数异常: " + ex.getMessage());
        ex.printStackTrace();
        return new JsonView(res);
    }
}
