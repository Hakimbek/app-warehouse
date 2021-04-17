package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.util.Date;

@Data
public class OutputDto {
    private Date date;
    private Integer warehouseId;
    private Integer clientId;
    private Integer currencyId;
    private String factureNumber;
    private String code;
}
