package lk.ijse.newOceansync.model;

import java.sql.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Employee {

    private String id;
    private String employeeId;
    private String name;
    private String activity;
    private String month;
    private double salary;
    private Date date;
    private String userId;
}
