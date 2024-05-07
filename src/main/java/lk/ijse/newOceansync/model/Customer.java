package lk.ijse.newOceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class Customer {
    private String customerId;
    private String name;
    private String address;
    private String tel;
}
