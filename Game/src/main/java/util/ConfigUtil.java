package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

// 配置工具类
public class ConfigUtil {
    // 配置对象
    private static final Properties props;
    // 项目根目录
    private static final String PROJECT_ROOT;

    // 静态初始化 - 从 classpath 加载
    static {
        props = new Properties();
        try (InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
            } else {
                System.err.println("配置文件不存在");
            }
        } catch (IOException e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
        }
        
        // 获取项目根目录
        String userDir = System.getProperty("user.dir");
        // 如果当前目录是 Game，向上一级
        if (userDir.endsWith("Game")) {
            PROJECT_ROOT = new File(userDir).getParent();
        } else {
            PROJECT_ROOT = userDir;
        }
    }

    // 获取配置值（自动拼接项目根目录）
    public static String get(String key) {
        String value = props.getProperty(key, "");
        if (value.isEmpty()) {
            return "";
        }
        return new File(PROJECT_ROOT, value).getAbsolutePath();
    }

    // 获取配置值（带默认值）
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // 用户信息文件路径
    public static String getUserInfoPath() {
        return get("data.userinfo");
    }

    // 存档目录
    public static String getSaveDir() {
        return get("data.save.dir");
    }

    // 资源图片目录
    public static String getImageDir() {
        return get("resources.image.dir");
    }
}
