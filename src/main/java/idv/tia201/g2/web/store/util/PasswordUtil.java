package idv.tia201.g2.web.store.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Pattern;

public class PasswordUtil {

    //    確認密碼格式正確
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

    //產生亂數密碼
    public static String generateRandomString(Integer minlen, Integer maxlen) {
        boolean flag = false;
        String randomPassword = "";
        while (!flag) {
            randomPassword = RandomStringUtils.randomAlphanumeric(minlen, maxlen);
            flag = checkPassword(randomPassword);
        }
        return randomPassword;
    }
}
