package com.example.model;

public class Eczaci {
    private int id;
    private String kullaniciAdi;
    private int eczaneId;

    public Eczaci(int id, String kullaniciAdi, int eczaneId) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.eczaneId = eczaneId;
    }

    public int getId() { return id; }
    public String getKullaniciAdi() { return kullaniciAdi; }
    public int getEczaneId() { return eczaneId; }
}
