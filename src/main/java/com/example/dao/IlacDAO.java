package com.example.dao;

import com.example.model.Ilac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IlacDAO {
    public List<Ilac> getEsdagerIlaclar(String etkenMadde, int haricIlacId) {
        List<Ilac> esdegerler = new ArrayList<>();
        String query = "SELECT * FROM ilaclar WHERE etken_madde = ? AND id != ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, etkenMadde);
            pstmt.setInt(2, haricIlacId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                esdegerler.add(new Ilac(
                        rs.getInt("id"),
                        rs.getString("ad"),
                        rs.getString("etken_madde"),
                        rs.getString("aciklama")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esdegerler;
    }

    public int ilacEkle(Ilac ilac) {
        String query = "INSERT INTO ilaclar (ad, etken_madde, aciklama) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, ilac.getAd());
            pstmt.setString(2, ilac.getEtkenMadde());
            pstmt.setString(3, ilac.getAciklama());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
