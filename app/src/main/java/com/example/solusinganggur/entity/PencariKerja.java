package com.example.solusinganggur.entity;

public class PencariKerja {
    private String namaLengkap;
    private String email;
    private String password;

    public PencariKerja() {
    }

    public PencariKerja(String namaLengkap, String email) {
        this.namaLengkap = namaLengkap;
        this.email = email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
