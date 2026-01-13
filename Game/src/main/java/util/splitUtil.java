package util;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

// 图片拆分工具类
public class splitUtil {
    // 难度配置：格数 -> 块尺寸
    private static final int[][] DIFFICULTY_CONFIG = {
        {2, 210},  // 2x2 非常简单
        {3, 140},  // 3x3 轻松
        {4, 105},  // 4x4 难
        {5, 84}    // 5x5 非常困难
    };

    // 为所有难度切分图片
    public static void splitImageForAllDifficulties(String srcImagePath, String baseDestPath) throws IOException {
        for (int[] config : DIFFICULTY_CONFIG) {
            int gridSize = config[0];
            int pieceSize = config[1];
            // 目录格式：animal1/4x4/
            String destPath = baseDestPath + "/" + gridSize + "x" + gridSize;
            splitImage(srcImagePath, destPath, gridSize, pieceSize);
            System.out.println("完成 " + gridSize + "x" + gridSize + " 难度的图片切分");
        }
    }

    // 拆分图片（指定难度）
    public static void splitImage(String srcImagePath, String destImagePath, int gridSize, int pieceSize) throws IOException {
        int fullSize = pieceSize * gridSize;
        // 获取原图
        BufferedImage original = ImageIO.read(new File(srcImagePath));

        // 创建方形图片
        BufferedImage resized = new BufferedImage(fullSize, fullSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resized.createGraphics();
        // 高质量渲染
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(original, 0, 0, fullSize, fullSize, null);
        graphics2D.dispose();
        // 创建目录
        new File(destImagePath).mkdirs();

        // 拆图片
        int num = 1;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                BufferedImage piece = resized.getSubimage(
                        col * pieceSize,
                        row * pieceSize,
                        pieceSize,
                        pieceSize
                );
                writeHighQualityJPG(piece, new File(destImagePath + "/" + num + ".jpg"));
                num++;
            }
        }

        // 保存完整图片
        writeHighQualityJPG(resized, new File(destImagePath + "/all.jpg"));
    }

    // 高质量保存JPG
    private static void writeHighQualityJPG(BufferedImage image, File file) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.95f);

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(file)) {
            writer.setOutput(ios);
            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        }
        writer.dispose();
    }
}
