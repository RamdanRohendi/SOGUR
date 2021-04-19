package com.example.solusinganggur.entity;

public class ListLamaran {
    private String key;
    private String namaPerusahaan;
    private String alamatPerusahaan;
    private String namaHRD;
    private String tglLamar;
    private String status;
    private String koorX;
    private String koorY;

    public ListLamaran() {
    }

    public ListLamaran(String namaPerusahaan, String alamatPerusahaan, String namaHRD, String tglLamar, String status, String koorX, String koorY) {
        this.namaPerusahaan = namaPerusahaan;
        this.alamatPerusahaan = alamatPerusahaan;
        this.namaHRD = namaHRD;
        this.tglLamar = tglLamar;
        this.status = status;
        this.koorX = koorX;
        this.koorY = koorY;
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

    public String getAlamatPerusahaan() {
        return alamatPerusahaan;
    }

    public void setAlamatPerusahaan(String alamatPerusahaan) {
        this.alamatPerusahaan = alamatPerusahaan;
    }

    public String getNamaHRD() {
        return namaHRD;
    }

    public void setNamaHRD(String namaHRD) {
        this.namaHRD = namaHRD;
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

    public String getKoorX() {
        return koorX;
    }

    public void setKoorX(String koorX) {
        this.koorX = koorX;
    }

    public String getKoorY() {
        return koorY;
    }

    public void setKoorY(String koorY) {
        this.koorY = koorY;
    }
}
