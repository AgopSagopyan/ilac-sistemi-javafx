package com.example.controller;

import com.example.App;
import com.example.dao.StokDAO;
import com.example.dao.IlacDAO;
import com.example.model.Eczaci;
import com.example.model.Stok;
import com.example.model.Ilac;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class DashboardController {
    
    private App mainApp;
    private Eczaci loggedInUser;
    private final StokDAO stokDAO = new StokDAO();
    private final IlacDAO ilacDAO = new IlacDAO();

    @FXML private Label welcomeLbl;
    @FXML private TableView<Stok> tableView;
    @FXML private TableColumn<Stok, String> idCol;
    @FXML private TableColumn<Stok, String> medCol;
    @FXML private TableColumn<Stok, String> qtyCol;
    @FXML private TableColumn<Stok, String> statusCol;
    @FXML private TextField qtyField;
    
    // Yeni Ilac form fields
    @FXML private TextField newIlacAd;
    @FXML private TextField newIlacEtken;
    @FXML private TextField newIlacAciklama;
    @FXML private TextField newIlacAdet;

    public void setMainApp(App mainApp, Eczaci loggedInUser) {
        this.mainApp = mainApp;
        this.loggedInUser = loggedInUser;
        welcomeLbl.setText("Hoşgeldiniz, " + loggedInUser.getKullaniciAdi());
        loadData();
    }

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));
        medCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIlac().getAd()));
        qtyCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAdet())));
        statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAktifMi() ? "Aktif" : "Pasif"));
    }

    private void loadData() {
        if (loggedInUser != null) {
            List<Stok> stocks = stokDAO.eczaneStoklariniGetir(loggedInUser.getEczaneId());
            tableView.setItems(FXCollections.observableArrayList(stocks));
        }
    }

    @FXML
    private void handleUpdate() {
        Stok selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                int newQty = Integer.parseInt(qtyField.getText());
                stokDAO.stokGuncelle(selected.getId(), newQty);
                loadData();
                qtyField.clear();
            } catch (NumberFormatException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Geçerli bir sayı giriniz.");
                a.show();
            }
        }
    }
    
    @FXML
    private void handleToggleStatus() {
        Stok selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            stokDAO.durumDegistir(selected.getId(), !selected.isAktifMi());
            loadData();
        }
    }
    
    @FXML
    private void handleAddMedicine() {
        if (newIlacAd.getText().isEmpty() || newIlacEtken.getText().isEmpty() || newIlacAdet.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Lütfen gerekli alanları doldurunuz (Ad, Etken Madde, Adet).");
            a.show();
            return;
        }
        
        try {
            int adet = Integer.parseInt(newIlacAdet.getText());
            Ilac yeniIlac = new Ilac(0, newIlacAd.getText(), newIlacEtken.getText(), newIlacAciklama.getText());
            int yeniIlacId = ilacDAO.ilacEkle(yeniIlac);
            if (yeniIlacId != -1) {
                stokDAO.stokEkle(loggedInUser.getEczaneId(), yeniIlacId, adet);
                loadData();
                newIlacAd.clear();
                newIlacEtken.clear();
                newIlacAciklama.clear();
                newIlacAdet.clear();
            }
        } catch (NumberFormatException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Adet için geçerli bir sayı giriniz.");
            a.show();
        }
    }

    @FXML
    private void handleLogout() {
        if (mainApp != null) {
            mainApp.showMainScreen();
        }
    }
}
