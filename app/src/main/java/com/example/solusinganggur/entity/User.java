package com.example.solusinganggur.entity;

public class User {
    private String role;
    private String key;
    private String namaPerusahaan;
    private String namaHRD;
    private String email;
    private String alamat;
    private String tentangPerusahaan;
    private String namaLengkap;
    private String jenisKelamin;
    private String ttl;
    private String agama;
    private String nomorTelepon;

    public User() {
    }

    public User(String email, String alamat, String namaLengkap, String jenisKelamin, String ttl, String agama, String nomorTelepon) {
        this.email = email;
        this.alamat = alamat;
        this.namaLengkap = namaLengkap;
        this.jenisKelamin = jenisKelamin;
        this.ttl = ttl;
        this.agama = agama;
        this.nomorTelepon = nomorTelepon;
    }

    public User(String namaPerusahaan, String email, String alamat, String tentangPerusahaan, String nomorTelepon, String namaHRD) {
        this.namaPerusahaan = namaPerusahaan;
        this.namaHRD = namaHRD;
        this.email = email;
        this.alamat = alamat;
        this.tentangPerusahaan = tentangPerusahaan;
        this.nomorTelepon = nomorTelepon;
    }

    public User(String email, String alamat, String namaLengkap, String nomorTelepon) {
        this.email = email;
        this.alamat = alamat;
        this.namaLengkap = namaLengkap;
        this.nomorTelepon = nomorTelepon;
    }

    public User(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getNamaHRD() {
        return namaHRD;
    }

    public void setNamaHRD(String namaHRD) {
        this.namaHRD = namaHRD;
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

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}
