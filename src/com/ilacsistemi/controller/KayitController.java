package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ilacsistemi.util.DBBaglanti;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KayitController {

    @FXML
    private TextField txtAd;

    @FXML
    private TextField txtSoyad;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSifre;

    @FXML
    private ComboBox<String> cmbRol;

    @FXML
    private Label lblMesaj;

    @FXML
    public void initialize() {
        cmbRol.setItems(FXCollections.observableArrayList("Kullanici", "Eczaci"));
    }

    @FXML
    void kayitOl(ActionEvent event) {
        String ad = txtAd.getText();
        String soyad = txtSoyad.getText();
        String email = txtEmail.getText();
        String sifre = txtSifre.getText();
        String rol = cmbRol.getValue();

        if (ad.isEmpty() || soyad.isEmpty() || email.isEmpty() || sifre.isEmpty() || rol == null) {
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        int rolId = rol.equals("Eczaci") ? 2 : 3;

        try (Connection con = DBBaglanti.baglantiGetir()) {
            String sql = "INSERT INTO kullanicilar (ad, soyad, email, sifre, rol_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ad);
            ps.setString(2, soyad);
            ps.setString(3, email);
            ps.setString(4, sifre);
            ps.setInt(5, rolId);
            
            int sonuc = ps.executeUpdate();
            if (sonuc > 0) {
                lblMesaj.setStyle("-fx-text-fill: green;");
                lblMesaj.setText("Kayıt başarılı! Giriş yapabilirsiniz.");
                // Formu temizle
                txtAd.clear(); txtSoyad.clear(); txtEmail.clear(); txtSifre.clear(); cmbRol.getSelectionModel().clearSelection();
            } else {
                lblMesaj.setStyle("-fx-text-fill: red;");
                lblMesaj.setText("Kayıt yapılamadı.");
            }
        } catch (Exception e) {
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Bu e-posta adresi kullanımda olabilir.");
            e.printStackTrace();
        }
    }

    @FXML
    void geriDon(ActionEvent event) {
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
}
