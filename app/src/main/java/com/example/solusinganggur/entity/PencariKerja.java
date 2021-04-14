package com.example.solusinganggur.entity;

public class PencariKerja {
    private String key;
    private String namaLengkap;
    private String email;

    public PencariKerja() {
    }

    public PencariKerja(String namaLengkap, String email) {
        this.namaLengkap = namaLengkap;
        this.email = email;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public String getKey() {
        return key;
    }
}
