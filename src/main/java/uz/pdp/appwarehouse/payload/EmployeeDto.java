package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String code;
    private String password;
    private boolean active;
    private Set<Integer> warehouseId;
}
