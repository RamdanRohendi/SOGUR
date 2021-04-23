package com.example.solusinganggur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.solusinganggur.adapter.ItemListSearchAdapter;
import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.Pekerjaan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PencariKerjaSearchFragment extends Fragment {
    private GridView gridView;
    private ArrayList<DetailPekerjaan> listDetailPekerjaan;
    private ItemListSearchAdapter searchAdapter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pencarikerja_search, container, false);

        mAuth = FirebaseAuth.getInstance();

        gridView = root.findViewById(R.id.grid_search);

        getData();
        searchView = (SearchView) root.findViewById(R.id.txt_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();

                ArrayList<DetailPekerjaan> pekerjaans = new ArrayList<>();
                for (DetailPekerjaan data : listDetailPekerjaan){
                    String namaPerusahaan = data.getNamaPerusahaan().toLowerCase();
                    if (namaPerusahaan.contains(s)) {
                        pekerjaans.add(data);
                    }
                }
                searchAdapter.setFilter(pekerjaans);

                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), listDetailPekerjaan.get(i).getNamaPerusahaan(), Toast.LENGTH_SHORT).show();
                double koorX = Double.parseDouble(listDetailPekerjaan.get(i).getKoordinatX());
                double koorY = Double.parseDouble(listDetailPekerjaan.get(i).getKoordinatY());
                String keyPekerjaan = listDetailPekerjaan.get(i).getKey();
                String namaPerusahaan = listDetailPekerjaan.get(i).getNamaPerusahaan();
                String emailPerusahaan = listDetailPekerjaan.get(i).getEmailPerusahaan();
                String namaHRD = listDetailPekerjaan.get(i).getNamaHRD();
                String detailAlamat = listDetailPekerjaan.get(i).getAlamatPerusahaan();
                String tglLowongan = listDetailPekerjaan.get(i).getWaktuLowongan();
                String deskJob = listDetailPekerjaan.get(i).getDeskripsiPekerjaan();

                Intent statusLowongan = new Intent(getActivity(), PencariKerjaDetailSearchActivity.class);
                statusLowongan.putExtra("keyPekerjaan", keyPekerjaan);
                statusLowongan.putExtra("namaPerusahaan", namaPerusahaan);
                statusLowongan.putExtra("emailPerusahaan", emailPerusahaan);
                statusLowongan.putExtra("namaHRD", namaHRD);
                statusLowongan.putExtra("detailAlamat", detailAlamat);
                statusLowongan.putExtra("tglLowongan", tglLowongan);
                statusLowongan.putExtra("deskJob", deskJob);
                statusLowongan.putExtra("koorX", koorX);
                statusLowongan.putExtra("koorY", koorY);
                startActivity(statusLowongan);
            }
        });

        return root;
    }

    private void getData() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("pekerjaan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDetailPekerjaan = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pekerjaan pekerjaan = dataSnapshot.getValue(Pekerjaan.class);

                    pekerjaan.getData().setKey(dataSnapshot.getKey());
                    if (pekerjaan.isPublish()) {
                        listDetailPekerjaan.add(pekerjaan.getData());
                    }
                }

                searchAdapter = new ItemListSearchAdapter(getActivity(), listDetailPekerjaan);
                gridView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });
    }
}