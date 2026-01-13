package model;

import java.io.Serial;
import java.io.Serializable;

// 用户实体类
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2927038108774795688L;
    
    // 用户名
    private String username;
    // 密码
    private String password;

    // 无参构造
    public User() {
    }

    // 有参构造
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 获取用户名
    public String getUsername() {
        return username;
    }

    // 设置用户名
    public void setUsername(String username) {
        this.username = username;
    }

    // 获取密码
    public String getPassword() {
        return password;
    }

    // 设置密码
    public void setPassword(String password) {
        this.password = password;
    }

    // 转换为字符串格式
    @Override
    public String toString() {
        return "username" + "=" + username + "&" + "password" + "=" + password;
    }
}