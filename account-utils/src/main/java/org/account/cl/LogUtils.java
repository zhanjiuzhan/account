package org.account.cl;

import org.slf4j.Logger;

/**
 * @author Administrator
 */
final public class LogUtils {

    private Logger logger;

    public LogUtils(Logger logger) {
        this.logger = logger;
    }

    public long start(String msg) {
        logger.info("[开始] " + msg);
        return System.currentTimeMillis();
    }

    public void end(String msg, long start) {
        logger.info("[结束] " + msg + " 耗时: " + (System.currentTimeMillis() - start));
    }
}
