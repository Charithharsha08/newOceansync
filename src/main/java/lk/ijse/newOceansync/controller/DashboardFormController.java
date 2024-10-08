package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {

    public JFXButton btnHome;
    public JFXButton btnStock;
    public JFXButton btnEmployee;
    public JFXButton btnCustomer;
    public JFXButton btnActivity;
    public JFXButton btnCource;
    public JFXButton btnPayment;
    public JFXButton btnDiscount;
    public AnchorPane sideNode;
    @FXML
    private AnchorPane centerNode;

    @FXML
    private Pane innerPane;

    private JFXButton selectedButton;

    public void initialize(){
        loadHomeForm();

    }

    private void loadHomeForm()  {
        handleSelection(btnHome);
        AnchorPane homePane = null;
        try {
            homePane = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(homePane);
        animatePane(homePane); // Call the animation method

    }



    @FXML
    void btnActivityOnAction(ActionEvent event) {
        handleSelection(btnActivity);
        AnchorPane activityPane = null;
        try {
            activityPane = FXMLLoader.load(this.getClass().getResource("/view/activity_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(activityPane);
        animatePane(activityPane); // Call the animation method


    }

    @FXML
    void btnCourceOnAction(ActionEvent event) {
        handleSelection(btnCource);
        AnchorPane courcePane = null;
        try {
            courcePane = FXMLLoader.load(this.getClass().getResource("/view/cource_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(courcePane);
        animatePane(courcePane); // Call the animation method

    }


    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        handleSelection(btnCustomer);
        AnchorPane customerPane = null;
        try {
            customerPane = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(customerPane);
        animatePane(customerPane); // Call the animation method

    }

    @FXML
    void btnDiscountOnAction(ActionEvent event) throws IOException {
        handleSelection(btnDiscount);
        AnchorPane discountPane  = FXMLLoader.load(this.getClass().getResource("/view/discount_form.fxml"));
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(discountPane);
        animatePane(discountPane); // Call the animation method

    }

    @FXML
    void btnEmoloyeeOnAction(ActionEvent event) {
        handleSelection(btnEmployee);
        AnchorPane homePane = null;
        try {
            homePane = FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(homePane);
        animatePane(homePane); // Call the animation method

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
    loadHomeForm();
    }

    @FXML
    void btnMenuBarOnAction(ActionEvent event) throws IOException {
        AnchorPane profileForm = FXMLLoader.load(this.getClass().getResource("/view/profile_form.fxml"));


        // Set the initial position of the profile form to be outside the sideNode
        profileForm.setTranslateX(-sideNode.getWidth());
        sideNode.getChildren().add(profileForm);

        // Create a TranslateTransition to animate the profile form
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), profileForm);
        slideIn.setFromX(-sideNode.getWidth());
        slideIn.setToX(0);

        // Play the animation
        slideIn.play();
        this.sideNode.getChildren().clear();
        this.sideNode.getChildren().add(profileForm);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        handleSelection(btnPayment);
        AnchorPane paymentPane = FXMLLoader.load(this.getClass().getResource("/view/payment_form.fxml"));
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(paymentPane);
        animatePane(paymentPane); // Call the animation method

    }

    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        handleSelection(btnStock);
        AnchorPane stockPane = FXMLLoader.load(this.getClass().getResource("/view/stock_form.fxml"));
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(stockPane);
        animatePane(stockPane); // Call the animation method
    }

    private void animatePane(Pane pane) {
        pane.setTranslateX(-pane.getWidth()); // Start the pane outside the view
        pane.setOpacity(0); // Start with opacity 0 for a fade-in effect

        // Create TranslateTransition to move the pane into view
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), pane);
        translateTransition.setToX(0);

        // Create FadeTransition to fade the pane in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pane);
        fadeTransition.setToValue(1);

        // Play both animations together
        translateTransition.play();
        fadeTransition.play();
    }


    private void handleSelection(JFXButton button) {
        if(selectedButton != null){
            selectedButton.setStyle(""); // Deselect the previously selected button
        }
        selectedButton = button; // Set the new selected button
        selectedButton.setStyle("-fx-background-radius: 20px 0px 0px 20px;\n" +
                "    -fx-background-color: #97aeed;\n" +
                "    -fx-text-fill: #ffffff;"); // Apply the selected style
    }

}
