package idv.tia201.g2.core.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class Core implements Serializable {
    @Serial
    private static final long serialVersionUID = 1457755989409740329L;
    private boolean successful;
    private String message;
    private Object data;
}
