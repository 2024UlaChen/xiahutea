package idv.tia201.g2.web.member.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarrierUtil {
    public static final String carrierPattern = "^/[0-9A-Z]{3}-[0-9A-Z]{3}$";

    public static boolean checkCarrier(String carrier) {
        return Pattern.matches(carrierPattern, carrier);
    }
}
