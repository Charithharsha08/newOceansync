package lk.ijse.newOceansync.model.tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class DiscountTm {
    private String discountId;
    private String Type;
    private double discount;
}
