package com.ilacsistemi.util;

// Ayarlar.java - Basit düzenleme için config dosyası
public class Ayarlar {
    // Veritabanı Bilgileri
    public static final String DB_URL = "jdbc:mysql://localhost:3307/eczane_sistemi?useUnicode=true&characterEncoding=utf8";
    public static final String DB_KULLANICI = "root";
    public static final String DB_SIFRE = ""; // Boş ise boş bırakın

    // SMTP E-Posta Bilgileri (Şifremi Unuttum için)
    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String SMTP_PORT = "587";
    public static final String GONDEREN_EPOSTA = "ornek@gmail.com";
    public static final String EPOSTA_SIFRE = "uygulama_sifresi"; 
}
