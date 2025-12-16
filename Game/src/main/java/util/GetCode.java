package util;

import java.util.Random;

public class GetCode {
    public static String getCode(){
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < 5; i++) {
            int type = random.nextInt(3); // 0:数字, 1:大写字母, 2:小写字母
            
            switch (type) {
                case 0 -> {
                    // 生成随机数字 0-9
                    int num = random.nextInt(10);
                    code.append(num);
                }
                case 1 -> {
                    // 生成随机大写字母 A-Z
                    char upperCase = (char) (random.nextInt(26) + 'A');
                    code.append(upperCase);
                }
                case 2 -> {
                    // 生成随机小写字母 a-z
                    char lowerCase = (char) (random.nextInt(26) + 'a');
                    code.append(lowerCase);
                }
            }
        }
        return code.toString();
    }
}