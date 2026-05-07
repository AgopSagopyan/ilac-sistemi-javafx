package com.example.controller;

import com.example.App;
import com.example.dao.IlacDAO;
import com.example.dao.StokDAO;
import com.example.model.Ilac;
import com.example.model.Stok;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

public class SearchController {
    
    private App mainApp;
    private final StokDAO stokDAO = new StokDAO();
    private final IlacDAO ilacDAO = new IlacDAO();
    
    // Harita merkezini Pane ortasına oturtmak icin basit bir olcekleme carpani
    private final double SCALE = 3.0; 
    private final double OFFSET_X = 150.0;
    private final double OFFSET_Y = 150.0;
    
    private final double USER_X = 15.0;
    private final double USER_Y = 25.0;

    @FXML private TextField searchField;
    @FXML private TableView<Stok> tableView;
    @FXML private TableColumn<Stok, String> pharmacyCol;
    @FXML private TableColumn<Stok, String> medicineCol;
    @FXML private TableColumn<Stok, String> distanceCol;
    @FXML private TableColumn<Stok, String> stockCol;
    @FXML private Button equivalentBtn;
    @FXML private ListView<Ilac> equivalentListView;
    @FXML private Pane mapPane;

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        pharmacyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEczane().getAd()));
        medicineCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIlac().getAd()));
        distanceCol.setCellValueFactory(data -> {
            double distance = data.getValue().getEczane().distanceTo(USER_X, USER_Y);
            return new SimpleStringProperty(String.format("%.2f", distance));
        });
        stockCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdet() > 0 ? "Var (" + data.getValue().getAdet() + ")" : "Yok"));

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            equivalentBtn.setDisable(newSel == null);
        });
    }

    @FXML
    private void handleSearch() {
        String q = searchField.getText().trim();
        if(!q.isEmpty()) {
            List<Stok> results = stokDAO.ilacStokAra(q);
            results.sort((s1, s2) -> Double.compare(
                    s1.getEczane().distanceTo(USER_X, USER_Y), 
                    s2.getEczane().distanceTo(USER_X, USER_Y)));
                    
            tableView.setItems(FXCollections.observableArrayList(results));
            equivalentListView.getItems().clear();
            equivalentBtn.setDisable(true);
            
            drawMap(results);
        }
    }
    
    private void drawMap(List<Stok> results) {
        mapPane.getChildren().clear();
        
        // Draw User
        Circle userDot = new Circle(USER_X * SCALE + OFFSET_X, USER_Y * SCALE + OFFSET_Y, 6);
        userDot.getStyleClass().add("map-point-user");
        Text userLbl = new Text(USER_X * SCALE + OFFSET_X + 10, USER_Y * SCALE + OFFSET_Y + 5, "Siz");
        mapPane.getChildren().addAll(userDot, userLbl);
        
        // Draw Pharmacies
        for (Stok s : results) {
            double px = s.getEczane().getKoordinatX() * SCALE + OFFSET_X;
            double py = s.getEczane().getKoordinatY() * SCALE + OFFSET_Y;
            Circle pharmDot = new Circle(px, py, 6);
            pharmDot.getStyleClass().add("map-point-pharmacy");
            Text pharmLbl = new Text(px + 10, py + 5, s.getEczane().getAd());
            mapPane.getChildren().addAll(pharmDot, pharmLbl);
        }
    }

    @FXML
    private void showEquivalents() {
        Stok selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            List<Ilac> equivalents = ilacDAO.getEsdagerIlaclar(selected.getIlac().getEtkenMadde(), selected.getIlac().getId());
            equivalentListView.setItems(FXCollections.observableArrayList(equivalents));
        }
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showMainScreen();
        }
    }
}
