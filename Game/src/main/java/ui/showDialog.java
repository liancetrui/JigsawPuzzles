package ui;

import javax.swing.*;

public class showDialog {
    public showDialog(String content) {
        JDialog jDialog = new JDialog();
        if (!jDialog.isVisible()) {
            //把弹框中原来的文字给清空掉。
            jDialog.getContentPane().removeAll();
            
            // 使用HTML格式支持自动换行，添加内边距
            JLabel jLabel = new JLabel("<html><div style='padding:20px;text-align:center;'>" + content + "</div></html>");
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setVerticalAlignment(SwingConstants.CENTER);
            
            jDialog.add(jLabel);
            
            // 自动调整弹框大小以适应内容
            jDialog.pack();
            
            // 设置最小宽度，避免太窄
            if (jDialog.getWidth() < 200) {
                jDialog.setSize(200, jDialog.getHeight());
            }
            
            //要把弹框在设置为顶层 -- 置顶效果
            jDialog.setAlwaysOnTop(true);
            //要让jDialog居中
            jDialog.setLocationRelativeTo(null);
            //弹框必须先关闭，才能关闭下面的界面
            jDialog.setModal(true);
            //让jDialog显示出来
            jDialog.setVisible(true);
        }
    }
}
