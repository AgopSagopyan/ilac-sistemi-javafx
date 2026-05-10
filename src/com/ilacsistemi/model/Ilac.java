package com.ilacsistemi.model;

public class Ilac {
    private int id;
    private String ilacAdi;
    private int turId;
    private String turAdi; // JOIN için
    private String fotografYolu;

    public Ilac(int id, String ilacAdi, int turId, String turAdi, String fotografYolu) {
        this.id = id;
        this.ilacAdi = ilacAdi;
        this.turId = turId;
        this.turAdi = turAdi;
        this.fotografYolu = fotografYolu;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIlacAdi() { return ilacAdi; }
    public void setIlacAdi(String ilacAdi) { this.ilacAdi = ilacAdi; }
    public int getTurId() { return turId; }
    public void setTurId(int turId) { this.turId = turId; }
    public String getTurAdi() { return turAdi; }
    public void setTurAdi(String turAdi) { this.turAdi = turAdi; }
    public String getFotografYolu() { return fotografYolu; }
    public void setFotografYolu(String fotografYolu) { this.fotografYolu = fotografYolu; }

    @Override
    public String toString() {
        return ilacAdi; // ComboBox gösterimi için
    }
}
