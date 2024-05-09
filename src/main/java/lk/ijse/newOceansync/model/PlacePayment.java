package lk.ijse.newOceansync.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class PlacePayment {

    private Payment payment;
    private List<PaymentDetail> paymentDetails;
    private List<SelectedActivity> selectedActivities;
    private List<SelectedCource> selectedCources;
    private List<SelectedStock> selectedStocks;
}
