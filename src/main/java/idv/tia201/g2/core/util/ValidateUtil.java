package idv.tia201.g2.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ValidateUtil {

    //    carrier validate
    public static final String CARRIER_PATTERN = "^/[\\dA-Z0-9+-\\.]{7}$";

    public static boolean checkCarrier(String carrier) {
        return Pattern.matches(CARRIER_PATTERN, carrier);
    }

    public static void main(String[] args) {
        List<String> carreierList = Arrays.asList("/123-456", "/-------", "/+++++++", "/.......", "/AAAAAAA", "/1222222","aaa","/12345678");
        for(String carrier : carreierList) {
            if(!checkCarrier(carrier)){
                System.out.println(checkCarrier(carrier));
                System.out.println(carrier);
            }
        }
    }
}
