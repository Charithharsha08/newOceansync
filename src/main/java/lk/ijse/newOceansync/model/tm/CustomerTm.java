package lk.ijse.newOceansync.model.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class CustomerTm {

    private String customerId;
    private String name;
    private String address;
    private String tel;
}
