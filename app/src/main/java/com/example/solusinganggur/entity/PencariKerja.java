package com.example.solusinganggur.entity;

public class PencariKerja extends User {
    public PencariKerja() {
    }

    public PencariKerja(String email, String alamat, String namaLengkap, String nomorTelepon) {
        super(email, alamat, namaLengkap, nomorTelepon);
    }

    public PencariKerja(String email, String alamat, String namaLengkap, String jenisKelamin, String ttl, String agama, String nomorTelepon) {
        super(email, alamat, namaLengkap, jenisKelamin, ttl, agama, nomorTelepon);
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
    public String getNamaLengkap() {
        return super.getNamaLengkap();
    }

    @Override
    public void setNamaLengkap(String namaLengkap) {
        super.setNamaLengkap(namaLengkap);
    }

    @Override
    public String getJenisKelamin() {
        return super.getJenisKelamin();
    }

    @Override
    public void setJenisKelamin(String jenisKelamin) {
        super.setJenisKelamin(jenisKelamin);
    }

    @Override
    public String getTtl() {
        return super.getTtl();
    }

    @Override
    public void setTtl(String ttl) {
        super.setTtl(ttl);
    }

    @Override
    public String getAgama() {
        return super.getAgama();
    }

    @Override
    public void setAgama(String agama) {
        super.setAgama(agama);
    }

    @Override
    public String getNomorTelepon() {
        return super.getNomorTelepon();
    }

    @Override
    public void setNomorTelepon(String nomorTelepon) {
        super.setNomorTelepon(nomorTelepon);
    }
}
