package com.example.dao;

import com.example.model.Eczaci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EczaciDAO {
    public Eczaci girisYap(String kullaniciAdi, String sifre) {
        String query = "SELECT * FROM eczacilar WHERE kullanici_adi = ? AND sifre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, kullaniciAdi);
            pstmt.setString(2, sifre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Eczaci(
                        rs.getInt("id"),
                        rs.getString("kullanici_adi"),
                        rs.getInt("eczane_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
