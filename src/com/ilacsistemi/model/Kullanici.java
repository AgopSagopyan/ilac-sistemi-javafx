package com.ilacsistemi.model;

public class Kullanici {
    private int id;
    private String ad;
    private String soyad;
    private String email;
    private String sifre;
    private int rolId;
    private int eczaneId; // 0 ise null

    public Kullanici(int id, String ad, String soyad, String email, String sifre, int rolId, int eczaneId) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.sifre = sifre;
        this.rolId = rolId;
        this.eczaneId = eczaneId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }
    public int getRolId() { return rolId; }
    public void setRolId(int rolId) { this.rolId = rolId; }
    public int getEczaneId() { return eczaneId; }
    public void setEczaneId(int eczaneId) { this.eczaneId = eczaneId; }
}
