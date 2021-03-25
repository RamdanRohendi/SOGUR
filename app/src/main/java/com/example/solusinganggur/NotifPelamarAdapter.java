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

import java.util.List;

public class NotifPelamarAdapter extends ArrayAdapter<Notifikasi> {
    List<Notifikasi> notifikasiList;

    Context context;

    int resource;

    public NotifPelamarAdapter(Context context, int resource, List<Notifikasi> notifikasiList) {
        super(context, resource, notifikasiList);
        this.context = context;
        this.resource = resource;
        this.notifikasiList = notifikasiList;
    }
    @NonNull

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewInfo = view.findViewById(R.id.textViewInfo);
        TextView textDetails = view.findViewById(R.id.textDetails);

        Notifikasi notifikasi = notifikasiList.get(position);

        imageView.setImageDrawable(context.getResources().getDrawable(notifikasi.getImage()));
            textViewInfo.setText(notifikasi.getName());
            textDetails.setText((notifikasi.getCategories()));

        return view;
    }
}
