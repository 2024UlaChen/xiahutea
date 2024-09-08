package idv.tia201.g2.web.store.util;

import java.util.regex.Pattern;

public class Vatutil {
    public static final Pattern TWBID_PATTERN = Pattern.compile("^[0-9]{8}$");

    public static boolean isValidTWBID(String twbid) {
        boolean result = false;
        String weight = "12121241";
        boolean type2 = false; //第七個數是否為七
        if (TWBID_PATTERN.matcher(twbid).matches()) {
            int tmp = 0, sum = 0;
            for (int i = 0; i < 8; i++) {
                tmp = (twbid.charAt(i)-'0')*(weight.charAt(i)-'0');
                sum += (tmp/10) + (tmp % 10); //取出十位數和個位數相加
                if (i == 6 && twbid.charAt(i) == '7') {
                    type2 = true;
                }
            }
            if (type2) {
                if ((sum % 10) == 0 || ((sum + 1) % 10) == 0) { //如果第七位數為7
                    result = true;
                }
            } else {
                if ((sum % 10) == 0) {
                    result = true;
                }
            }
        }
        return result;
    }
}
