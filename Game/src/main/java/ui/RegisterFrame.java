package ui;

import javax.swing.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame() {
        initJFrame();
        //TODO 完成注册界面
    }

    private void initJFrame() {
        //宽高
        setSize(488,500);
        //标题
        setTitle("拼图游戏 注册界面");
        //置顶
        setAlwaysOnTop(true);
        //居中
        setLocationRelativeTo(null);
        //关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //显示
        setVisible(true);
    }
}