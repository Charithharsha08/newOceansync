package lk.ijse.newOceansync.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.newOceansync.model.Activity;
import lk.ijse.newOceansync.model.tm.ActivityTm;
import lk.ijse.newOceansync.repository.ActivityRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
    private TableView<ActivityTm> tblActivity;

    List <Activity> activities = new ArrayList<>();

    public void initialize() {
        this.activities = getAllActivities();
        setCellValue();
        loadActivityTable();

    }

    private void loadActivityTable() {

        ObservableList<ActivityTm> observableList = FXCollections.observableArrayList();
        for (Activity activity : activities) {
            observableList.add(new ActivityTm(activity.getActivityId(), activity.getName(), activity.getType(), activity.getLocation(), activity.getCost()));
        }
        tblActivity.setItems(observableList);
    }

    private void setCellValue() {
        colActivityId.setCellValueFactory(new PropertyValueFactory<>("activityId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private List<Activity> getAllActivities() {
        List<Activity> activityList = null;
        try {
            activityList = new ActivityRepo().getAll();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return activityList;
    }

}
