package lk.ijse.newOceansync.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.newOceansync.service.LoadingTask;

import java.io.IOException;

public class SplashScreenController {

    public AnchorPane rootNode;
    @FXML
    private Label lblProgress;

    @FXML
    private Rectangle recMain;

    @FXML
    private Rectangle recSub;

    public void initialize() throws IOException {
        LoadingTask task = new LoadingTask();
        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            String formattedNum = String.format("%.0f", newValue.doubleValue() * 100);
            System.out.println(formattedNum);
            lblProgress.setText(formattedNum + " %");
            recSub.setWidth(recMain.getWidth() * newValue.doubleValue());
            if (newValue.doubleValue() == 1.0) {
                Window window = lblProgress.getScene().getWindow();
                Stage stage = (Stage) window;
                stage.close();
                openLoginForm();
            }
        });
        new Thread(task).start();
    }

    private void openLoginForm() {
        try {
            AnchorPane node = FXMLLoader.load(getClass().getResource("/view/login_page.fxml"));
            Scene scene = new Scene(node);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
