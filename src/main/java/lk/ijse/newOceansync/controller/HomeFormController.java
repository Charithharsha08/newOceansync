package lk.ijse.newOceansync.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;

import static lk.ijse.newOceansync.controller.LoginFormController.credential;

public class HomeFormController {

    public Label lblDayStatus;
    @FXML
    private Label lblActivity;

    @FXML
    private Label lblCourceCount;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane sidePane;
    public void initialize() {
        setDayStatus();
    }

    private void setDayStatus() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String status;

        if (hour >= 5 && hour < 12) {
            status = "Morning ";
        } else if (hour >= 12 && hour < 18) {
            status = "Afternoon ";
        } else {
            status = "Evening ";
        }

        lblDayStatus.setText(status+credential[1]);
    }


}
