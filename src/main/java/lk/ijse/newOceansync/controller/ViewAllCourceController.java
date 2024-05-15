package lk.ijse.newOceansync.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.newOceansync.model.Cource;
import lk.ijse.newOceansync.model.Discount;
import lk.ijse.newOceansync.model.tm.CourceTm;
import lk.ijse.newOceansync.repository.CourceRepo;
import lk.ijse.newOceansync.repository.DiscountRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewAllCourceController {

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colCourceId;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CourceTm> tblCource;

    private List<Cource> courceList = new ArrayList<>();

    public void initialize() {
        this.courceList = getAllCource();
        setCellValueFactory();
        loadCourceTable();
    }

    private void loadCourceTable() {
        ObservableList<CourceTm> courceLists = FXCollections.observableArrayList();
        for (Cource cource : courceList) {
            CourceTm courceTm = new CourceTm(
                    cource.getCourceId(),
                    cource.getName(),
                    cource.getDuration(),
                    cource.getCost()
            );
            courceLists.add(courceTm);
        }
        tblCource.setItems(courceLists);
    }

    private void setCellValueFactory() {
        colCourceId.setCellValueFactory(new PropertyValueFactory<>("courceId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

    }

    private List<Cource> getAllCource() {
        List<Cource> courceList = null;
        try {
            courceList = CourceRepo.getAllDiscount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return courceList;
    }
    }


