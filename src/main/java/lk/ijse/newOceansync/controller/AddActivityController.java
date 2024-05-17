package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.newOceansync.model.Activity;
import lk.ijse.newOceansync.repository.ActivityRepo;
import lk.ijse.newOceansync.repository.CustomerRepo;

import java.sql.SQLException;
import java.util.List;

public class AddActivityController {

    @FXML
    private Label lblActivityId;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtType;

//    private static final String ACCOUNT_SID = "AC3c1af771ad6b846145a1b66d0532d3c6";
//    private static final String AUTH_TOKEN = "d379722ce22e027b8b6c474cde7f7d4f";
//    private static final String TWILIO_PHONE_NUMBER = "+12077421415";

    public void initialize() throws SQLException, ClassNotFoundException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        loadNextActivityId();

    }

    private void loadNextActivityId() {
        String currentId = null;
        currentId = ActivityRepo.currentId();
        String nextId = nextId(currentId);
        lblActivityId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            // String[] split = currentId.split("O");
            String[] split = currentId.split("A");
            //  System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
//System.out.println("Arrays.toString(split) = " + split[0]);
            int id = Integer.parseInt(split[1]);    //2
            return "A" + ++id;

        }
        return "A1";

    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtName.clear();
        txtType.clear();
        txtLocation.clear();
        txtCost.clear();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String activityId = lblActivityId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());
        Activity activity = new Activity(activityId,name,type,location,cost);
        if (name.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter name").show();
            return;
        }if (type.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter type").show();
            return;
        }if (location.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter location").show();
            return;
        }if (txtCost.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter cost").show();
            return;
        }
        try {
            boolean isSaved = ActivityRepo.activitySave(activity);

            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Activity saved!").show();
                clearFields();
                sendSmsToCustomers("Dear Valued Customer, we are pleased to announce a new activity at the Submarine Diving Center. Don't miss out on this exciting opportunity. Join us and experience it today!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
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
    void txtLocationOnAction(ActionEvent event) {
        txtCost.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtType.requestFocus();

    }

    @FXML
    void txtTypeOnAction(ActionEvent event) {
    txtLocation.requestFocus();
    }

}
