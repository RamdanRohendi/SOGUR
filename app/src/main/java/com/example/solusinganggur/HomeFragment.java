package com.example.solusinganggur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//
//        ArrayList<Perusahaan> perusahaans = new ArrayList<>();
//        PerusahaanAdapter adapter = new PerusahaanAdapter(getActivity(), perusahaans);
//        ListView listView = root.findViewById(R.id.listhome);
//        listView.setAdapter(adapter);
//
//        Perusahaan perusahaan;
//
//        for (int a = 0; a < 5; a++){
//            perusahaan = new Perusahaan(R.drawable.indofood, "Lowongan Kerja PT. Indofood Sukses Makmur Tbk.", "Bandung, Kota Bandung, Jawa Barat", "31 Agustus 2020 - 10 September 2020");
//            adapter.add(perusahaan);
//        }

        return inflater.inflate(R.layout.fragment_home, null);
    }
}