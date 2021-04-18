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

import com.example.solusinganggur.AuthLupaPasswordActivity;
import com.example.solusinganggur.PencariKerjaDetailPendingActivity;
import com.example.solusinganggur.PencariKerjaPendingActivity;
import com.example.solusinganggur.R;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.Pekerjaan;

import java.util.ArrayList;

public class ItemListPendingAdapter extends RecyclerView.Adapter<ItemListPendingAdapter.ViewHolder> {
    private ArrayList<ListLamaran> lamarans;
    private Context context;

    public ItemListPendingAdapter(ArrayList<ListLamaran> lamarans, Context context) {
        this.lamarans = lamarans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pencarikerja_pending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String namaPerusahaan = lamarans.get(position).getNamaPerusahaan();
        final String tglMelamar = lamarans.get(position).getTglLamar();

        holder.namaPerusahaan.setText(namaPerusahaan);
        holder.tglLamar.setText(tglMelamar);
        holder.itemPending.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                context.startActivity(new Intent(context, PencariKerjaDetailPendingActivity.class));
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
        private CardView itemPending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPerusahaan = itemView.findViewById(R.id.company);
            tglLamar = itemView.findViewById(R.id.tgl_lamar);
            itemPending = itemView.findViewById(R.id.list_pending);
        }
    }
}