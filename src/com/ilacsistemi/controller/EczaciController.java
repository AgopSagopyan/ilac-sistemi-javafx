package com.ilacsistemi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ilacsistemi.model.Eczane;
import com.ilacsistemi.model.Ilac;
import com.ilacsistemi.util.DBBaglanti;

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

public class EczaciController {

    @FXML private ComboBox<Eczane> cmbEczane;
    @FXML private ComboBox<Ilac> cmbIlac;
    @FXML private TextField txtMiktar;
    @FXML private Label lblMesaj;

    @FXML private TableView<StokGorunumu> tblStoklar;
    @FXML private TableColumn<StokGorunumu, String> colIlacAdi;
    @FXML private TableColumn<StokGorunumu, Number> colMiktar;

    private ObservableList<Eczane> eczaneListesi = FXCollections.observableArrayList();
    private ObservableList<Ilac> ilacListesi = FXCollections.observableArrayList();
    private ObservableList<StokGorunumu> stokListesi = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIlacAdi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().ilacAdi));
        colMiktar.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().miktar));
        tblStoklar.setItems(stokListesi);

        veriYukle();
    }

    private void veriYukle() {
        try (Connection con = DBBaglanti.baglantiGetir()) {
            // Eczaneleri Yükle
            ResultSet rsE = con.createStatement().executeQuery("SELECT * FROM eczaneler");
            while (rsE.next()) {
                eczaneListesi.add(new Eczane(rsE.getInt("id"), rsE.getString("eczane_adi"), "", 0, 0));
            }
            cmbEczane.setItems(eczaneListesi);

            // İlaçları Yükle
            ResultSet rsI = con.createStatement().executeQuery("SELECT * FROM ilaclar");
            while (rsI.next()) {
                ilacListesi.add(new Ilac(rsI.getInt("id"), rsI.getString("ilac_adi"), rsI.getInt("tur_id"), "", ""));
            }
            cmbIlac.setItems(ilacListesi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eczaneSecildi(ActionEvent event) {
        tabloyuGuncelle();
    }

    private void tabloyuGuncelle() {
        Eczane secilenEczane = cmbEczane.getValue();
        if (secilenEczane == null) return;

        stokListesi.clear();
        try (Connection con = DBBaglanti.baglantiGetir()) {
            String sql = "SELECT i.ilac_adi, s.miktar FROM stoklar s JOIN ilaclar i ON s.ilac_id = i.id WHERE s.eczane_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, secilenEczane.getId());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                stokListesi.add(new StokGorunumu(rs.getString("ilac_adi"), rs.getInt("miktar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void stokGuncelle(ActionEvent event) {
        Eczane eczane = cmbEczane.getValue();
        Ilac ilac = cmbIlac.getValue();
        String miktarStr = txtMiktar.getText();

        if (eczane == null || ilac == null || miktarStr.isEmpty()) {
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Lütfen Eczane, İlaç ve Miktar seçiniz.");
            return;
        }

        try {
            int miktar = Integer.parseInt(miktarStr);
            try (Connection con = DBBaglanti.baglantiGetir()) {
                // Stok var mı kontrol et
                String checkSql = "SELECT id FROM stoklar WHERE eczane_id = ? AND ilac_id = ?";
                PreparedStatement psCheck = con.prepareStatement(checkSql);
                psCheck.setInt(1, eczane.getId());
                psCheck.setInt(2, ilac.getId());
                ResultSet rs = psCheck.executeQuery();

                if (rs.next()) {
                    // Güncelle
                    String updSql = "UPDATE stoklar SET miktar = ? WHERE eczane_id = ? AND ilac_id = ?";
                    PreparedStatement psUpd = con.prepareStatement(updSql);
                    psUpd.setInt(1, miktar);
                    psUpd.setInt(2, eczane.getId());
                    psUpd.setInt(3, ilac.getId());
                    psUpd.executeUpdate();
                } else {
                    // Ekle
                    String insSql = "INSERT INTO stoklar (eczane_id, ilac_id, miktar) VALUES (?, ?, ?)";
                    PreparedStatement psIns = con.prepareStatement(insSql);
                    psIns.setInt(1, eczane.getId());
                    psIns.setInt(2, ilac.getId());
                    psIns.setInt(3, miktar);
                    psIns.executeUpdate();
                }
                lblMesaj.setStyle("-fx-text-fill: green;");
                lblMesaj.setText("Stok başarıyla güncellendi.");
                tabloyuGuncelle();
                txtMiktar.clear();
            }
        } catch (NumberFormatException e) {
            lblMesaj.setStyle("-fx-text-fill: red;");
            lblMesaj.setText("Miktar sadece sayı olmalıdır.");
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

    public static class StokGorunumu {
        String ilacAdi;
        int miktar;
        public StokGorunumu(String i, int m) { this.ilacAdi = i; this.miktar = m; }
    }
}
