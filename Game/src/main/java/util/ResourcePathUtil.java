package util;

import java.net.URL;

/**
 * 资源路径工具类，使用反射获取资源文件路径
 */
public class ResourcePathUtil {

    /**
     * 通过类加载器获取资源的绝对路径
     *
     * @param resourcePath 相对于resources目录的路径
     * @return 资源的URL，如果找不到返回null
     */
    public static URL getResourceUrl(String resourcePath) {
        ClassLoader classLoader = ResourcePathUtil.class.getClassLoader();
        return classLoader.getResource(resourcePath);
    }

    /**
     * 通过类加载器获取资源的绝对路径字符串
     *
     * @param resourcePath 相对于resources目录的路径
     * @return 资源的绝对路径字符串，如果找不到返回null
     */
    public static String getResourcePath(String resourcePath) {
        URL url = getResourceUrl(resourcePath);
        return url != null ? url.getPath() : null;
    }
}