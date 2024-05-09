package lk.ijse.newOceansync.model.tm;
import com.jfoenix.controls.JFXButton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentTm {
    private String id;
    private String name;
    private String description;
    private double unitPrice;
    private int qty;
    private double discount;
    private double total;
    private JFXButton btn;
}
