package com.example.solusinganggur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solusinganggur.R;
import com.example.solusinganggur.entity.DetailPekerjaan;

import java.util.ArrayList;

public class ItemListSearchAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DetailPekerjaan> listDetailPekerjaan;

    public ItemListSearchAdapter(Context mContext, ArrayList<DetailPekerjaan> listDetailPekerjaan) {
        this.mContext = mContext;
        this.listDetailPekerjaan = listDetailPekerjaan;
    }

    @Override
    public int getCount() {
        return listDetailPekerjaan.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridViewSearch;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            gridViewSearch = inflater.inflate(R.layout.item_list_pencarikerja_search, null);
            ImageView gbrPerusahaan = gridViewSearch.findViewById(R.id.img_item_search);
            TextView namaPerusahaan = gridViewSearch.findViewById(R.id.txt_item_search);

            gbrPerusahaan.setImageResource(R.drawable.ic_baseline_business_center_24);
            namaPerusahaan.setText(listDetailPekerjaan.get(i).getNamaPerusahaan());
        } else {
            gridViewSearch = view;
        }

        return gridViewSearch;
    }


}
