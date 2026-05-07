package com.example.model;

public class Ilac {
    private int id;
    private String ad;
    private String etkenMadde;
    private String aciklama;

    public Ilac(int id, String ad, String etkenMadde, String aciklama) {
        this.id = id;
        this.ad = ad;
        this.etkenMadde = etkenMadde;
        this.aciklama = aciklama;
    }

    public int getId() { return id; }
    public String getAd() { return ad; }
    public String getEtkenMadde() { return etkenMadde; }
    public String getAciklama() { return aciklama; }

    @Override
    public String toString() { return ad; }
}
