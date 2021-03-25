package com.example.solusinganggur;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotifPelamarActivity extends AppCompatActivity {

    List<Notifikasi> notifikasiList;

    ListView listNotifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_rolepelamar);

        notifikasiList = new ArrayList<>();
        listNotifikasi = (ListView)findViewById(R.id.listNotifikasi);

        notifikasiList.add(new Notifikasi(R.drawable.bell_notifBlue, "CV Anda telah diterima!",
                "To see details click this notification."));
        notifikasiList.add(new Notifikasi(R.drawable.bell_notifGreen, "CV Anda telah dikirim",
                "To see details click this notification."));
        notifikasiList.add(new Notifikasi(R.drawable.bell_notifYellow, "Selamat Datang di Sogur",
                "To see details click this notification."));
        notifikasiList.add(new Notifikasi(R.drawable.bell_notifRed, "CV Anda Tidak Terkirim",
                "To see details click this notification"));

        NotifPelamarAdapter adapter = new NotifPelamarAdapter(this, R.layout.notifikasi_pelamar_list, notifikasiList);

        listNotifikasi.setAdapter(adapter);
    }
}
