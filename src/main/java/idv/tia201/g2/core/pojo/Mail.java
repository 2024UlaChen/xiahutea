package idv.tia201.g2.core.pojo;

import lombok.Data;

@Data
public class Mail {
    private String recipient;
    private String subject;
    private String text;

}
