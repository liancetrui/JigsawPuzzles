package ui;

import controller.AuthController;
import util.ResourcePathUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

// 注册界面
public class RegisterFrame extends JFrame implements MouseListener {
    // 认证控制器
    private static AuthController authController = new AuthController();

    // 输入框
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField rePassword = new JTextField();

    // 按钮
    JButton submit = new JButton();
    JButton reset = new JButton();

    // 构造方法
    public RegisterFrame() {
        initFrame();
        initView();
        setVisible(true);
    }

    // 注册新用户
    private String registerNewUser() {
        // 检查用户名是否为空
        if (username.getText()
                .isEmpty()) {
            return "用户名不能为空";
        }
        // 检查用户名长度
        String usernameText = username.getText();
        if (usernameText.length() < 3 || usernameText.length() > 16) {
            return "用户名长度必须在3-16个字符之间";
        }
        // 检查用户名开头
        if (!Character.isLetter(usernameText.charAt(0))) {
            return "用户名必须以字母开头";
        }
        // 检查用户名格式
        if (!usernameText.matches("^[a-zA-Z0-9_]+$")) {
            return "用户名只能包含字母、数字、下划线";
        }
        // 检查密码是否为空
        if (password.getText()
                .isEmpty()) {
            return "密码不能为空";
        }
        // 检查密码长度
        if (password.getText()
                .length() < 8 || password.getText()
                .length() > 16) {
            return "密码长度必须在8-16位之间";
        }
        // 检查密码格式
        if (!password.getText()
                .matches(".*[a-zA-Z].*") || !password.getText()
                .matches(".*\\d.*")) {
            return "密码必须包含字母和数字";
        }
        // 检查两次密码是否一致
        if (!password.getText()
                .equals(rePassword.getText())) {
            return "两次输入的密码不一致";
        }
        // 检查用户名是否已存在
        if (authController.userExists(username.getText())) {
            return "用户名已存在";
        }
        // 注册用户
        authController.registerUser(username.getText(), rePassword.getText());
        return "注册成功";
    }

    // 获取资源URL
    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }

    // 鼠标点击事件
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == submit) {
            registerNewUser();
        }
    }

    // 鼠标按下事件
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == submit) {
            submit.setIcon(new ImageIcon(getResourceUrl("image/register/注册按下.png")));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon(getResourceUrl("image/register/重置按下.png")));
        }
    }

    // 鼠标释放事件
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

    // 鼠标进入事件
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    // 鼠标离开事件
    @Override
    public void mouseExited(MouseEvent e) {

    }

    // 初始化界面组件
    private void initView() {
        // 添加用户名标签
        JLabel usernameText = new JLabel(new ImageIcon(getResourceUrl("image/register/注册用户名.png")));
        usernameText.setBounds(85, 135, 80, 20);

        // 添加用户名输入框
        username.setBounds(195, 134, 200, 30);

        // 添加密码标签
        JLabel passwordText = new JLabel(new ImageIcon(getResourceUrl("image/register/注册密码.png")));
        passwordText.setBounds(97, 193, 70, 20);

        // 添加密码输入框
        password.setBounds(195, 195, 200, 30);

        // 添加确认密码标签
        JLabel rePasswordText = new JLabel(new ImageIcon(getResourceUrl("image/register/再次输入密码.png")));
        rePasswordText.setBounds(64, 255, 95, 20);

        // 添加确认密码输入框
        rePassword.setBounds(195, 255, 200, 30);

        // 注册按钮
        submit.setIcon(new ImageIcon(getResourceUrl("image/register/注册按钮.png")));
        submit.setBounds(123, 310, 128, 47);
        submit.setBorderPainted(false);
        submit.setContentAreaFilled(false);
        submit.addMouseListener(this);

        // 重置按钮
        reset.setIcon(new ImageIcon(getResourceUrl("image/register/重置按钮.png")));
        reset.setBounds(256, 310, 128, 47);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);

        // 禁止中文输入
        username.enableInputMethods(false);
        password.enableInputMethods(false);
        rePassword.enableInputMethods(false);

        // 禁止复制粘贴
        username.setTransferHandler(null);
        password.setTransferHandler(null);
        rePassword.setTransferHandler(null);

        // 背景图片
        JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/register/background.png")));
        background.setBounds(0, 0, 470, 390);

        // 添加组件
        this.getContentPane().add(usernameText);
        this.getContentPane().add(passwordText);
        this.getContentPane().add(rePasswordText);
        this.getContentPane().add(username);
        this.getContentPane().add(password);
        this.getContentPane().add(rePassword);
        this.getContentPane().add(submit);
        this.getContentPane().add(reset);
        this.getContentPane().add(background);
    }

    // 初始化窗口
    private void initFrame() {
        setSize(488, 430);
        setTitle("拼图游戏 V1.0注册");
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // 窗口关闭监听
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new LoginFrame();
            }
        });
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }
}
