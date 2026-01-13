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
    // 拼图块尺寸
    private static final int SPLIT_SIZE = 105;
    // 完整图片尺寸
    private static final int FULL_SIZE = SPLIT_SIZE * 4;

    // 主方法
    public static void main(String[] args) throws IOException {
        splitImage("C:\\Users\\Narylr\\Pictures\\anan.jpg", "D:\\java\\JigsawPuzzles\\Game\\src\\main\\resources\\image\\person\\person1");
        splitImage("C:\\Users\\Narylr\\Pictures\\jiejie.jpg", "D:\\java\\JigsawPuzzles\\Game\\src\\main\\resources\\image\\person\\person2");
    }

    // 拆分图片
    public static void splitImage(String srcImagePath, String destImagePath) throws IOException {
        // 获取原图
        BufferedImage original = ImageIO.read(new File(srcImagePath));

        // 创建420x420的新图
        BufferedImage resized = new BufferedImage(FULL_SIZE, FULL_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resized.createGraphics();
        // 高质量渲染
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(original, 0, 0, FULL_SIZE, FULL_SIZE, null);
        graphics2D.dispose();
        // 创建目录
        new File(destImagePath).mkdirs();

        // 拆图片
        int num = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                BufferedImage piece = resized.getSubimage(
                        col * SPLIT_SIZE,
                        row * SPLIT_SIZE,
                        SPLIT_SIZE,
                        SPLIT_SIZE
                );
                writeHighQualityJPG(piece, new File(destImagePath + "/" + num + ".jpg"));
                num++;
            }
        }

        // 保存完整图片
        writeHighQualityJPG(resized, new File(destImagePath + "/all.jpg"));
        System.out.println("图片处理完毕");
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
