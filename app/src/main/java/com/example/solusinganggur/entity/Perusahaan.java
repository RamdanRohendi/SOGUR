package com.example.solusinganggur.entity;

public class Perusahaan extends User {
    public Perusahaan() {
    }

    public Perusahaan(String namaPerusahaan, String email, String alamat, String tentangPerusahaan, String nomorTelepon) {
        super(namaPerusahaan, email, alamat, tentangPerusahaan, nomorTelepon);
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
