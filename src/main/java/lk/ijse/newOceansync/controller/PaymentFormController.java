package lk.ijse.newOceansync.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.newOceansync.db.DbConnection;
import lk.ijse.newOceansync.model.*;
import lk.ijse.newOceansync.model.tm.PaymentTm;
import lk.ijse.newOceansync.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PaymentFormController {

    public Label lblPaymentId;
    public JFXComboBox cmbSelectedCourceDiscount;
    public JFXComboBox cmbSelectedActivityDiscount;
    public JFXComboBox cmbBougthtStockDiscount;
    public JFXComboBox cmbPaymentType;
    public Label lblNetTotal;
    public JFXTextField txtAmount;
    @FXML
    private JFXComboBox<String> cmbSelectedActivity;

    @FXML
    private JFXComboBox<String> cmbSelectedCource;

    @FXML
    private JFXComboBox<String> cmbStock;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblActivityId;

    @FXML
    private Label lblCourceId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblSelectedActivityAmount;

    @FXML
    private Label lblSelectedCourceCost;

    @FXML
    private Label lblStockAmount;

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblTel;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchId;

    private ObservableList<PaymentTm> paymentList = FXCollections.observableArrayList();

    String activityId;
    String courceId;
    String paymentId;
    double totalAmount;
    double balance;


    public void initialize() {
        setCellValueFactory();
        loadNextPaymentId();
        loadAllCources();
        loadAllActivities();
        loadAllStocks();
        loadAllDiscounts();
        loadDateAndTime();
        loadPaymentType();
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void loadNextPaymentId() {
        String currentId = PaymentRepo.currerntId();
        String nextId = nextId(currentId);
        lblPaymentId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("P");
            int id = Integer.parseInt(split[1]);    //2
            return "P" + ++id;
        }
        return "P1";
    }

    private void loadAllCources() {
        ObservableList<String> cources = FXCollections.observableArrayList();
        try {
            List<String> cource = CourceRepo.getAllCources();
            for (String name : cource) {
                cources.add(name);
            }
            cmbSelectedCource.setItems(cources);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllActivities() {
        ObservableList<String> activities = FXCollections.observableArrayList();
        try {
            List<String> activity = ActivityRepo.getAllActivities();
            for (String name : activity) {
                activities.add(name);
            }
            cmbSelectedActivity.setItems(activities);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllStocks() {
        ObservableList<String> stocks = FXCollections.observableArrayList();
        try {
            List<String> stock = StockRepo.getAllStockName();
            for (String name : stock) {
                stocks.add(name);
            }
            cmbStock.setItems(stocks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadAllDiscounts() {
        ObservableList<Double> discounts = FXCollections.observableArrayList();
        try {
            List<Double> discount = DiscountRepo.getDiscount();
            for (Double name : discount) {
                discounts.add(name);
            }
            cmbBougthtStockDiscount.setItems(discounts);
            cmbSelectedActivityDiscount.setItems(discounts);
            cmbSelectedCourceDiscount.setItems(discounts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadDateAndTime() {
        LocalDate now = LocalDate.now();
        LocalTime localTime = LocalTime.now().withNano(0);

        lblDate.setText(now.toString());
        lblTime.setText(localTime.toString());
        //System.out.println(localTime);
    }
    private void loadPaymentType() {
        for (Type type : Type.values()) {
            cmbPaymentType.getItems().add(type.name());
        }
    }
    @FXML
    void btnAddToCartActivityOnAction(ActionEvent event) {
        String paymentId = lblPaymentId.getText();
        String courseId = lblCourceId.getText();
        String customerName = lblCustomerName.getText();
        activityId = lblActivityId.getText();
        String description = cmbSelectedActivity.getValue();
        double unitPrice = Double.parseDouble(lblSelectedActivityAmount.getText());
        int qty;
        if (txtQty.getText().equals("")) {
            qty = 1;
        } else {
            qty = Integer.parseInt(txtQty.getText());
        }
//        int discount = Integer.parseInt(cmbDiscount.getValue());
        double discount;
        if (cmbSelectedActivityDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbSelectedActivityDiscount.getValue()));
        }

        double total = qty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                qty += paymentList.get(i).getQty();
                total = unitPrice * qty;

                paymentList.get(i).setQty(qty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(activityId, customerName, description, unitPrice, qty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();


    }

    @FXML
    void btnAddToCartCourceOnAction(ActionEvent event) {
        // String paymentId = lblPaymentId.getText();
        String customerName = lblCustomerName.getText();
        courceId = lblCourceId.getText();
        String description = cmbSelectedCource.getValue();
        double unitPrice = Double.parseDouble(lblSelectedCourceCost.getText());
        int qty;
        if (txtQty.getText().equals("")) {
            qty = 1;
        } else {
            qty = Integer.parseInt(txtQty.getText());
        }
        double discount;
        if (cmbSelectedCourceDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbSelectedCourceDiscount.getValue()));
        }
        double total = qty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                qty += paymentList.get(i).getQty();
                total = unitPrice * qty;

                paymentList.get(i).setQty(qty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(courceId, customerName, description, unitPrice, qty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();

    }

    private void calculateNetTotal() {

        double netTotal = 0;
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            PaymentTm tm = tblPayment.getItems().get(i);
            netTotal += tm.getTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnAddToCartStockOnAction(ActionEvent event) {
        //String paymentId = lblPaymentId.getText();
        String stockId = lblStockId.getText();
        String customerName = lblCustomerName.getText();
        String description = cmbStock.getValue();
        double unitPrice = Double.parseDouble(lblStockAmount.getText());
        int qty;
        if (txtQty.getText().equals("")) {
            qty = 1;
        } else {
            qty = Integer.parseInt(txtQty.getText());
        }
        double discount;
        if (cmbBougthtStockDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbBougthtStockDiscount.getValue()));
        }
        double total = qty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                qty += paymentList.get(i).getQty();
                total = unitPrice * qty;

                paymentList.get(i).setQty(qty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(stockId, customerName, description, unitPrice, qty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {

        txtQty.setText("");
        lblCustomerId.setText("");
        lblCustomerName.setText("");
        cmbSelectedCource.getSelectionModel().clearSelection();
        cmbSelectedActivity.getSelectionModel().clearSelection();
        cmbStock.getSelectionModel().clearSelection();
        cmbPaymentType.getSelectionModel().clearSelection();
        cmbBougthtStockDiscount.getSelectionModel().clearSelection();
        cmbSelectedCourceDiscount.getSelectionModel().clearSelection();
        cmbSelectedActivityDiscount.getSelectionModel().clearSelection();
        lblCourceId.setText("");
        lblSelectedCourceCost.setText("");
        lblSelectedActivityAmount.setText("");
        lblStockId.setText("");
        lblStockAmount.setText("");
        lblSelectedCourceCost.setText("");
        lblSelectedActivityAmount.setText("");
    }

    @FXML
    void btnPlacePaymentOnAction(ActionEvent event) {

         paymentId = lblPaymentId.getText();
        String type = (String) cmbPaymentType.getValue();
        double total = Double.parseDouble(lblNetTotal.getText());
        Date date = Date.valueOf(lblDate.getText());
        String customerId = lblCustomerId.getText();
        String stockId = lblStockId.getText();
        totalAmount = Double.parseDouble(txtAmount.getText());
        balance = totalAmount- total;

        var payment = new Payment(paymentId, type, total, date, customerId);

        List<PaymentDetail> paymentDetails = new ArrayList<>();
        List<SelectedActivity> selectedActivities = new ArrayList<>();
        List<SelectedCource> selectedCources = new ArrayList<>();
       List<SelectedStock> selectedStocks =new ArrayList<>();

        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            PaymentTm tm = tblPayment.getItems().get(i);

            PaymentDetail paymentDetail = new PaymentDetail(
                lblPaymentId.getText(),
                lblCustomerId.getText(),
                lblCustomerName.getText(),
                    tm.getId(),
                    tm.getDescription(),
                    tm.getUnitPrice(),
                    tm.getQty(),
                    tm.getDiscount(),
                    tm.getTotal()
            );
            paymentDetails.add(paymentDetail);

            char[] charArray = tm.getId().toCharArray();
            if (charArray[0] == 'A') {
                System.out.println("A");
                SelectedActivity sAct = new SelectedActivity(
                        tm.getId(),
                        customerId,
                        Date.valueOf(lblDate.getText()));
                selectedActivities.add(sAct);
                System.out.println(sAct.toString());
            } else if (charArray[0] == 'C') {
                SelectedCource sCource = new SelectedCource(
                        customerId,
                        tm.getId(),
                        Date.valueOf(lblDate.getText())
                );
                selectedCources.add(sCource);
                //  System.out.println("C");
            }else if (charArray[0] == 'S') {
                SelectedStock sStock = new SelectedStock(
                        stockId,
                        tm.getQty(),
                        customerId,
                        paymentId,
                        Date.valueOf(lblDate.getText())
                );
                selectedStocks.add(sStock);

                System.out.println(sStock);
                // System.out.println("S");
            }
        }
        PlacePayment placePayment = new PlacePayment(payment, paymentDetails, selectedActivities, selectedCources,selectedStocks);
        try {
            boolean isSaved = PlacePaymentRepo.placePayment(placePayment);
            System.out.println(isSaved);
            System.out.println("Is saved placed payment");
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved").showAndWait();
                clearFields();
                loadNextPaymentId();

            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Saved").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btncreateNewActivityOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/add_activity.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Activity Form");

        stage.show();
    }

    @FXML
    void btncreateNewCourceOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/add_cource.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Course Form");

        stage.show();
    }

    @FXML
    void cmbBougthtStockDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSelectedActivityDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSelectedActivityOnAction(ActionEvent event) {
        String activity = (String) cmbSelectedActivity.getValue();
        try {
            Activity activity1 = ActivityRepo.getActivityByActivityName(activity);
            lblSelectedActivityAmount.setText(String.valueOf(activity1.getCost()));
            lblActivityId.setText(activity1.getActivityId());
            lblLocation.setText(activity1.getLocation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSelectedCourceDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSelectedCourceOnAction(ActionEvent event) {
        String cource = (String) cmbSelectedCource.getValue();
        try {
            Cource cource1 = CourceRepo.getCourceByCourceName(cource);
            lblSelectedCourceCost.setText(String.valueOf(cource1.getCost()));
            lblCourceId.setText(cource1.getCourceId());
            lblDuration.setText(String.valueOf(cource1.getDuration()));
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void cmbStockOnAction(ActionEvent event) {
        String stock = (String) cmbStock.getValue();
        try {
            Stock stock1 = StockRepo.getStockByStockName(stock);
            lblStockAmount.setText(String.valueOf(stock1.getPrice()));
            lblStockId.setText(stock1.getStockId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartStockOnAction(event);

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String tel = txtSearchId.getText();
        try {
            Customer customer = CustomerRepo.getCustomerByTel(tel);

            if (customer == null) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
                return;
            }else {
                lblCustomerId.setText(customer.getCustomerId());
                lblCustomerName.setText(customer.getName());
                lblTel.setText(customer.getTel());
            }
        } catch (SQLException e) {
            // new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            throw new RuntimeException(e);
        }


    }

    public void cmbPaymentTypeOnAction(ActionEvent actionEvent) {
    }

    public void btncreateNewCustomerOnAction(ActionEvent actionEvent) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/add_customer.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Payment Form");

        stage.show();
    }

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {


            JasperDesign report = JRXmlLoader.load("src/main/resources/reports/payment-report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(report);

            Map<String , Object> data = new HashMap<>();
           // System.out.println(data);
            data.put("paymentId", paymentId);
        System.out.println(totalAmount);

            data.put("totalAmount", totalAmount);
            data.put("balance", balance);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);


    }

    public void txtAmountOnAction(ActionEvent actionEvent) {
        cmbPaymentTypeOnAction(actionEvent);
    }
}
enum Type{
    CASH, CARD
}

