package idv.tia201.g2.web.store.util;

import java.util.regex.Pattern;

public class PasswordUtil {
    public static boolean checkPassword(String password){
//        至少包含一個大寫字母。
//        至少包含一個小寫字母。
//        至少包含一個數字。
//        總長度在 9 到 14 個字符之間。
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{9,14}$";

        Pattern r = Pattern.compile(pattern);
        boolean passwordMatcher = Pattern.matches(pattern, password);
        return passwordMatcher;
    }
}
