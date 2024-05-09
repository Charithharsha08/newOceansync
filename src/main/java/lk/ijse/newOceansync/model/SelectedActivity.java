package lk.ijse.newOceansync.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SelectedActivity {
    private String activityId;
    private String customerId;
    private Date date;

}