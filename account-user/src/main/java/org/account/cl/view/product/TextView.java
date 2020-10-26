package org.account.cl.view.product;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * @author chenglei
 */
public class TextView extends ModelAndView {
    private String content;

    public TextView(String content) {
        this.content = content;
        super.setView(new TextViewResolver());
    }

    public class TextViewResolver extends JcAbstractViewResolver {

        @Override
        protected void makeResponse(HttpServletResponse response) throws Exception {
            response.setContentLength(content.getBytes(RetUtils.CHARACTER_CODE).length);
            Writer out = response.getWriter();
            out.write(content);
            out.flush();
        }

        @Override
        protected String getContentSpType() {
            return RetUtils.CONTENT_TYPE_TEXT;
        }
    }
}
