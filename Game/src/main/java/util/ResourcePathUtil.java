package util;

import java.net.URL;

// 资源路径工具类
public class ResourcePathUtil {

    // 通过类加载器获取资源URL
    public static URL getResourceUrl(String resourcePath) {
        ClassLoader classLoader = ResourcePathUtil.class.getClassLoader();
        return classLoader.getResource(resourcePath);
    }

    // 通过类加载器获取资源的绝对路径字符串
    public static String getResourcePath(String resourcePath) {
        URL url = getResourceUrl(resourcePath);
        return url != null ? url.getPath() : null;
    }
}