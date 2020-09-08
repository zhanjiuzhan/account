package org.account.cl.view.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ImageRes {
    public static final String JPG = "jpg";
    public static final String PNG = "png";

    private String type;
    private BufferedImage img;
    private static List<String> types = new ArrayList<>(2);

    static {
        types.add(JPG);
        types.add(PNG);
    }

    public ImageRes(BufferedImage img, String type) throws Exception {
        if (!types.contains(type)) {
            throw new Exception("图片类型错误");
        }
        this.img = img;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
