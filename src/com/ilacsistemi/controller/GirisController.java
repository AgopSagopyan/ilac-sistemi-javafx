package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ilacsistemi.util.DBBaglanti;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GirisController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSifre;

    @FXML
    private Label lblMesaj;

    @FXML
    void girisYap(ActionEvent event) {
        String email = txtEmail.getText();
        String sifre = txtSifre.getText();

        if (email.isEmpty() || sifre.isEmpty()) {
            lblMesaj.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        try (Connection con = DBBaglanti.baglantiGetir()) {
            if (con == null) {
                lblMesaj.setText("Veritabanı bağlantı hatası!");
                return;
            }

            String sql = "SELECT * FROM kullanicilar WHERE email = ? AND sifre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, sifre);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int rolId = rs.getInt("rol_id");
                
                // Panele Yönlendirme
                if (rolId == 1) {
                    ekranAc(event, "/com/ilacsistemi/view/AdminPanel.fxml", "Admin Paneli");
                } else if (rolId == 2) {
                    ekranAc(event, "/com/ilacsistemi/view/EczaciPanel.fxml", "Eczacı Paneli");
                } else {
                    ekranAc(event, "/com/ilacsistemi/view/KullaniciPanel.fxml", "Kullanıcı Paneli");
                }
            } else {
                lblMesaj.setText("E-posta veya şifre hatalı.");
            }
        } catch (Exception e) {
            lblMesaj.setText("Giriş yapılırken hata oluştu.");
            e.printStackTrace();
        }
    }

    @FXML
    void kayitEkraniAc(ActionEvent event) {
        ekranAc(event, "/com/ilacsistemi/view/Kayit.fxml", "Kayıt Ol");
    }

    @FXML
    void sifremiUnuttumAc(ActionEvent event) {
        ekranAc(event, "/com/ilacsistemi/view/SifremiUnuttum.fxml", "Şifremi Unuttum");
    }

    private void ekranAc(ActionEvent event, String fxmlYolu, String baslik) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlYolu));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(baslik);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
