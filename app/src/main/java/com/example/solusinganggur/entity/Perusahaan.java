package com.example.solusinganggur.entity;

public class Perusahaan extends User {
    public Perusahaan() {
    }

    public Perusahaan(String namaPerusahaan, String email, String alamat, String tentangPerusahaan, String nomorTelepon, String namaHRD) {
        super(namaPerusahaan, email, alamat, tentangPerusahaan, nomorTelepon, namaHRD);
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    @Override
    public void setKey(String key) {
        super.setKey(key);
    }

    @Override
    public String getNamaPerusahaan() {
        return super.getNamaPerusahaan();
    }

    @Override
    public void setNamaPerusahaan(String namaPerusahaan) {
        super.setNamaPerusahaan(namaPerusahaan);
    }

    @Override
    public String getNamaHRD() {
        return super.getNamaHRD();
    }

    @Override
    public void setNamaHRD(String namaHRD) {
        super.setNamaHRD(namaHRD);
    }

    @Override
    public String getNomorTelepon() {
        return super.getNomorTelepon();
    }

    @Override
    public void setNomorTelepon(String nomorTelepon) {
        super.setNomorTelepon(nomorTelepon);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getAlamat() {
        return super.getAlamat();
    }

    @Override
    public void setAlamat(String alamat) {
        super.setAlamat(alamat);
    }

    @Override
    public String getTentangPerusahaan() {
        return super.getTentangPerusahaan();
    }

    @Override
    public void setTentangPerusahaan(String tentangPerusahaan) {
        super.setTentangPerusahaan(tentangPerusahaan);
    }
}
