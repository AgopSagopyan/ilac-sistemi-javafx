package com.example.dao;

import com.example.model.Ilac;
import com.example.model.Eczane;
import com.example.model.Stok;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StokDAO {
    public List<Stok> ilacStokAra(String ilacAdi) {
        List<Stok> stoklar = new ArrayList<>();
        String query = "SELECT s.id, s.adet, s.aktif_mi, p.id as pid, p.ad as pad, p.adres, p.koordinat_x, p.koordinat_y, " +
                       "m.id as mid, m.ad as mad, m.etken_madde, m.aciklama " +
                       "FROM stoklar s " +
                       "JOIN eczaneler p ON s.eczane_id = p.id " +
                       "JOIN ilaclar m ON s.ilac_id = m.id " +
                       "WHERE m.ad LIKE ? AND s.aktif_mi = TRUE AND s.adet > 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + ilacAdi + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Eczane eczane = new Eczane(rs.getInt("pid"), rs.getString("pad"), rs.getString("adres"), rs.getDouble("koordinat_x"), rs.getDouble("koordinat_y"));
                Ilac ilac = new Ilac(rs.getInt("mid"), rs.getString("mad"), rs.getString("etken_madde"), rs.getString("aciklama"));
                stoklar.add(new Stok(rs.getInt("id"), eczane, ilac, rs.getInt("adet"), rs.getBoolean("aktif_mi")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stoklar;
    }

    public List<Stok> eczaneStoklariniGetir(int eczaneId) {
        List<Stok> stoklar = new ArrayList<>();
        String query = "SELECT s.id, s.adet, s.aktif_mi, m.id as mid, m.ad as mad, m.etken_madde, m.aciklama " +
                       "FROM stoklar s " +
                       "JOIN ilaclar m ON s.ilac_id = m.id " +
                       "WHERE s.eczane_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, eczaneId);
            ResultSet rs = pstmt.executeQuery();
            Eczane sahteEczane = new Eczane(eczaneId, "", "", 0, 0);
            while (rs.next()) {
                Ilac ilac = new Ilac(rs.getInt("mid"), rs.getString("mad"), rs.getString("etken_madde"), rs.getString("aciklama"));
                stoklar.add(new Stok(rs.getInt("id"), sahteEczane, ilac, rs.getInt("adet"), rs.getBoolean("aktif_mi")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stoklar;
    }

    public void stokGuncelle(int stokId, int yeniAdet) {
        String query = "UPDATE stoklar SET adet = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, yeniAdet);
            pstmt.setInt(2, stokId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void durumDegistir(int stokId, boolean yeniDurum) {
        String query = "UPDATE stoklar SET aktif_mi = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setBoolean(1, yeniDurum);
            pstmt.setInt(2, stokId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stokEkle(int eczaneId, int ilacId, int adet) {
        String query = "INSERT INTO stoklar (eczane_id, ilac_id, adet, aktif_mi) VALUES (?, ?, ?, TRUE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, eczaneId);
            pstmt.setInt(2, ilacId);
            pstmt.setInt(3, adet);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
