package uz.pdp.appwarehouse.payload;

import lombok.Data;

@Data
public class CategoryDto {
    private String name;
    private boolean active;
    private Integer parentId;
}
