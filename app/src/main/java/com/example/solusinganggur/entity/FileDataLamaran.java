package com.example.solusinganggur.entity;

public class FileDataLamaran {
    private String nama;
    private String url;

    public FileDataLamaran() {
    }

    public FileDataLamaran(String nama, String url) {
        this.nama = nama;
        this.url = url;
    }

    public String getNama() {
        return nama;
    }

    public String getUrl() {
        return url;
    }
}
