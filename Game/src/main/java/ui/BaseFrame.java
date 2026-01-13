package ui;

import javax.swing.*;

// 窗口基类
public abstract class BaseFrame extends JFrame {

    // 初始化通用窗口配置
    protected void initJFrame(int width, int height, String title) {
        initJFrame(width, height, title, WindowConstants.EXIT_ON_CLOSE);
    }

    // 初始化窗口配置（可指定关闭模式）
    protected void initJFrame(int width, int height, String title, int closeOperation) {
        setSize(width, height);
        setTitle(title);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(closeOperation);
        setResizable(false);
        setLayout(null);
    }
}
