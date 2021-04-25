package com.example.solusinganggur.entity;

import java.util.ArrayList;

public class ChatRoom {
    private String key;
    private String namapencarikerja;
    private String namaperusahaan;
    private ArrayList<detailPesan> data;

    public ChatRoom() {
    }

    public ChatRoom(String namapencarikerja, String namaperusahaan, ArrayList<detailPesan> data) {
        this.namapencarikerja = namapencarikerja;
        this.namaperusahaan = namaperusahaan;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNamapencarikerja() {
        return namapencarikerja;
    }

    public void setNamapencarikerja(String namapencarikerja) {
        this.namapencarikerja = namapencarikerja;
    }

    public String getNamaperusahaan() {
        return namaperusahaan;
    }

    public void setNamaperusahaan(String namaperusahaan) {
        this.namaperusahaan = namaperusahaan;
    }

    public ArrayList<detailPesan> getData() {
        return data;
    }

    public void setData(ArrayList<detailPesan> data) {
        this.data = data;
    }
}
