package ui;

import controller.AuthController;
import model.User;
import util.ResourcePathUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class RegisterFrame extends JFrame implements MouseListener {
    private static AuthController authController = new AuthController();

    //提升三个输入框的变量的作用范围，让这三个变量可以在本类中所有方法里面可以使用。
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField rePassword = new JTextField();

    //提升两个按钮变量的作用范围，让这两个变量可以在本类中所有方法里面可以使用。
    JButton submit = new JButton();
    JButton reset = new JButton();


    public RegisterFrame() {
        initFrame();
        initView();
        setVisible(true);
    }

    private String registerNewUser() {
        if (username.getText()
                .isEmpty()) {
            return "用户名不能为空";
        }
        //用户名可以是字母、数字、下划线，长度3-16 个字符，必须以字母开头（不能数字或下划线开头）
        String usernameText = username.getText();
        if (usernameText.length() < 3 || usernameText.length() > 16) {
            return "用户名长度必须在3-16个字符之间";
        }
        if (!Character.isLetter(usernameText.charAt(0))) {
            return "用户名必须以字母开头";
        }
        if (!usernameText.matches("^[a-zA-Z0-9_]+$")) {
            return "用户名只能包含字母、数字、下划线";
        }
        if (password.getText()
                .isEmpty()) {
            return "密码不能为空";
        }
        // 8-16位，必须包含字母和数字
        if (password.getText()
                .length() < 8 || password.getText()
                .length() > 16) {
            return "密码长度必须在8-16位之间";
        }
        if (!password.getText()
                .matches(".*[a-zA-Z].*") || !password.getText()
                .matches(".*\\d.*")) {
            return "密码必须包含字母和数字";
        }
        if (!password.getText()
                .equals(rePassword.getText())) {
            return "两次输入的密码不一致";
        }
        //判断用户名是否已经存在
        if (authController.userExists(username.getText())) {
            return "用户名已存在";
        }
        authController.registerUser(username.getText(), rePassword.getText());
        return "注册成功";
    }

    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == submit) {
            registerNewUser();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon(getResourceUrl("image/register/注册按下.png")));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon(getResourceUrl("image/register/重置按下.png")));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon(getResourceUrl("image/register/注册按钮.png")));
            String result = registerNewUser();
            new showDialog(result);
            if (result.equals("注册成功")) {
                new LoginFrame();
                dispose();
            }
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon(getResourceUrl("image/register/重置按钮.png")));
            new showDialog("重置所有内容");
            username.setText("");
            password.setText("");
            rePassword.setText("");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void initView() {
        //添加注册用户名的文本
        JLabel usernameText = new JLabel(new ImageIcon(getResourceUrl("image/register/注册用户名.png")));
        usernameText.setBounds(85, 135, 80, 20);

        //添加注册用户名的输入框
        username.setBounds(195, 134, 200, 30);

        //添加注册密码的文本
        JLabel passwordText = new JLabel(new ImageIcon(getResourceUrl("image/register/注册密码.png")));
        passwordText.setBounds(97, 193, 70, 20);

        //添加密码输入框
        password.setBounds(195, 195, 200, 30);

        //添加再次输入密码的文本
        JLabel rePasswordText = new JLabel(new ImageIcon(getResourceUrl("image/register/再次输入密码.png")));
        rePasswordText.setBounds(64, 255, 95, 20);

        //添加再次输入密码的输入框
        rePassword.setBounds(195, 255, 200, 30);

        //注册的按钮
        submit.setIcon(new ImageIcon(getResourceUrl("image/register/注册按钮.png")));
        submit.setBounds(123, 310, 128, 47);
        submit.setBorderPainted(false);
        submit.setContentAreaFilled(false);
        submit.addMouseListener(this);

        //重置的按钮
        reset.setIcon(new ImageIcon(getResourceUrl("image/register/重置按钮.png")));
        reset.setBounds(256, 310, 128, 47);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);

        //禁止中文输入
        username.enableInputMethods(false);
        password.enableInputMethods(false);
        rePassword.enableInputMethods(false);

        //禁止复制粘贴
        username.setTransferHandler(null);
        password.setTransferHandler(null);
        rePassword.setTransferHandler(null);

        //背景图片
        JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/register/background.png")));
        background.setBounds(0, 0, 470, 390);

        this.getContentPane()
                .add(usernameText);
        this.getContentPane()
                .add(passwordText);
        this.getContentPane()
                .add(rePasswordText);
        this.getContentPane()
                .add(username);
        this.getContentPane()
                .add(password);
        this.getContentPane()
                .add(rePassword);
        this.getContentPane()
                .add(submit);
        this.getContentPane()
                .add(reset);
        this.getContentPane()
                .add(background);
    }

    private void initFrame() {
        //设置宽高
        setSize(488, 430);
        //设置标题
        setTitle("拼图游戏 V1.0注册");
        //取消内部默认布局
        setLayout(null);
        //设置关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置居中
        setLocationRelativeTo(null);
        //设置置顶
        setAlwaysOnTop(true);
    }
}
