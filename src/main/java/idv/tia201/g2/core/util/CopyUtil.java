package idv.tia201.g2.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class CopyUtil {
    //    返回傳入對象 source 中所有值為 null 的屬性名稱
    public static String[] getNullPropertyNames (Object source) {
//        包裝傳入的對象，讓之後可以訪問該對象的屬性
        final BeanWrapper src = new BeanWrapperImpl(source);

//        獲取該對象的 PropertyDescriptor[]，每個 PropertyDescriptor代表一個屬性。
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

//        存儲所有屬性名稱
        Set<String> emptyNames = new HashSet<String>();

//        通過 src.getPropertyValue(pd.getName()) 獲取屬性值。
//        如果值為 null，則將該屬性名稱添加到 emptyNames 集合中
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

//        將 emptyNames 轉換成一個字符串數組並返回
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    //    將 src 對象的屬性複製到 target 對象，但忽略 src 對象中值為 null 的屬性。
    //    public static void copyProperties(Object source, Object target, String... ignoreProperties)
    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
