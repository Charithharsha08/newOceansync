package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXTextField;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public void initialize() {
        loadNextCourceId();
    }

    private void loadNextCourceId() {
        String currentId = CourceRepo.currentId();
        String nextId = nextId(currentId);
        lblCourceId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");
            int id = Integer.parseInt(split[1]);
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
            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
            return;
        }

        try {
            cost = Double.parseDouble(txtCost.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric value for cost").show();
            return;
        }

        if (name.isEmpty() || duration.isEmpty() || txtCost.getText().isEmpty() || cost < 0) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields with valid values").show();
            return;
        }

        Cource cource = new Cource(courceId, name, duration, cost);

        try {
            boolean isSaved = CourceRepo.courceSave(cource);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                clearFields();
                loadNextCourceId();
                String message = String.format(
                        "Dear Valued Customer, we are pleased to announce the addition of a new course at the Submarine Diving Center. We encourage you to join us for this exciting opportunity and experience it firsthand."
                        );
                sendSmsToCustomers(message);
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    void txtDurationAction(ActionEvent event) {
        txtCost.requestFocus();
    }

    @FXML
    void txtnameOnAction(ActionEvent event) {
        txtDuration.requestFocus();
    }

    @FXML
    public void txtCostReleaseOnAction(KeyEvent keyEvent) {
        Regex.setTextColor(TextField.AMOUNT, txtCost);
    }

    public boolean isValid() {
        return Regex.setTextColor(TextField.AMOUNT, txtCost);
    }
}
