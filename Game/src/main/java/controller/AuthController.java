package controller;

import model.User;
import util.GetCode;

import java.util.ArrayList;
import java.util.Arrays;

public class AuthController {
    private static ArrayList<User> userList = new ArrayList<>();
    
    static {
        userList.add(new User("114514", "1919810"));
        userList.add(new User("1919810", "114514"));
    }
    
    /**
     * 验证用户登录
     * @param username 用户名
     * @param password 密码
     * @param captcha 验证码
     * @param actualCode 实际验证码
     * @return 错误信息，如果验证通过则返回null
     */
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
        if (!captcha.equalsIgnoreCase(actualCode)) {
            return "验证码错误";
        }

        // 遍历用户列表查找匹配的账号
        for (User user : userList) {
            if (username.equals(user.getUsername()) 
                && Arrays.equals(password, user.getPassword().toCharArray())) {
                return null; // 登录成功
            }
        }
        return "用户名或密码错误";
    }
    
    /**
     * 生成新的验证码
     * @return 新的验证码
     */
    public String generateCaptcha() {
        return GetCode.getCode();
    }
}