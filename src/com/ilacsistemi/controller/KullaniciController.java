package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ilacsistemi.util.DBBaglanti;

import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KullaniciController {

    @FXML private TextField txtIlacArama;
    @FXML private TextField txtEnlem;
    @FXML private TextField txtBoylam;
    @FXML private Label lblDurum;

    @FXML private TableView<AramaSonucu> tblEczaneler;
    @FXML private TableColumn<AramaSonucu, String> colEczaneAdi;
    @FXML private TableColumn<AramaSonucu, Number> colMiktar;
    @FXML private TableColumn<AramaSonucu, Number> colMesafe;

    @FXML private TableView<MuadilSonucu> tblMuadiller;
    @FXML private TableColumn<MuadilSonucu, String> colMuadilIlac;
    @FXML private TableColumn<MuadilSonucu, String> colMuadilTur;

    private ObservableList<AramaSonucu> eczaneListesi = FXCollections.observableArrayList();
    private ObservableList<MuadilSonucu> muadilListesi = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colEczaneAdi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().eczaneAdi));
        colMiktar.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().miktar));
        colMesafe.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().mesafe));

        colMuadilIlac.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().ilacAdi));
        colMuadilTur.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().turAdi));

        tblEczaneler.setItems(eczaneListesi);
        tblMuadiller.setItems(muadilListesi);
    }

    @FXML
    void ilacAra(ActionEvent event) {
        String arananIlac = txtIlacArama.getText().trim();
        if (arananIlac.isEmpty()) {
            lblDurum.setText("Lütfen aramak istediğiniz ilacın adını girin.");
            return;
        }

        double konumEnlem = 0;
        double konumBoylam = 0;
        try {
            konumEnlem = Double.parseDouble(txtEnlem.getText());
            konumBoylam = Double.parseDouble(txtBoylam.getText());
        } catch (NumberFormatException e) {
            lblDurum.setText("Konum (Enlem/Boylam) sayısal olmalıdır.");
            return;
        }

        eczaneListesi.clear();
        muadilListesi.clear();

        try (Connection con = DBBaglanti.baglantiGetir()) {
            // İlacı Bul
            String ilacSql = "SELECT id, tur_id FROM ilaclar WHERE ilac_adi LIKE ?";
            PreparedStatement psIlac = con.prepareStatement(ilacSql);
            psIlac.setString(1, "%" + arananIlac + "%");
            ResultSet rsIlac = psIlac.executeQuery();

            if (rsIlac.next()) {
                int ilacId = rsIlac.getInt("id");
                int turId = rsIlac.getInt("tur_id");

                // Stokları ve Eczaneleri Bul
                String stokSql = "SELECT e.eczane_adi, e.enlem, e.boylam, s.miktar " +
                                 "FROM stoklar s JOIN eczaneler e ON s.eczane_id = e.id " +
                                 "WHERE s.ilac_id = ? AND s.miktar > 0";
                PreparedStatement psStok = con.prepareStatement(stokSql);
                psStok.setInt(1, ilacId);
                ResultSet rsStok = psStok.executeQuery();

                boolean stokVarMi = false;
                while (rsStok.next()) {
                    stokVarMi = true;
                    String eAdi = rsStok.getString("eczane_adi");
                    int miktar = rsStok.getInt("miktar");
                    double eEnlem = rsStok.getDouble("enlem");
                    double eBoylam = rsStok.getDouble("boylam");
                    
                    // Basit Öklid mesafesi simülasyonu
                    double mesafe = Math.sqrt(Math.pow(eEnlem - konumEnlem, 2) + Math.pow(eBoylam - konumBoylam, 2));
                    
                    eczaneListesi.add(new AramaSonucu(eAdi, miktar, Math.round(mesafe * 100.0) / 100.0));
                }

                // Mesafeye göre sırala
                eczaneListesi.sort((o1, o2) -> Double.compare(o1.mesafe, o2.mesafe));

                if (!stokVarMi) {
                    lblDurum.setText("Aradığınız ilaç şu an hiçbir eczanede bulunmuyor. Yandaki muadilleri inceleyebilirsiniz.");
                } else {
                    lblDurum.setText("İlaç bulundu ve mesafeye göre listelendi.");
                }

                // Muadilleri Bul (Aynı türdeki diğer ilaçlar)
                String muadilSql = "SELECT i.ilac_adi, t.tur_adi FROM ilaclar i " +
                                   "JOIN ilac_turleri t ON i.tur_id = t.id " +
                                   "WHERE i.tur_id = ? AND i.id != ?";
                PreparedStatement psMuadil = con.prepareStatement(muadilSql);
                psMuadil.setInt(1, turId);
                psMuadil.setInt(2, ilacId);
                ResultSet rsMuadil = psMuadil.executeQuery();
                
                while (rsMuadil.next()) {
                    muadilListesi.add(new MuadilSonucu(rsMuadil.getString("ilac_adi"), rsMuadil.getString("tur_adi")));
                }

            } else {
                lblDurum.setText("Belirtilen isimde ilaç sistemde bulunamadı.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblDurum.setText("Arama sırasında veritabanı hatası oluştu.");
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

    // TableView için yardımcı sınıflar
    public static class AramaSonucu {
        String eczaneAdi;
        int miktar;
        double mesafe;
        public AramaSonucu(String e, int m, double msf) { this.eczaneAdi = e; this.miktar = m; this.mesafe = msf; }
    }

    public static class MuadilSonucu {
        String ilacAdi;
        String turAdi;
        public MuadilSonucu(String i, String t) { this.ilacAdi = i; this.turAdi = t; }
    }
}
