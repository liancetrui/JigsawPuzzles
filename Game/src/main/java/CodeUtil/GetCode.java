package CodeUtil;

public class GetCode {
    public static String getCode(){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int num = (int)(Math.random()*10);
            code.append(num);
        }
        return code.toString();
    }
}
