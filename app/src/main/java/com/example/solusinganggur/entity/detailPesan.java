package com.example.solusinganggur.entity;

public class detailPesan {
    private String key;
    private String pengirim;
    private String pesan;

    public detailPesan() {
    }

    public detailPesan(String pengirim, String pesan) {
        this.pengirim = pengirim;
        this.pesan = pesan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getPengirim() {
        return this.pengirim;
    }

    public String getPesan() {
        return this.pesan;
    }
}

