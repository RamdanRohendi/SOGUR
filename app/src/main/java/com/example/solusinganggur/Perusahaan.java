package com.example.solusinganggur;

public class Perusahaan {
    private int image;
    private String nama;
    private String alamat;
    private String lamalowongan;

    public Perusahaan(int image, String nama, String alamat, String lamalowongan){
        this.image = image;
        this.nama = nama;
        this.alamat = alamat;
        this.lamalowongan = lamalowongan;
    }

    public int getImage() {
        return image;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getLamalowongan() {
        return lamalowongan;
    }
}
