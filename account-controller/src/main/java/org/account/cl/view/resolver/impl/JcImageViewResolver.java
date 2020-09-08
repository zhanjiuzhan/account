package org.account.cl.view.resolver.impl;

import org.account.cl.view.model.ImageRes;
import org.account.cl.view.resolver.JcAbstractViewResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author Administrator
 */
public class JcImageViewResolver extends JcAbstractViewResolver {

    private ImageRes imageRes;

    public JcImageViewResolver(ImageRes imageRes) {
        this.imageRes = imageRes;
    }

    @Override
    protected void makeResponse(HttpServletResponse response) throws Exception {

        OutputStream out = response.getOutputStream();
        ImageIO.write(this.imageRes.getImg(), this.imageRes.getType(), out);
    }

    @Override
    protected String getContentSpType() {
        if (this.imageRes.getType() == ImageRes.JPG) {
            return "image/jpeg";
        } else {
            // TODO
            return "";
        }
    }
}
