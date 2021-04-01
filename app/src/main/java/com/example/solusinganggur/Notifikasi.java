package com.example.solusinganggur;

public class Notifikasi {
    int image;
    String name, categories;
 //note
    public Notifikasi(int image, String name, String categories){
        this.image = image;
        this.name = name;
        this.categories = categories;
    }
    public int getImage(){
        return image;
    }
    public String getName(){
        return name;
    }
    public String getCategories(){
        return categories;
    }
}