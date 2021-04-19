package com.example.solusinganggur.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solusinganggur.PencariKerjaDetailAcceptedActivity;
import com.example.solusinganggur.R;
import com.example.solusinganggur.entity.ListLamaran;

import java.util.ArrayList;

public class ItemListAcceptAdapter extends RecyclerView.Adapter<ItemListAcceptAdapter.ViewHolder> {
    private ArrayList<ListLamaran> lamarans;
    private Context context;

    public ItemListAcceptAdapter(ArrayList<ListLamaran> lamarans, Context context) {
        this.lamarans = lamarans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pencarikerja_accepted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String namaPerusahaan = lamarans.get(position).getNamaPerusahaan();
        final String tglMelamar = lamarans.get(position).getTglLamar();
        final String koorX = lamarans.get(position).getKoorX();
        final String koorY = lamarans.get(position).getKoorY();

        holder.namaPerusahaan.setText(namaPerusahaan);
        holder.tglLamar.setText(tglMelamar);
        holder.itemAccept.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                double DkoorX = Double.parseDouble(koorX);
                double DkoorY = Double.parseDouble(koorY);

                Intent detailLowongan = new Intent(context, PencariKerjaDetailAcceptedActivity.class);
                detailLowongan.putExtra("keyLowongan", lamarans.get(position).getKey());
                detailLowongan.putExtra("namaPerusahaan", lamarans.get(position).getNamaPerusahaan());
                detailLowongan.putExtra("lokasiPerusahaan", lamarans.get(position).getAlamatPerusahaan());
                detailLowongan.putExtra("namaHRD", lamarans.get(position).getNamaHRD());
                detailLowongan.putExtra("tglLowongan", lamarans.get(position).getTglLamar());
                detailLowongan.putExtra("koorX", DkoorX);
                detailLowongan.putExtra("koorY", DkoorY);
                context.startActivity(detailLowongan);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lamarans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPerusahaan;
        private TextView tglLamar;
        private CardView itemAccept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPerusahaan = itemView.findViewById(R.id.company);
            tglLamar = itemView.findViewById(R.id.tgl_lamar);
            itemAccept = itemView.findViewById(R.id.list_accept);
        }
    }
}
