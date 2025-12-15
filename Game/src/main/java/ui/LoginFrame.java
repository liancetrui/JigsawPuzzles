package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    //图片
    JLabel background = new JLabel(new ImageIcon("Game/src/main/resources/image/login/background.png"));
    JLabel userName = new JLabel(new ImageIcon("Game/src/main/resources/image/login/用户名.png"));
    JLabel password = new JLabel(new ImageIcon("Game/src/main/resources/image/login/密码.png"));
    JLabel captcha = new JLabel(new ImageIcon("Game/src/main/resources/image/login/验证码.png"));
    JButton loginButton = new JButton(new ImageIcon("Game/src/main/resources/image/login/登录按钮.png"));
    JButton registerButton = new JButton(new ImageIcon("Game/src/main/resources/image/login/注册按钮.png"));

    //输入框
    JTextField usernameText = new JTextField();
    JTextField passwordText = new JTextField();
    JTextField captchaText = new JTextField();

    public LoginFrame() {
        initJFrame();

        initView();

        //显示
        setVisible(true);
    }

    private void initView() {
        //添加用户名图片
        userName.setBounds(100, 150, 47, 17);
        getContentPane().add(userName);
        //添加用户名输入框
        usernameText.setBounds(150, 141, 200, 30);
        getContentPane().add(usernameText);
        //添加密码图片
        password.setBounds(110, 215, 32, 16);
        getContentPane().add(password);
        //添加密码输入框
        passwordText.setBounds(150, 211, 200, 30);
        getContentPane().add(passwordText);
        //添加验证码图片
        captcha.setBounds(100, 280, 56, 21);
        getContentPane().add(captcha);
        //添加验证码
        JButton codeJButton = new JButton();
        codeJButton.addActionListener(this);
        String code = CodeUtil.GetCode.getCode();
        codeJButton.setText(code);
        //去除按钮的默认边框
        codeJButton.setBorderPainted(false);
        //去除按钮的默认背景
        codeJButton.setContentAreaFilled(false);
        codeJButton.setBounds(300, 272, 50, 30);
        getContentPane().add(codeJButton);

        //添加验证码输入框
        captchaText.setBounds(180, 273, 100, 30);
        getContentPane().add(captchaText);
        //添加登录按钮
        loginButton.setBounds(120, 330, 128, 47);
        //去除按钮的默认边框
        loginButton.setBorderPainted(false);
        //去除按钮的默认背景
        loginButton.setContentAreaFilled(false);
        getContentPane().add(loginButton);
        //添加注册按钮
        registerButton.setBounds(250, 330, 128, 47);
        //去除按钮的默认边框
        registerButton.setBorderPainted(false);
        //去除按钮的默认背景
        registerButton.setContentAreaFilled(false);
        getContentPane().add(registerButton);

        initBackground();
    }

    private void initBackground() {
        background.setBounds(0, 0, 470, 390);
        getContentPane().add(background);
    }

    private void initJFrame() {
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
        //禁止改变大小
        setResizable(false);
        //取消内部默认布局
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CodeUtil.GetCode.getCode();
    }
}
