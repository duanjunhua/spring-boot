package com.duanjh.module.pic_handler;

import org.apache.commons.lang3.ObjectUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

/**
 * @Author: Michale J H Duan[JunHua]
 * @Date: 2024-08-23 星期五 15:17
 * @Version: v1.0
 * @Description: 图片处理
 */
public class PictureUtils {

    //默认压缩的图片宽度
    public static Integer IMG_WIDTH = 200;
    //默认压缩的图片高度
    public static Integer IMG_HEIGHT = 80;

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\1.png");
        BufferedImage img = ImageIO.read(file);
        BufferedImage image = clearImageBackground(img, true, IMG_WIDTH, IMG_HEIGHT);
        File out = new File("C:\\Users\\Administrator\\Desktop\\alphaImage.png");
        ImageIO.write(image, "png", out);
    }

    /**
     * 去除图片背景色，图片透明化
     * @param image 原始图片
     * @param isCompress 是否压缩
     * @param imageWidth 压缩后的图片宽度: isCompress为true时不能为null
     * @param imageHeight 他所后的图片高度： isCompress为true时不能为null
     */
    public static BufferedImage clearImageBackground(BufferedImage image, boolean isCompress, Integer imageWidth, Integer imageHeight) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        try {
            image = transImageBackgroundColor(image, Color.BLACK, Color.WHITE);
            if(isCompress){
                image = resizeImage(image, imageWidth, imageHeight);
            }
            bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int width = image.getWidth();
            int height = image.getHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int rgba = image.getRGB(x, y);
                    if (isWhite(rgba)) {
                        bufferedImage.setRGB(x, y, 0x00ffff);
                    } else {
                        bufferedImage.setRGB(x, y, rgba);
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static boolean isWhite(int rgba) {
        int r = (rgba >> 16) & 0xFF;
        int g = (rgba >> 8) & 0xFF;
        int b = rgba & 0xFF;
        return r > 250 && g > 250 && b > 250;
    }

    /**
     * 压缩图片
     * @param image 原始图片
     * @param filterColor 过滤的颜色（如：文字黑色不替换，则filterColor为BLACK）
     * @param color 新的背景颜色
     */
    public static BufferedImage transImageBackgroundColor(BufferedImage image,  Color filterColor, Color color) {
        for (int x = image.getMinX(); x < image.getWidth(); x++) {
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                Color src = new Color(rgb);
                if(filterColor.equals(src)){
                    image.setRGB(x, y, src.getRGB());
                    continue;
                }
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    /**
     * 压缩图片
     * @param image 原始图片
     * @param newWidth 压缩宽度
     * @param newHeight 压缩高度
     */
    public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        Image imageSrc = image.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
        BufferedImage resizeImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = resizeImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(imageSrc, 0, 0,newWidth, newHeight, null);
        g2d.dispose();
        return resizeImage;
    }

    /**
     * 图片亮度调整
     * @param image 原始图片
     * @param contrast 对比度因子 (1.0为原始值)， 默认为2
     * @param brightness 亮度偏移量 (0为原始值)， 默认为15
     * @return
     */
    public static BufferedImage adjustBrightnessContrast(BufferedImage image, Float contrast, Float brightness) {

        if(ObjectUtils.isEmpty(contrast)) contrast = 2f;

        if(ObjectUtils.isEmpty(brightness)) brightness = 15f;

        // 创建RescaleOp对象来调整亮度和对比度
        RescaleOp rescaleOp = new RescaleOp(contrast, brightness, null);

        // 应用变换
        BufferedImage adjustedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        rescaleOp.filter(image, adjustedImage);

        return adjustedImage;
    }
}
