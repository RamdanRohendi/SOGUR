package com.example.solusinganggur.entity;

public class ListLamaran {
    private String key;
    private String namaPerusahaan;
    private String tglLamar;
    private String status;

    public ListLamaran() {
    }

    public ListLamaran(String namaPerusahaan, String tglMelamar) {
        this.namaPerusahaan = namaPerusahaan;
        this.tglLamar = tglMelamar;
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

    public String getTglLamar() {
        return tglLamar;
    }

    public void setTglLamar(String tglMelamar) {
        this.tglLamar = tglMelamar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
