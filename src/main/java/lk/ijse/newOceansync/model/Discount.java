package lk.ijse.newOceansync.model;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Discount {
    private String discountId;
    private String type;
    private double discount;
}
