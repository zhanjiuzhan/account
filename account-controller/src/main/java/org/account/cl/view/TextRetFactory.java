package org.account.cl.view;

import org.account.cl.view.product.TextView;

/**
 * @author Administrator
 */
public interface TextRetFactory {
    /**
     * 返回文本视图
     * @return
     */
    static TextView getRet() {
        return new TextView("200");
    }

    /**
     * 返回文本视图
     * @param content
     * @return
     */
    static TextView getRet(String content) {
        if (content == null) {
            throw new RuntimeException("传值错误!");
        }
        return new TextView(content);
    }
}
