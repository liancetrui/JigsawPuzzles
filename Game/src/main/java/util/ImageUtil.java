package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 图片工具类
 */
public class ImageUtil {

    /**
     * 创建指定尺寸的纯色图片
     *
     * @param width  宽度
     * @param height 高度
     * @param color  颜色
     * @return ImageIcon对象
     */
    public static ImageIcon createColorImage(int width, int height, Color color) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 填充背景色
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();

        return new ImageIcon(image);
    }

    /**
     * 创建默认的白色拼图图片(105*105像素)
     *
     * @return ImageIcon对象
     */
    public static ImageIcon createDefaultPuzzleImage() {
        return createColorImage(105, 105, Color.WHITE);
    }
}