package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ilacsistemi.util.DBBaglanti;
import com.ilacsistemi.util.EpostaServisi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SifremiUnuttumController {

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblMesaj;

    @FXML
    void sifreGonder(ActionEvent event) {
        String email = txtEmail.getText();
        
        if (email.isEmpty()) {
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Lütfen e-posta adresinizi girin.");
            return;
        }

        try (Connection con = DBBaglanti.baglantiGetir()) {
            String sql = "SELECT sifre FROM kullanicilar WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String sifre = rs.getString("sifre");
                String icerik = "Merhaba,\n\nSisteme giriş için şifreniz: " + sifre + "\n\nİyi günler dileriz.";
                
                boolean gonderildi = EpostaServisi.epostaGonder(email, "Eczane Sistemi - Şifre Hatırlatma", icerik);
                
                if (gonderildi) {
                    lblMesaj.setStyle("-fx-text-fill: green;");
                    lblMesaj.setText("Şifreniz e-posta adresinize gönderildi.");
                } else {
                    lblMesaj.setStyle("-fx-text-fill: red;");
                    lblMesaj.setText("E-posta gönderilirken bir hata oluştu.");
                }
                
            } else {
                lblMesaj.setStyle("-fx-text-fill: red;");
                lblMesaj.setText("Bu e-posta adresi sistemde bulunamadı.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Veritabanı hatası oluştu.");
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
