package com.example.solusinganggur.entity;

public class Perusahaan {
    private String key;
    private String role;
    private String namaPerusahaan;
    private String email;
    private String alamat;
    private String tentangPerusahaan;

    public Perusahaan() {
    }

    public Perusahaan(String role, String namaPerusahaan, String email, String alamat, String tentangPerusahaan) {
        this.role = role;
        this.namaPerusahaan = namaPerusahaan;
        this.email = email;
        this.alamat = alamat;
        this.tentangPerusahaan = tentangPerusahaan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTentangPerusahaan() {
        return tentangPerusahaan;
    }

    public void setTentangPerusahaan(String tentangPerusahaan) {
        this.tentangPerusahaan = tentangPerusahaan;
    }
}
