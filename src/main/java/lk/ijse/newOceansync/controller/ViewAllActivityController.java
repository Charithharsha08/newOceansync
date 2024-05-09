package lk.ijse.newOceansync.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewAllActivityController {

    @FXML
    private TableColumn<?, ?> colActivityId;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<?> tblActivity;

}
