package util;

import java.util.Random;

// 验证码生成工具类
public class GetCodeUtil {
    // 生成随机验证码
    public static String getCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        // 生成5位随机字符
        for (int i = 0; i < 5; i++) {
            int type = random.nextInt(3);

            switch (type) {
                case 0 -> {
                    // 生成随机数字
                    int num = random.nextInt(10);
                    code.append(num);
                }
                case 1 -> {
                    // 生成随机大写字母
                    char upperCase = (char) (random.nextInt(26) + 'A');
                    code.append(upperCase);
                }
                case 2 -> {
                    // 生成随机小写字母
                    char lowerCase = (char) (random.nextInt(26) + 'a');
                    code.append(lowerCase);
                }
            }
        }
        return code.toString();
    }
}