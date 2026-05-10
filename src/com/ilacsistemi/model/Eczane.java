package com.ilacsistemi.model;

public class Eczane {
    private int id;
    private String eczaneAdi;
    private String adres;
    private double enlem;
    private double boylam;

    public Eczane(int id, String eczaneAdi, String adres, double enlem, double boylam) {
        this.id = id;
        this.eczaneAdi = eczaneAdi;
        this.adres = adres;
        this.enlem = enlem;
        this.boylam = boylam;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEczaneAdi() { return eczaneAdi; }
    public void setEczaneAdi(String eczaneAdi) { this.eczaneAdi = eczaneAdi; }
    public String getAdres() { return adres; }
    public void setAdres(String adres) { this.adres = adres; }
    public double getEnlem() { return enlem; }
    public void setEnlem(double enlem) { this.enlem = enlem; }
    public double getBoylam() { return boylam; }
    public void setBoylam(double boylam) { this.boylam = boylam; }

    @Override
    public String toString() {
        return eczaneAdi; // ComboBox gösterimi için
    }
}
