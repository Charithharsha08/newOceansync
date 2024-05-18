package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.repository.CourceRepo;
import lk.ijse.newOceansync.repository.CustomerRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.sql.SQLException;
import java.util.List;

public class AddCourceController {

    @FXML
    private Label lblCourceId;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtName;
    private static final String ACCOUNT_SID = "AC3c1af771ad6b846145a1b66d0532d3c6";
    private static final String AUTH_TOKEN = "d379722ce22e027b8b6c474cde7f7d4f";
    private static final String TWILIO_PHONE_NUMBER = "+12077421415";

    
    public void initialize()  {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        loadNextCourceId();
    }

    private void loadNextCourceId() {
        String currentId = null;
        currentId = CourceRepo.currentId();
        String nextId = nextId(currentId);
        lblCourceId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");
            int id = Integer.parseInt(split[1]);    //2
            return "C" + ++id;

        }
        return "C1";
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtName.clear();
        txtDuration.clear();
        txtCost.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        double cost;
        String courceId = lblCourceId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        if (!isValid()) {


        try {
            cost = Double.parseDouble(txtCost.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric value for cost").show();
            return;
        }
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Name").show();
        }if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Duration").show();
        }if (txtCost.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Cost").show();
        }if(cost<0 ) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Cource Cost").show();
        }

        Cource cource = new Cource(courceId,name,duration,cost);

        try {
            boolean isSaved = CourceRepo.courceSave(cource);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                clearFields();
                loadNextCourceId();
                sendSmsToCustomers("Dear Valued Customer, we are pleased to announce the addition of a new course, \"" + name + "\", at the Submarine Diving Center. This course has a duration of \"" + duration + "\". We encourage you to join us for this exciting opportunity and experience it firsthand.");

            }else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

    private void sendSmsToCustomers(String message) {

        try {
            List<String> customerPhoneNumbers = CustomerRepo.getAllCustomerPhoneNumbers();
            for (String phoneNumber : customerPhoneNumbers) {
                Message.creator(
                        new com.twilio.type.PhoneNumber(phoneNumber),
                        new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                        message
                ).create();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send SMS: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtCostOnAction(ActionEvent event) {
        btnSaveOnAction(event);
    }

    @FXML
    void txtDurationAction(ActionEvent event) {
        txtCost.requestFocus();
    }

    @FXML
    void txtnameOnAction(ActionEvent event) {
        txtDuration.requestFocus();

    }

    public void txtCostReleaseOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.AMOUNT, txtCost);
    }

    public boolean isValid() {
        if (!Regex.setTextColor(TextField.AMOUNT, txtCost)) return false;
        return true;
    }
}
