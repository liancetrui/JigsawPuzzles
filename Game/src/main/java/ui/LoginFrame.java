package ui;

import controller.AuthController;
import util.ResourcePathUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class LoginFrame extends JFrame implements ActionListener, MouseListener {
    private AuthController authController = new AuthController();
    
    String code = authController.generateCaptcha();
    //图片
    JLabel background = new JLabel(new ImageIcon(getResourceUrl("image/login/background.png")));
    JLabel usernameImage = new JLabel(new ImageIcon(getResourceUrl("image/login/用户名.png")));
    JLabel passwordImage = new JLabel(new ImageIcon(getResourceUrl("image/login/密码.png")));
    //验证码
    JLabel captcha = new JLabel(new ImageIcon(getResourceUrl("image/login/验证码.png")));
    JButton codeJButton = new JButton();
    //登录
    ImageIcon loginButtonIcon = new ImageIcon(getResourceUrl("image/login/登录按钮.png"));
    ImageIcon loginButtonIconPressed = new ImageIcon(getResourceUrl("image/login/登录按下.png"));
    JButton loginButton = new JButton(loginButtonIcon);
    //注册
    ImageIcon registerButtonIcon = new ImageIcon(getResourceUrl("image/login/注册按钮.png"));
    ImageIcon registerButtonIconPressed = new ImageIcon(getResourceUrl("image/login/注册按下.png"));
    JButton registerButton = new JButton(registerButtonIcon);
    
    // 显示密码按钮
    ImageIcon showPasswordIcon = new ImageIcon(getResourceUrl("image/login/显示密码.png"));
    ImageIcon showPasswordPressedIcon = new ImageIcon(getResourceUrl("image/login/显示密码按下.png"));
    JButton showPasswordButton = new JButton(showPasswordIcon);



    boolean isPasswordVisible = false;  // 密码是否可见


    //输入框
    JTextField usernameImageText = new JTextField();
    JPasswordField passwordImageText = new JPasswordField();
    JTextField captchaText = new JTextField();

    public LoginFrame() {
        initJFrame();

        initView();

        //显示
        setVisible(true);
    }

    /**
     * 获取资源URL
     * @param path 资源路径
     * @return URL对象
     */
    private URL getResourceUrl(String path) {
        return ResourcePathUtil.getResourceUrl(path);
    }

    /**
     * 执行登录操作
     */
    private void login() {
        String errorMsg = authController.validateLogin(
            usernameImageText.getText(),
            passwordImageText.getPassword(),
            captchaText.getText(),
            code
        );
        if (errorMsg == null) {
            dispose();  // 销毁登录窗口，释放资源
            new GameFrame();
        } else {
            showJDialog(errorMsg);
        }
    }

    private void initView() {
        //添加用户名图片
        usernameImage.setBounds(100, 150, 47, 17);
        getContentPane().add(usernameImage);

        //添加用户名输入框
        usernameImageText.setBounds(150, 141, 200, 30);
        getContentPane().add(usernameImageText);

        //添加密码图片
        passwordImage.setBounds(110, 215, 32, 16);
        getContentPane().add(passwordImage);

        //添加密码输入框
        passwordImageText.setBounds(150, 211, 200, 30);
        passwordImageText.setEchoChar('●');
        getContentPane().add(passwordImageText);
        
        // 添加显示密码按钮
        showPasswordButton.setBounds(355, 218, 29, 18);
        showPasswordButton.setBorderPainted(false);
        showPasswordButton.setContentAreaFilled(false);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.addActionListener(this);
        getContentPane().add(showPasswordButton);

        //添加验证码图片
        captcha.setBounds(100, 280, 56, 21);
        getContentPane().add(captcha);

        //添加验证码
        codeJButton.setText(code);
        codeJButton.addActionListener(this);
        codeJButton.setBounds(280, 272, 100, 30);
        //去除按钮的默认边框
        codeJButton.setBorderPainted(false);
        //去除按钮的默认背景
        codeJButton.setContentAreaFilled(false);
        //去除按钮的焦点
        codeJButton.setFocusPainted(false);
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
        loginButton.addMouseListener(this);
        getContentPane().add(loginButton);

        //添加注册按钮
        registerButton.setBounds(250, 330, 127, 47);
        //去除按钮的默认边框
        registerButton.setBorderPainted(false);
        //去除按钮的默认背景
        registerButton.setContentAreaFilled(false);
        registerButton.addMouseListener(this);
        getContentPane().add(registerButton);

        background.setBounds(0, 0, 470, 390);
        getContentPane().add(background);

        getContentPane().repaint();
    }

    private void initJFrame() {
        //宽高
        setSize(488, 430);
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

    //要展示用户名或密码错误
    public void showJDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);

        //创建Jlabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //让弹框展示出来
        jDialog.setVisible(true);
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == loginButton) {
            loginButton.setIcon(loginButtonIconPressed);
        } else if (source == registerButton) {
            registerButton.setIcon(registerButtonIconPressed);
        }
    }

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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}