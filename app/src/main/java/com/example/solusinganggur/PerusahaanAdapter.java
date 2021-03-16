package com.example.solusinganggur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PerusahaanAdapter extends ArrayAdapter<Perusahaan> {
    public PerusahaanAdapter(@NonNull Context context, ArrayList<Perusahaan> perusahaan) {
        super(context, 0, perusahaan);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Perusahaan perusahaan = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.desainlistmenu, parent, false);
        }

        ImageView imagePt = convertView.findViewById(R.id.imagePt);
        TextView namaPt = convertView.findViewById(R.id.namaPt);
        TextView alamatPt = convertView.findViewById(R.id.alamatPt);
        TextView lamaLowongan = convertView.findViewById(R.id.tglLowongan);

        imagePt.setImageResource(perusahaan.getImage());
        namaPt.setText(perusahaan.getNama());
        alamatPt.setText(perusahaan.getAlamat());
        lamaLowongan.setText(perusahaan.getLamalowongan());

        return convertView;
    }
}
