package controller;

import cn.hutool.core.io.FileUtil;
import model.User;
import util.GetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 用户认证控制器
public class AuthController {
    // 用户列表
    private static final ArrayList<User> userList = new ArrayList<>();

    // 从文件加载用户数据
    public void loadUsers() {
        List<String> userInfoList = FileUtil.readUtf8Lines("D:\\java\\JigsawPuzzles\\Game\\src\\main\\data\\userinfo.txt");
        for (String s : userInfoList) {
            String[] split = s.split("&");
            String username = split[0].split("=")[1];
            String password = split[1].split("=")[1];
            User user = new User(username, password);
            userList.add(user);
        }
    }

    // 验证登录
    public String validateLogin(String username, char[] password, String captcha, String actualCode) {
        // 检查用户名是否为空
        if (username == null || username.trim()
                .isEmpty()) {
            return "用户名不能为空";
        }

        // 检查密码是否为空
        if (password == null || password.length == 0) {
            return "密码不能为空";
        }

        // 检查验证码是否为空
        if (captcha == null || captcha.trim()
                .isEmpty()) {
            return "验证码不能为空";
        }

        // 验证码校验
        if (!captcha.equals(actualCode)) {
            return "验证码错误";
        }

        // 遍历用户列表查找匹配的账号
        for (User user : userList) {
            if (username.equals(user.getUsername())
                    && Arrays.equals(password, user.getPassword()
                    .toCharArray())) {
                return null; // 登录成功
            }
        }
        return "用户名或密码错误";
    }

    // 生成验证码
    public String generateCaptcha() {
        return GetCode.getCode();
    }

    // 检查用户名是否已存在
    public boolean userExists(String usernameText) {
        for (User user : userList) {
            if (usernameText.equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    // 注册新用户
    public void registerUser(String userName, String passWord) {
        User user = new User(userName, passWord);
        userList.add(user);
        FileUtil.appendUtf8Lines(List.of(user.toString()), "D:\\java\\JigsawPuzzles\\Game\\src\\main\\data\\userinfo.txt");
    }
}