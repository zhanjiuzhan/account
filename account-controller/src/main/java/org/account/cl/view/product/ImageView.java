package org.account.cl.view.product;

import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenglei
 */
public class ImageView extends ModelAndView {

    private ImageRet imageRet;

    public ImageView(BufferedImage buf) {
        this(buf, Type.JPG);
    }

    public ImageView(BufferedImage buf, Type type) {
        this.imageRet = new ImageRet(buf, type);
        super.setView(new ImageViewResolver());
    }

    public class ImageViewResolver extends JcAbstractViewResolver {

        @Override
        protected void makeResponse(HttpServletResponse response){
            try {
                OutputStream out = response.getOutputStream();
                ImageIO.write(imageRet.getImg(), imageRet.getType().getType(), out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String getContentSpType() {
            if (imageRet.getType() == null) {
                throw new RuntimeException("参数错误!");
            }
            String contentType = "";
            switch (imageRet.getType()) {
                case JPG:
                case JPEG:
                    contentType = "image/jpeg";
                    break;
                case PNG:
                    contentType = "image/png";
                    break;
                case BMP:
                    contentType = "image/bmp";
                    break;
                default:break;
            }
            return contentType;
        }
    }

    public static class ImageRet {

        private Type type;
        private BufferedImage img;

        public ImageRet(BufferedImage img, Type type) {
            this.img = img;
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        public BufferedImage getImg() {
            return img;
        }
    }

    public enum Type {
        /**
         * 图片类型
         */
        JPEG("jpeg"),
        JPG("jpg"),
        PNG("png"),
        BMP("bmp")
        ;
        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
