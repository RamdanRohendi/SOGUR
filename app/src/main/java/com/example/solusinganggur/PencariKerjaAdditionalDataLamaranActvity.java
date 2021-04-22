package com.example.solusinganggur;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.solusinganggur.entity.DetailPekerjaan;
import com.example.solusinganggur.entity.FileDataLamaran;
import com.example.solusinganggur.entity.ListLamaran;
import com.example.solusinganggur.entity.PencariKerja;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PencariKerjaAdditionalDataLamaranActvity extends AppCompatActivity {
    private static final String TAG = "AddLamaranActivity";
    final static int PICK_PDF_CODE = 2342;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String getUserID;
    private StorageReference mStorageReference;

    private DetailPekerjaan detailPekerjaan;
    private String keyPekerjaan;
    private String namaPerusahaan;
    private String emailPerusahaan;
    private String namaHRD;
    private String detailAlamat;
    private String tglLowongan;
    private String deskJob;
    private double koorX;
    private double koorY;
    private String ketData;

    private EditText edtNama;
    private EditText edtAlamat;
    private EditText edtNoTelp;
    private EditText edtEmail;
    private TextView txtdataCV;
    private TextView txtdataLamaran;

    private Button attachFileCV;
    private Button attachFileLamaran;
    private Button btnSerahkan;

    private FileDataLamaran dataLamaranCV;
    private FileDataLamaran dataLamaranSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarikerja_additional_data_lamaran);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        getUserID = user.getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        edtNama = findViewById(R.id.input_nama);
        edtAlamat = findViewById(R.id.input_alamat);
        edtNoTelp = findViewById(R.id.input_nomor_telepon);
        edtEmail = findViewById(R.id.input_email);
        txtdataCV = findViewById(R.id.data_cv);
        txtdataLamaran = findViewById(R.id.data_lamaran);

        keyPekerjaan = getIntent().getExtras().getString("keyPekerjaan");
        namaPerusahaan = getIntent().getExtras().getString("namaPerusahaan");
        emailPerusahaan = getIntent().getExtras().getString("emailPerusahaan");
        namaHRD = getIntent().getExtras().getString("namaHRD");
        detailAlamat = getIntent().getExtras().getString("detailAlamat");
        tglLowongan = getIntent().getExtras().getString("tglLowongan");
        deskJob = getIntent().getExtras().getString("deskJob");
        koorX = getIntent().getExtras().getDouble("koorX");
        koorY = getIntent().getExtras().getDouble("koorY");
        String SkoorX = String.valueOf(koorX);
        String SkoorY = String.valueOf(koorY);

        detailPekerjaan = new DetailPekerjaan(namaPerusahaan, namaHRD, detailAlamat, emailPerusahaan, tglLowongan, deskJob, SkoorX, SkoorY);
        detailPekerjaan.setKey(keyPekerjaan);

        reference.child("user").child(getUserID).child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PencariKerja pencariKerja = snapshot.getValue(PencariKerja.class);
                if (pencariKerja != null) {
                    edtNama.setText(pencariKerja.getNamaLengkap());
                    edtEmail.setText(pencariKerja.getEmail());
                    edtAlamat.setText(pencariKerja.getAlamat());
                    edtNoTelp.setText(pencariKerja.getNomorTelepon());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MyData", error.getDetails() + " " + error.getMessage());
            }
        });

        attachFileCV = findViewById(R.id.attach_file_cv);
        attachFileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ketData = "CV";
                getPDF();
            }
        });

        attachFileLamaran = findViewById(R.id.attach_file_lamaran);
        attachFileLamaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ketData = "Lamaran";
                getPDF();
            }
        });

        txtdataCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlDown = dataLamaranCV.getUrl();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlDown));
                startActivity(intent);
            }
        });

        txtdataLamaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlDown = dataLamaranSurat.getUrl();

                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse(urlDown));
                startActivity(intent2);
            }
        });

        btnSerahkan = findViewById(R.id.btn_serahkan);
        btnSerahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serahkanLamaran();

                if (edtNama.getText().toString().trim().isEmpty()) {
                    edtNama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    edtEmail.setError("Email tidak boleh kosong");
                    return;
                }

                if (edtAlamat.getText().toString().trim().isEmpty()) {
                    edtAlamat.setError("Alamat tidak boleh kosong");
                    return;
                }

                if (edtNoTelp.getText().toString().trim().isEmpty()) {
                    edtNoTelp.setError("Nomor Telepon tidak boleh kosong");
                    return;
                }
            }
        });
    }

    private void serahkanLamaran() {
        Log.d(TAG, "daftar");
        if (!validateForm()) {
            return;
        }

        String nama = edtNama.getText().toString().trim();
        String alamat = edtAlamat.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String notlp = edtNoTelp.getText().toString().trim();
        String tgl = new SimpleDateFormat("EEEE, d MMMM yyyy").format(Calendar.getInstance().getTime());

        PencariKerja pencariKerja = new PencariKerja(email, alamat, nama, notlp);
        ListLamaran lamaran = new ListLamaran(detailPekerjaan.getNamaPerusahaan(), detailPekerjaan.getAlamatPerusahaan(), detailPekerjaan.getNamaHRD(), tgl, "pending", detailPekerjaan.getKoordinatX(), detailPekerjaan.getKoordinatY());
        lamaran.setCv(dataLamaranCV);
        lamaran.setSuratLamaran(dataLamaranSurat);

        reference.child("user").child(getUserID).child("lowongan_pekerjaan").child(keyPekerjaan).setValue(lamaran);
        reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).setValue(pencariKerja).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).child("cv").setValue(dataLamaranCV);
                reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).child("surat").setValue(dataLamaranSurat);
                reference.child("pekerjaan").child(keyPekerjaan).child("pelamar").child(getUserID).child("status").setValue("pending");
                startActivity(new Intent(getApplicationContext(), PencariKerjaKonfirmasiAdditionalDataLamaran.class));
                finish();
            }
        });
    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(edtNama.getText().toString())) {
            result = false;
        } else {
            edtNama.setError(null);
        }

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            result = false;
        } else {
            edtEmail.setError(null);
        }

        return result;
    }

    private void getPDF() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
               ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                       PackageManager.PERMISSION_GRANTED) {
           Toast.makeText(getApplicationContext(), "Please Allow App to Read Your Storage", Toast.LENGTH_LONG).show();
           requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
           return;
       }

       Intent intent = new Intent();
       intent.setType("application/pdf");
       intent.setAction(Intent.ACTION_GET_CONTENT);
       startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                uploadFile(data.getData());
            } else {
                Toast.makeText(this, "No FIle Chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {
        StorageReference sRef = mStorageReference.child("file/" + getUserID + "/" + keyPekerjaan + "/" + edtNama.getText().toString().trim() + "_" + ketData + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (ketData.equals("CV")) {
                            txtdataCV.setText(edtNama.getText().toString().trim() + "_CV.pdf");
                        }

                        if (ketData.equals("Lamaran")) {
                            txtdataLamaran.setText(edtNama.getText().toString().trim() + "_Lamaran.pdf");
                        }

                        mStorageReference.child("file/" + getUserID + "/" + keyPekerjaan + "/" + edtNama.getText().toString().trim() + "_" + ketData + ".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (ketData.equals("CV")) {
                                    dataLamaranCV = new FileDataLamaran(edtNama.getText().toString().trim() + "_CV.pdf", uri.toString());
                                    reference.child("file").child(getUserID).child(keyPekerjaan).child("CV").setValue(dataLamaranCV);
                                }

                                if (ketData.equals("Lamaran")) {
                                    dataLamaranSurat = new FileDataLamaran(edtNama.getText().toString().trim() + "_Lamaran.pdf", uri.toString());
                                    reference.child("file").child(getUserID).child(keyPekerjaan).child("Lamaran").setValue(dataLamaranSurat);
                                }
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progres = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        if (ketData.equals("CV")) {
                            txtdataCV.setText((int) progres + "% Uploading");
                        }
                        if (ketData.equals("Lamaran")) {
                            txtdataLamaran.setText((int) progres + "% Uploading");
                        }
                    }
                });
    }

    public void kembali(View view) {
        finish();
    }
}
