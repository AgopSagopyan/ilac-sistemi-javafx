package com.example.model;

public class Eczane {
    private int id;
    private String ad;
    private String adres;
    private double koordinatX;
    private double koordinatY;

    public Eczane(int id, String ad, String adres, double koordinatX, double koordinatY) {
        this.id = id;
        this.ad = ad;
        this.adres = adres;
        this.koordinatX = koordinatX;
        this.koordinatY = koordinatY;
    }

    public int getId() { return id; }
    public String getAd() { return ad; }
    public String getAdres() { return adres; }
    public double getKoordinatX() { return koordinatX; }
    public double getKoordinatY() { return koordinatY; }
    
    // Calculates simple distance to a user coordinate (mocking Google Maps)
    public double distanceTo(double userX, double userY) {
        return Math.sqrt(Math.pow(this.koordinatX - userX, 2) + Math.pow(this.koordinatY - userY, 2));
    }

    @Override
    public String toString() { return ad; }
}
