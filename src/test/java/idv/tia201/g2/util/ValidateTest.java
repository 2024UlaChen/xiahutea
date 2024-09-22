package idv.tia201.g2.util;

import idv.tia201.g2.core.util.ValidateUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ValidateTest {
    @Test
    public void testCellphone() {
        List<String> cellphoneList = Arrays.asList("0123456789", "0912345678", "09123456789", "091234567a");
        for (String cellphone : cellphoneList) {
            if (!ValidateUtil.checkCellphone(cellphone)) {
                System.out.println(ValidateUtil.checkCellphone(cellphone));
                System.out.println(cellphone);
            }
        }
    }

    @Test
    public void testCarrier() {
        List<String> carreierList = Arrays.asList("/123-456", "/-------", "/+++++++", "/.......", "/AAAAAAA", "/1222222", "aaa", "/12345678");
        for (String carrier : carreierList) {
            if (!ValidateUtil.checkCarrier(carrier)) {
                System.out.println(ValidateUtil.checkCarrier(carrier));
                System.out.println(carrier);
            }
        }
    }
}
