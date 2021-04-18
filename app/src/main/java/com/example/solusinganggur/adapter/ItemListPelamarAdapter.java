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

import com.example.solusinganggur.PencariKerjaAdditionalDataLamaranActvity;
import com.example.solusinganggur.PencariKerjaDetailPendingActivity;
import com.example.solusinganggur.PerusahaanDetailDaftarPelamarActivity;
import com.example.solusinganggur.R;
import com.example.solusinganggur.entity.PencariKerja;

import java.util.ArrayList;

public class ItemListPelamarAdapter extends RecyclerView.Adapter<ItemListPelamarAdapter.ViewHolder> {
    private ArrayList<PencariKerja> listPencariKerja;
    private Context context;

    public ItemListPelamarAdapter(ArrayList<PencariKerja> listPencariKerja, Context context) {
        this.listPencariKerja = listPencariKerja;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_perusahaan_daftar_pelamar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String namaPelamar = listPencariKerja.get(position).getNamaLengkap();
        final String keyPelamar = listPencariKerja.get(position).getKey();

        holder.namaPelamar.setText(namaPelamar);
        holder.itemPelamar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent detailPelamar = new Intent(context, PerusahaanDetailDaftarPelamarActivity.class);
                detailPelamar.putExtra("keyPelamar", keyPelamar);
                context.startActivity(detailPelamar);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPencariKerja.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPelamar;
        private CardView itemPelamar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPelamar = itemView.findViewById(R.id.nama);
            itemPelamar = itemView.findViewById(R.id.item_pelamar);
        }
    }
}
