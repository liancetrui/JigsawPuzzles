package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// 图片切割工具界面
public class SplitToolUI extends JFrame {
    // 源图片路径
    private JTextField srcPathField;
    // 输出目录
    private JTextField destPathField;
    // 输出文件夹名
    private JTextField folderNameField;
    // 难度复选框
    private JCheckBox check2x2, check3x3, check4x4, check5x5;
    // 预览标签
    private JLabel previewLabel;
    // 状态标签
    private JLabel statusLabel;
    // 源图片
    private File srcFile;

    // 主方法
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplitToolUI::new);
    }

    // 构造方法
    public SplitToolUI() {
        initUI();
        setVisible(true);
    }

    // 初始化界面
    private void initUI() {
        setTitle("拼图图片切割工具");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // 源图片选择
        JLabel srcLabel = new JLabel("源图片:");
        srcLabel.setBounds(20, 20, 60, 25);
        add(srcLabel);

        srcPathField = new JTextField();
        srcPathField.setBounds(80, 20, 400, 25);
        // 回车确认路径
        srcPathField.addActionListener(e -> updateSrcFromInput());
        add(srcPathField);

        JButton srcBtn = new JButton("选择");
        srcBtn.setBounds(490, 20, 80, 25);
        srcBtn.addActionListener(e -> selectSourceImage());
        add(srcBtn);

        // 输出目录
        JLabel destLabel = new JLabel("输出目录:");
        destLabel.setBounds(20, 55, 60, 25);
        add(destLabel);

        destPathField = new JTextField("D:\\java\\JigsawPuzzles\\Game\\src\\main\\resources\\image");
        destPathField.setBounds(80, 55, 400, 25);
        destPathField.setEditable(false);
        add(destPathField);

        JButton destBtn = new JButton("选择");
        destBtn.setBounds(490, 55, 80, 25);
        destBtn.addActionListener(e -> selectDestDir());
        add(destBtn);

        // 文件夹名
        JLabel folderLabel = new JLabel("文件夹名:");
        folderLabel.setBounds(20, 90, 60, 25);
        add(folderLabel);

        folderNameField = new JTextField("animal/animal1");
        folderNameField.setBounds(80, 90, 200, 25);
        add(folderNameField);

        JLabel tipLabel = new JLabel("(如: animal/animal1, girl/girl5)");
        tipLabel.setBounds(290, 90, 200, 25);
        tipLabel.setForeground(Color.GRAY);
        add(tipLabel);

        // 难度选择
        JLabel diffLabel = new JLabel("难度选择:");
        diffLabel.setBounds(20, 125, 60, 25);
        add(diffLabel);

        check2x2 = new JCheckBox("2x2 非常简单", true);
        check2x2.setBounds(80, 125, 120, 25);
        add(check2x2);

        check3x3 = new JCheckBox("3x3 轻松", true);
        check3x3.setBounds(200, 125, 100, 25);
        add(check3x3);

        check4x4 = new JCheckBox("4x4 难", true);
        check4x4.setBounds(300, 125, 80, 25);
        add(check4x4);

        check5x5 = new JCheckBox("5x5 非常困难", true);
        check5x5.setBounds(380, 125, 120, 25);
        add(check5x5);

        // 全选/取消全选
        JButton selectAllBtn = new JButton("全选");
        selectAllBtn.setBounds(510, 125, 60, 25);
        selectAllBtn.addActionListener(e -> {
            boolean all = !check2x2.isSelected() || !check3x3.isSelected() || !check4x4.isSelected() || !check5x5.isSelected();
            check2x2.setSelected(all);
            check3x3.setSelected(all);
            check4x4.setSelected(all);
            check5x5.setSelected(all);
        });
        add(selectAllBtn);

        // 预览区域
        JLabel previewTitle = new JLabel("预览:");
        previewTitle.setBounds(20, 160, 60, 25);
        add(previewTitle);

        previewLabel = new JLabel();
        previewLabel.setBounds(80, 160, 200, 200);
        previewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewLabel.setText("未选择图片");
        add(previewLabel);

        // 切割按钮
        JButton splitBtn = new JButton("开始切割");
        splitBtn.setBounds(320, 220, 120, 40);
        splitBtn.setFont(new Font("微软雅黑", Font.BOLD, 14));
        splitBtn.addActionListener(e -> doSplit());
        add(splitBtn);

        // 状态显示
        statusLabel = new JLabel("就绪");
        statusLabel.setBounds(20, 380, 560, 60);
        statusLabel.setVerticalAlignment(SwingConstants.TOP);
        add(statusLabel);
    }

    // 选择源图片
    private void selectSourceImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("图片文件", "jpg", "jpeg", "png", "bmp"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            srcFile = chooser.getSelectedFile();
            srcPathField.setText(srcFile.getAbsolutePath());
            showPreview();
        }
    }

    // 从输入框更新源图片
    private void updateSrcFromInput() {
        String path = srcPathField.getText().trim();
        if (path.isEmpty()) return;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            srcFile = file;
            showPreview();
        } else {
            showStatus("错误: 图片路径无效", true);
        }
    }

    // 选择输出目录
    private void selectDestDir() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            destPathField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    // 显示预览
    private void showPreview() {
        if (srcFile == null || !srcFile.exists()) return;
        try {
            BufferedImage img = ImageIO.read(srcFile);
            // 缩放到预览尺寸
            Image scaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            previewLabel.setIcon(new ImageIcon(scaled));
            previewLabel.setText("");
        } catch (Exception e) {
            previewLabel.setIcon(null);
            previewLabel.setText("加载失败");
        }
    }

    // 执行切割
    private void doSplit() {
        // 验证
        if (srcFile == null || !srcFile.exists()) {
            showStatus("错误: 请选择源图片", true);
            return;
        }
        String folderName = folderNameField.getText().trim();
        if (folderName.isEmpty()) {
            showStatus("错误: 请输入文件夹名", true);
            return;
        }
        if (!check2x2.isSelected() && !check3x3.isSelected() && !check4x4.isSelected() && !check5x5.isSelected()) {
            showStatus("错误: 请至少选择一个难度", true);
            return;
        }

        // 构建输出路径
        String basePath = destPathField.getText() + File.separator + folderName.replace("/", File.separator);
        StringBuilder result = new StringBuilder();

        try {
            // 切割选中的难度
            if (check2x2.isSelected()) {
                splitUtil.splitImage(srcFile.getAbsolutePath(), basePath + File.separator + "2x2", 2, 210);
                result.append("✓ 2x2 完成\n");
            }
            if (check3x3.isSelected()) {
                splitUtil.splitImage(srcFile.getAbsolutePath(), basePath + File.separator + "3x3", 3, 140);
                result.append("✓ 3x3 完成\n");
            }
            if (check4x4.isSelected()) {
                splitUtil.splitImage(srcFile.getAbsolutePath(), basePath + File.separator + "4x4", 4, 105);
                result.append("✓ 4x4 完成\n");
            }
            if (check5x5.isSelected()) {
                splitUtil.splitImage(srcFile.getAbsolutePath(), basePath + File.separator + "5x5", 5, 84);
                result.append("✓ 5x5 完成\n");
            }
            result.append("输出目录: ").append(basePath);
            showStatus(result.toString(), false);
        } catch (Exception e) {
            showStatus("切割失败: " + e.getMessage(), true);
        }
    }

    // 显示状态
    private void showStatus(String msg, boolean isError) {
        statusLabel.setText("<html>" + msg.replace("\n", "<br>") + "</html>");
        statusLabel.setForeground(isError ? Color.RED : new Color(0, 128, 0));
    }
}
