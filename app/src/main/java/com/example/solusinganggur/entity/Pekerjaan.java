package com.example.solusinganggur.entity;

public class Pekerjaan {
    private String key;
    private String idPerusahaan;
    private String namaPerusahaan;
    private String namaHRD;
    private String alamatPerusahaan;
    private String emailPerusahaan;
    private String waktuLowongan;
    private String deskripsiPekerjaan;
    private String koordinatX;
    private String koordinatY;

    public Pekerjaan() {
    }

    public Pekerjaan(String namaPerusahaan, String namaHRD, String alamatPerusahaan, String emailPerusahaan, String waktuLowongan, String deskripsiPekerjaan, String koordinatX, String koordinatY) {
        this.namaPerusahaan = namaPerusahaan;
        this.namaHRD = namaHRD;
        this.alamatPerusahaan = alamatPerusahaan;
        this.emailPerusahaan = emailPerusahaan;
        this.waktuLowongan = waktuLowongan;
        this.deskripsiPekerjaan = deskripsiPekerjaan;
        this.koordinatX = koordinatX;
        this.koordinatY = koordinatY;
    }

    public Pekerjaan(String namaPerusahaan, String deskripsiPekerjaan) {
        this.namaPerusahaan = namaPerusahaan;
        this.deskripsiPekerjaan = deskripsiPekerjaan;
    }

    public Pekerjaan(String namaPerusahaan, String alamatPerusahaan, String emailPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
        this.alamatPerusahaan = alamatPerusahaan;
        this.emailPerusahaan = emailPerusahaan;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getIdPerusahaan() {
        return idPerusahaan;
    }

    public void setIdPerusahaan(String idPerusahaan) {
        this.idPerusahaan = idPerusahaan;
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

    public String getAlamatPerusahaan() {
        return alamatPerusahaan;
    }

    public void setAlamatPerusahaan(String alamatPerusahaan) {
        this.alamatPerusahaan = alamatPerusahaan;
    }

    public String getEmailPerusahaan() {
        return emailPerusahaan;
    }

    public void setEmailPerusahaan(String emailPerusahaan) {
        this.emailPerusahaan = emailPerusahaan;
    }

    public String getWaktuLowongan() {
        return waktuLowongan;
    }

    public void setWaktuLowongan(String waktuLowongan) {
        this.waktuLowongan = waktuLowongan;
    }

    public String getDeskripsiPekerjaan() {
        return deskripsiPekerjaan;
    }

    public void setDeskripsiPekerjaan(String deskripsiPekerjaan) {
        this.deskripsiPekerjaan = deskripsiPekerjaan;
    }

    public String getKoordinatX() {
        return koordinatX;
    }

    public void setKoordinatX(String koordinatX) {
        this.koordinatX = koordinatX;
    }

    public String getKoordinatY() {
        return koordinatY;
    }

    public void setKoordinatY(String koordinatY) {
        this.koordinatY = koordinatY;
    }
}
