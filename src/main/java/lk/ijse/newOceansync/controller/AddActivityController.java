package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.newOceansync.model.Activity;
import lk.ijse.newOceansync.repository.ActivityRepo;
import lk.ijse.newOceansync.repository.CustomerRepo;
import lk.ijse.newOceansync.util.Regex;
import lk.ijse.newOceansync.util.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


    public void initialize() throws SQLException, ClassNotFoundException {
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
        double cost;
        String activityId = lblActivityId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String location = txtLocation.getText();
        try {
            cost = Double.parseDouble(txtCost.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric value for cost").show();
            return;
        }
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
        if (isValid()){
            try {
                boolean isSaved = ActivityRepo.activitySave(activity);

                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION, "Activity saved!").show();
                    clearFields();
                    String message = String.format("Dear Valued Customer, we are pleased to announce a new activity at the Submarine Diving Center. Don't miss out on this exciting opportunity. Join us and experience it today!");
                    sendSmsToCustomers(message);
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save activity: " + e.getMessage()).show();
                throw new RuntimeException(e);
            }
        }

    }

    private boolean isValid() {
        if (!Regex.setTextColor(TextField.AMOUNT, txtCost)) return false;
        return true;
    }

    private void sendSmsToCustomers(String message) {
        try {
            List<String> customerPhoneNumbers = CustomerRepo.getAllCustomerPhoneNumbers();
            for (String phoneNumber : customerPhoneNumbers) {
                sendSms(phoneNumber, message);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to send SMS: " + e.getMessage()).show();
        }
    }

    private void sendSms(String phoneNumber, String message) {
        try {
            String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
            String urlString = String.format("https://raviyaapi.onrender.com/send_message?to=%s&msg=%s", phoneNumber, encodedMessage);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();
                System.out.println("SMS sent successfully: " + content.toString());
            } else {
                System.out.println("Failed to send SMS, response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void txtCostOnKeyRelease(KeyEvent keyEvent) {
    Regex.setTextColor(TextField.AMOUNT, txtCost);
    }

}
