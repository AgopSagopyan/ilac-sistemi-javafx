package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ilacsistemi.util.DBBaglanti;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminController {

    @FXML private TextField txtEczaneAdi, txtAdres, txtEnlem, txtBoylam;
    @FXML private Label lblEczaneMesaj;

    @FXML private TextField txtIlacAdi, txtFotografYolu;
    @FXML private ComboBox<IlacTuru> cmbIlacTuru;
    @FXML private Label lblIlacMesaj;

    @FXML private TableView<SistemStok> tblTumStoklar;
    @FXML private TableColumn<SistemStok, String> colSEczane;
    @FXML private TableColumn<SistemStok, String> colSIlac;
    @FXML private TableColumn<SistemStok, Number> colSMiktar;

    private ObservableList<IlacTuru> turListesi = FXCollections.observableArrayList();
    private ObservableList<SistemStok> tumStoklarListesi = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colSEczane.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().eczaneAdi));
        colSIlac.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().ilacAdi));
        colSMiktar.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().miktar));
        tblTumStoklar.setItems(tumStoklarListesi);

        turleriYukle();
        stoklariYenile(null);
    }

    private void turleriYukle() {
        try (Connection con = DBBaglanti.baglantiGetir()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM ilac_turleri");
            while (rs.next()) {
                turListesi.add(new IlacTuru(rs.getInt("id"), rs.getString("tur_adi")));
            }
            cmbIlacTuru.setItems(turListesi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eczaneKaydet(ActionEvent event) {
        try {
            double enlem = Double.parseDouble(txtEnlem.getText());
            double boylam = Double.parseDouble(txtBoylam.getText());
            
            try (Connection con = DBBaglanti.baglantiGetir()) {
                String sql = "INSERT INTO eczaneler (eczane_adi, adres, enlem, boylam) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, txtEczaneAdi.getText());
                ps.setString(2, txtAdres.getText());
                ps.setDouble(3, enlem);
                ps.setDouble(4, boylam);
                ps.executeUpdate();
                lblEczaneMesaj.setStyle("-fx-text-fill: green;");
                lblEczaneMesaj.setText("Eczane başarıyla eklendi.");
                txtEczaneAdi.clear(); txtAdres.clear(); txtEnlem.clear(); txtBoylam.clear();
            }
        } catch (NumberFormatException e) {
            lblEczaneMesaj.setStyle("-fx-text-fill: red;");
            lblEczaneMesaj.setText("Enlem ve boylam sayısal olmalıdır.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ilacKaydet(ActionEvent event) {
        IlacTuru secilenTur = cmbIlacTuru.getValue();
        if (secilenTur == null || txtIlacAdi.getText().isEmpty()) {
            lblIlacMesaj.setStyle("-fx-text-fill: red;");
            lblIlacMesaj.setText("Ad ve tür seçimi zorunludur.");
            return;
        }

        try (Connection con = DBBaglanti.baglantiGetir()) {
            String sql = "INSERT INTO ilaclar (ilac_adi, tur_id, fotograf_yolu) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, txtIlacAdi.getText());
            ps.setInt(2, secilenTur.id);
            ps.setString(3, txtFotografYolu.getText());
            ps.executeUpdate();
            lblIlacMesaj.setStyle("-fx-text-fill: green;");
            lblIlacMesaj.setText("İlaç başarıyla sisteme eklendi.");
            txtIlacAdi.clear(); txtFotografYolu.clear(); cmbIlacTuru.getSelectionModel().clearSelection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void fotografSec(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("İlaç Fotoğrafı Seç");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File secilenDosya = fileChooser.showOpenDialog(stage);
        
        if (secilenDosya != null) {
            txtFotografYolu.setText(secilenDosya.getAbsolutePath());
        }
    }

    @FXML
    void stoklariYenile(ActionEvent event) {
        tumStoklarListesi.clear();
        try (Connection con = DBBaglanti.baglantiGetir()) {
            String sql = "SELECT e.eczane_adi, i.ilac_adi, s.miktar FROM stoklar s " +
                         "JOIN eczaneler e ON s.eczane_id = e.id " +
                         "JOIN ilaclar i ON s.ilac_id = i.id " +
                         "ORDER BY e.eczane_adi ASC, i.ilac_adi ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                tumStoklarListesi.add(new SistemStok(rs.getString("eczane_adi"), rs.getString("ilac_adi"), rs.getInt("miktar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cikisYap(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/ilacsistemi/view/Giris.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Eczane İlaç Sistemi - Giriş");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class IlacTuru {
        int id; String ad;
        public IlacTuru(int id, String ad) { this.id = id; this.ad = ad; }
        @Override public String toString() { return ad; }
    }

    public static class SistemStok {
        String eczaneAdi; String ilacAdi; int miktar;
        public SistemStok(String e, String i, int m) { this.eczaneAdi = e; this.ilacAdi = i; this.miktar = m; }
    }
}
