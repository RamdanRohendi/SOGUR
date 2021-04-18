package com.example.solusinganggur.entity;

public class Pekerjaan {
    private String key;
    private String idPerusahaan;
    private DetailPekerjaan data;
    private boolean publish;

    public Pekerjaan() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdPerusahaan() {
        return idPerusahaan;
    }

    public void setIdPerusahaan(String idPerusahaan) {
        this.idPerusahaan = idPerusahaan;
    }

    public DetailPekerjaan getData() {
        return data;
    }

    public void setData(DetailPekerjaan data) {
        this.data = data;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
