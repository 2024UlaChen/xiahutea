package idv.tia201.g2.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public  class ValidateUtil {

    //    carrier validate
    public static final String CARRIER_PATTERN = "^/[\\dA-Z0-9+-\\.]{7}$";

    public static final String CELLPHONE_PATTERN = "^09[0-9]{8}$";

    public static final String MEMBER_PWD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]{6,16}$";

    public static boolean checkCarrier(String carrier) {
        return Pattern.matches(CARRIER_PATTERN, carrier);
    }
    public static boolean checkCellphone(String phone) {
        return Pattern.matches(CELLPHONE_PATTERN, phone);
    }

}
