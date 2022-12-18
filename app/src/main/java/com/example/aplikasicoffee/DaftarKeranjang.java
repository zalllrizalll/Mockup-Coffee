package com.example.aplikasicoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasicoffee.adapter.PesananAdapter;
import com.example.aplikasicoffee.model.Pesanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DaftarKeranjang extends AppCompatActivity {

    private RecyclerView rec_keranjang;
    private FloatingActionButton btnAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pesanan> list = new ArrayList<>();
    private PesananAdapter pesananAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemDecoration decoration;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_keranjang);

        rec_keranjang = findViewById(R.id.rec_keranjang);
        btnAdd = findViewById(R.id.btn_add);
        // Progress Dialog
        progressDialog = new ProgressDialog(DaftarKeranjang.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil Data...");

        // Build RecyclerView
        buildRecyclerView();

        // List Pilih Jika Ditekan terdapat 2 pilihan yaitu Edit dan Hapus
        pesananAdapter.setDialog(new PesananAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DaftarKeranjang.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i)
                        {
                            case 0:
                                // Edit Data
                                Intent intent = new Intent(getApplicationContext(), FormPemesanan.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNamaCoffee());
                                intent.putExtra("alamat", list.get(pos).getAlamat());
                                intent.putExtra("jumlah pesanan", list.get(pos).getJumlahPesanan());
                                intent.putExtra("harga", list.get(pos).getHarga());
                                intent.putExtra("keterangan", list.get(pos).getKeterangan());
                                startActivity(intent);
                                break;
                            case 1:
                                // Hapus Data
                                hapusData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        // Pindah ke Form Pemesanan
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FormPemesanan.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Menampilkan Data dari Firestore Database
        tampilData();
    }

    private void buildRecyclerView()
    {
        pesananAdapter = new PesananAdapter(getApplicationContext(), list);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        rec_keranjang.setLayoutManager(layoutManager);
        rec_keranjang.addItemDecoration(decoration);
        rec_keranjang.setAdapter(pesananAdapter);
    }

    private void tampilData()
    {
        progressDialog.show();
        db.collection("keranjang")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        // Jika Data Berhasil Di Ambil
                        if(task.isSuccessful())
                        {
                            // Mengambil Data di Cloud Firestore Database
                            for(QueryDocumentSnapshot document : task.getResult())
                            {
                                Pesanan pesanan = new Pesanan(document.getString("nama"), document.getString("alamat"), document.getString("jumlah pesanan"), document.getString("harga"), document.getString("keterangan"));
                                pesanan.setId(document.getId());
                                list.add(pesanan);
                            }
                            pesananAdapter.notifyDataSetChanged();
                        }
                        // Jika Data Gagal Di Ambil
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Data Gagal Di Ambil!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void hapusData(String id)
    {
        progressDialog.show();
        db.collection("keranjang").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Data Gagal di Hapus!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        tampilData();
                    }
                });
    }
}