package com.ilacsistemi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBBaglanti {
    
    // Veritabanı bağlantısını başlatır
    public static Connection baglantiGetir() {
        Connection baglanti = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            baglanti = DriverManager.getConnection(Ayarlar.DB_URL, Ayarlar.DB_KULLANICI, Ayarlar.DB_SIFRE);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }
        return baglanti;
    }
}
