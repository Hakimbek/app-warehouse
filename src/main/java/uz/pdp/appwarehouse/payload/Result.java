package uz.pdp.appwarehouse.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String message;
    private boolean success;
    private Object object;
    private Integer id;

    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Result(String message, boolean success, Integer id) {
        this.message = message;
        this.success = success;
        this.id = id;
    }

    public Result(String message, boolean success, Object object) {
        this.message = message;
        this.success = success;
        this.object = object;
    }
}
