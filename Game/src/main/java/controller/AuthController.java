package controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import model.User;
import util.ConfigUtil;
import util.GetCode;

import java.io.File;
import java.util.ArrayList;

// 用户认证控制器
public class AuthController {
    // 用户列表
    private static final ArrayList<User> userList = new ArrayList<>();

    // 从文件加载用户数据
    public void loadUsers() {
        String userInfoPath = ConfigUtil.getUserInfoPath();
        // JSON格式
        if (FileUtil.isEmpty(new File(userInfoPath))) {
            FileUtil.touch(userInfoPath);
        }
        String json = FileUtil.readUtf8String(userInfoPath);

        if (StrUtil.isNotBlank(json)) {
            userList.addAll(JSONUtil.toList(json, User.class));
        }
    }

    // 验证登录
    public String validateLogin(String username, char[] password, String captcha, String actualCode) {
        // 检查用户名是否为空
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }

        // 检查密码是否为空
        if (password == null || password.length == 0) {
            return "密码不能为空";
        }

        // 检查验证码是否为空
        if (captcha == null || captcha.trim().isEmpty()) {
            return "验证码不能为空";
        }

        // 验证码校验
        if (!captcha.equals(actualCode)) {
            return "验证码错误";
        }

        // 加密输入的密码
        String encryptedPassword = SecureUtil.md5(new String(password));

        // 遍历用户列表查找匹配的账号
        for (User user : userList) {
            if (username.equals(user.getUsername()) && encryptedPassword.equals(user.getPassword())) {
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
        // 加密密码
        String encryptedPassword = SecureUtil.md5(passWord);
        User user = new User(userName, encryptedPassword);
        userList.add(user);
        // 保存为JSON格式
        FileUtil.writeUtf8String(
            JSONUtil.toJsonPrettyStr(userList),
            ConfigUtil.getUserInfoPath()
        );
    }
}