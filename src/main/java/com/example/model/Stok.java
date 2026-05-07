package com.example.model;

public class Stok {
    private int id;
    private Eczane eczane;
    private Ilac ilac;
    private int adet;
    private boolean aktifMi;

    public Stok(int id, Eczane eczane, Ilac ilac, int adet, boolean aktifMi) {
        this.id = id;
        this.eczane = eczane;
        this.ilac = ilac;
        this.adet = adet;
        this.aktifMi = aktifMi;
    }

    public int getId() { return id; }
    public Eczane getEczane() { return eczane; }
    public Ilac getIlac() { return ilac; }
    public int getAdet() { return adet; }
    public boolean isAktifMi() { return aktifMi; }
    
    public void setAdet(int adet) { this.adet = adet; }
    public void setAktifMi(boolean aktifMi) { this.aktifMi = aktifMi; }
}
