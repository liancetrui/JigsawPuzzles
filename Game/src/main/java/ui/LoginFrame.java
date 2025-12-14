package ui;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        //宽高
        setSize(488,430);
        //标题
        setTitle("拼图游戏 登录界面");
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
