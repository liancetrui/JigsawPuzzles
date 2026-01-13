package ui;

import javax.swing.*;

// 弹窗工具类
public class showDialog {
    // 构造方法，显示弹窗
    public showDialog(String content) {
        JDialog jDialog = new JDialog();
        if (!jDialog.isVisible()) {
            // 清空原有内容
            jDialog.getContentPane().removeAll();
            
            // 创建标签，使用HTML格式支持自动换行
            JLabel jLabel = new JLabel("<html><div style='padding:20px;text-align:center;'>" + content + "</div></html>");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setVerticalAlignment(SwingConstants.CENTER);
            
            jDialog.add(jLabel);
            
            // 自动调整弹框大小以适应内容
            jDialog.pack();
            
            // 设置最小宽度
            if (jDialog.getWidth() < 200) {
                jDialog.setSize(200, jDialog.getHeight());
            }
            
            // 置顶
            jDialog.setAlwaysOnTop(true);
            // 居中
            jDialog.setLocationRelativeTo(null);
            // 模态
            jDialog.setModal(true);
            // 显示
            jDialog.setVisible(true);
        }
    }
}
