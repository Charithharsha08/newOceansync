package lk.ijse.newOceansync.model.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data

public class ActivityTm {

    private String activityId;
    private String name;
    private String type;
    private String location;
    private double cost;
}
