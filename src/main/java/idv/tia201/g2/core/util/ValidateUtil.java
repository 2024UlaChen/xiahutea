package idv.tia201.g2.core.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ValidateUtil {

    //    carrier validate
    public static final String CARRIER_PATTERN = "^/[\\dA-Z0-9+-\\.]{7}$";

    public static final String CELLPHONE_PATTERN = "^09[0-9]{8}$";

    public static final String MEMBER_PWD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{6,16}$";

    public static final Pattern TWBID_PATTERN = Pattern.compile("^[0-9]{8}$");

    public static boolean checkCarrier(String carrier) {
        return Pattern.matches(CARRIER_PATTERN, carrier);
    }

    public static boolean checkCellphone(String phone) {
        return Pattern.matches(CELLPHONE_PATTERN, phone);
    }

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


    public static boolean checkMemberPwd(String memberPwd) {
        return Pattern.matches(MEMBER_PWD_PATTERN, memberPwd);
    }


}
