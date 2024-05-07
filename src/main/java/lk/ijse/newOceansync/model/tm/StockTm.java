package lk.ijse.newOceansync.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StockTm {
    private String stockId;
    private String name;
    private double price;
    private int qty;
    private String userId;
}
