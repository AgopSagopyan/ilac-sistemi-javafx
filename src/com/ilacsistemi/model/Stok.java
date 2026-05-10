package com.ilacsistemi.model;

public class Stok {
    private int id;
    private int eczaneId;
    private String eczaneAdi; // JOIN için
    private int ilacId;
    private String ilacAdi; // JOIN için
    private int miktar;

    public Stok(int id, int eczaneId, String eczaneAdi, int ilacId, String ilacAdi, int miktar) {
        this.id = id;
        this.eczaneId = eczaneId;
        this.eczaneAdi = eczaneAdi;
        this.ilacId = ilacId;
        this.ilacAdi = ilacAdi;
        this.miktar = miktar;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEczaneId() { return eczaneId; }
    public void setEczaneId(int eczaneId) { this.eczaneId = eczaneId; }
    public String getEczaneAdi() { return eczaneAdi; }
    public void setEczaneAdi(String eczaneAdi) { this.eczaneAdi = eczaneAdi; }
    public int getIlacId() { return ilacId; }
    public void setIlacId(int ilacId) { this.ilacId = ilacId; }
    public String getIlacAdi() { return ilacAdi; }
    public void setIlacAdi(String ilacAdi) { this.ilacAdi = ilacAdi; }
    public int getMiktar() { return miktar; }
    public void setMiktar(int miktar) { this.miktar = miktar; }
}
