package ui;

import javax.swing.*;

// 弹窗工具类
public class showDialog {
    // 显示文本弹窗
    public showDialog(String content) {
        this(null, content, null);
    }

    // 显示图片弹窗
    public showDialog(String title, JLabel imageLabel) {
        this(title, null, imageLabel);
    }

    // 通用构造方法
    private showDialog(String title, String content, JLabel imageLabel) {
        JDialog jDialog = new JDialog();
        jDialog.getContentPane().removeAll();

        if (imageLabel != null) {
            // 图片模式
            if (title != null) {
                jDialog.setTitle(title);
            }
            jDialog.add(imageLabel);
            jDialog.pack();
        } else {
            // 文本模式
            JLabel jLabel = new JLabel("<html><div style='padding:20px;text-align:center;'>" + content + "</div></html>");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setVerticalAlignment(SwingConstants.CENTER);
            jDialog.add(jLabel);
            jDialog.pack();
            if (jDialog.getWidth() < 200) {
                jDialog.setSize(200, jDialog.getHeight());
            }
        }

        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);
        jDialog.setVisible(true);
    }
}
