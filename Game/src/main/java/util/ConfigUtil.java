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
    // 运行目录
    private static final String RUN_DIR;
    // AppData目录
    private static final String APP_DATA_DIR;

    // 静态初始化 - 根据模式加载配置
    static {
        props = new Properties();
        
        // 先读取主配置获取模式
        String mode = "dev";
        try (InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                Properties mainProps = new Properties();
                mainProps.load(new InputStreamReader(is, StandardCharsets.UTF_8));
                mode = mainProps.getProperty("mode", "dev");
            }
        } catch (IOException e) {
            System.err.println("加载主配置失败: " + e.getMessage());
        }
        
        // 根据模式加载对应配置文件
        String configFile = "config-" + mode + ".properties";
        try (InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is != null) {
                props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
                System.out.println("[配置] 加载模式: " + mode);
            } else {
                System.err.println("配置文件不存在: " + configFile);
            }
        } catch (IOException e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
        }
        
        // 获取运行目录
        RUN_DIR = System.getProperty("user.dir");
        
        // 获取AppData目录
        String appData = System.getenv("APPDATA");
        if (appData != null) {
            APP_DATA_DIR = appData + File.separator + "JigsawPuzzles";
            // 确保目录存在
            new File(APP_DATA_DIR).mkdirs();
        } else {
            APP_DATA_DIR = RUN_DIR;
        }
    }

    // 获取配置值（解析占位符）
    public static String get(String key) {
        String value = props.getProperty(key, "");
        if (value.isEmpty()) {
            return "";
        }
        // 解析 ${APPDATA} 占位符
        if (value.startsWith("${APPDATA}")) {
            value = value.replace("${APPDATA}", APP_DATA_DIR);
            return new File(value).getAbsolutePath();
        }
        // 相对路径，拼接运行目录
        return new File(RUN_DIR, value).getAbsolutePath();
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
