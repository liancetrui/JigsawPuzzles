package ui;

import controller.AuthController;
import util.ResourcePathUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

// 登录界面
public class LoginFrame extends JFrame implements ActionListener, MouseListener {
    // 认证控制器
    private AuthController authController = new AuthController();

    // 验证码
    String code = authController.generateCaptcha();
    // 图片组件
    JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/login/background.png")));
    JLabel usernameImage = new JLabel(new ImageIcon(getResourceUrl("image/login/用户名.png")));
    JLabel passwordImage = new JLabel(new ImageIcon(getResourceUrl("image/login/密码.png")));
    JLabel captcha = new JLabel(new ImageIcon(getResourceUrl("image/login/验证码.png")));
    JButton codeJButton = new JButton();
    // 登录按钮
    ImageIcon loginButtonIcon = new ImageIcon(getResourceUrl("image/login/登录按钮.png"));
    ImageIcon loginButtonIconPressed = new ImageIcon(getResourceUrl("image/login/登录按下.png"));
    JButton loginButton = new JButton(loginButtonIcon);
    // 注册按钮
    ImageIcon registerButtonIcon = new ImageIcon(getResourceUrl("image/login/注册按钮.png"));
    ImageIcon registerButtonIconPressed = new ImageIcon(getResourceUrl("image/login/注册按下.png"));
    JButton registerButton = new JButton(registerButtonIcon);
    // 显示密码按钮
    ImageIcon showPasswordIcon = new ImageIcon(getResourceUrl("image/login/显示密码.png"));
    ImageIcon showPasswordPressedIcon = new ImageIcon(getResourceUrl("image/login/显示密码按下.png"));
    JButton showPasswordButton = new JButton(showPasswordIcon);

    // 密码是否可见
    boolean isPasswordVisible = false;

    // 输入框
    JTextField usernameImageText = new JTextField();
    JPasswordField passwordImageText = new JPasswordField();
    JTextField captchaText = new JTextField();

    // 构造方法
    public LoginFrame() {
        authController.loadUsers();
        initJFrame();
        initView();
        setVisible(true);
    }

    // 初始化窗口
    private void initJFrame() {
        setSize(488, 430);
        setTitle("拼图游戏 登录界面");
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    // 获取资源URL
    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }

    // 执行登录操作
    private void login() {
        String username = usernameImageText.getText();
        String errorMsg = authController.validateLogin(
                username,
                passwordImageText.getPassword(),
                captchaText.getText(),
                code
        );
        if (errorMsg == null) {
            dispose();  // 销毁登录窗口，释放资源
            new GameFrame(username);  // 传递用户名到游戏界面
        } else {
            new showDialog(errorMsg);
        }
    }

    // 初始化界面组件
    private void initView() {
        // 添加用户名图片
        usernameImage.setBounds(100, 150, 47, 17);
        getContentPane().add(usernameImage);

        // 添加用户名输入框
        usernameImageText.setBounds(150, 141, 200, 30);
        usernameImageText.enableInputMethods(false);
        getContentPane().add(usernameImageText);

        // 添加密码图片
        passwordImage.setBounds(110, 215, 32, 16);
        getContentPane().add(passwordImage);

        // 添加密码输入框
        passwordImageText.setBounds(150, 211, 200, 30);
        passwordImageText.setEchoChar('●');
        passwordImageText.enableInputMethods(false);
        getContentPane().add(passwordImageText);

        // 添加显示密码按钮
        showPasswordButton.setBounds(355, 218, 29, 18);
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.addActionListener(this);
        getContentPane().add(showPasswordButton);

        // 添加验证码图片
        captcha.setBounds(100, 280, 56, 21);
        getContentPane().add(captcha);

        // 添加验证码按钮
        codeJButton.setText(code);
        codeJButton.addActionListener(this);
        codeJButton.setBounds(280, 272, 100, 30);
        codeJButton.setBorderPainted(false);
        codeJButton.setContentAreaFilled(false);
        codeJButton.setFocusPainted(false);
        getContentPane().add(codeJButton);

        // 添加验证码输入框
        captchaText.setBounds(180, 273, 100, 30);
        captchaText.enableInputMethods(false);
        getContentPane().add(captchaText);

        // 添加登录按钮
        loginButton.setBounds(120, 330, 128, 47);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addMouseListener(this);
        getContentPane().add(loginButton);

        // 添加注册按钮
        registerButton.setBounds(250, 330, 127, 47);
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.addMouseListener(this);
        getContentPane().add(registerButton);

        // 添加背景
        background.setBounds(0, 0, 470, 390);
        getContentPane().add(background);

        getContentPane().repaint();
    }

    // 按钮点击事件
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == codeJButton) {
            // 刷新验证码
            code = authController.generateCaptcha();
            codeJButton.setText(code);
        } else if (source == showPasswordButton) {
            // 切换密码显示/隐藏
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                showPasswordButton.setIcon(showPasswordPressedIcon);
                showPasswordButton.setBounds(355, 217, 32, 21);

                passwordImageText.setEchoChar((char) 0);  // 显示明文
            } else {
                showPasswordButton.setIcon(showPasswordIcon);
                showPasswordButton.setBounds(355, 217, 29, 18);
                passwordImageText.setEchoChar('●');  // 隐藏密码
            }
        }
    }

    // 鼠标点击事件
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // 鼠标按下事件
    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == loginButton) {
            loginButton.setIcon(loginButtonIconPressed);
        } else if (source == registerButton) {
            registerButton.setIcon(registerButtonIconPressed);
            new RegisterFrame();
            dispose();
        }
    }

    // 鼠标释放事件
    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        if (source == loginButton) {
            loginButton.setIcon(loginButtonIcon);
            login();
            code = authController.generateCaptcha();
            codeJButton.setText(code);
        } else if (source == registerButton) {
            registerButton.setIcon(registerButtonIcon);
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
}