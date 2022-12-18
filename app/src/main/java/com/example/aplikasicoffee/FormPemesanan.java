package com.example.aplikasicoffee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormPemesanan extends AppCompatActivity {

    private EditText edtNama, edtAlamat, edtJmlPesanan, edtHarga, edtKeterangan;
    private Button addCart;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pemesanan);

        edtNama = findViewById(R.id.nameCoffee);
        edtAlamat = findViewById(R.id.alamat);
        edtJmlPesanan = findViewById(R.id.jumlahPesanan);
        edtHarga = findViewById(R.id.harga);
        edtKeterangan = findViewById(R.id.keterangan);
        addCart = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(FormPemesanan.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        addCart.setOnClickListener(v -> {
            if(edtNama.getText().length() > 0 && edtAlamat.getText().length() > 0 && edtJmlPesanan.getText().length() > 0 && edtHarga.getText().length() > 0 && edtKeterangan.getText().length() > 0)
            {
                // Save Data
                saveData(edtNama.getText().toString(), edtAlamat.getText().toString(), edtJmlPesanan.getText().toString(), edtHarga.getText().toString(), edtKeterangan.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan Isi Data Dengan Lengkap !", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        if(intent != null) {
            id = intent.getStringExtra("id");
            edtNama.setText(intent.getStringExtra("nama"));
            edtAlamat.setText(intent.getStringExtra("alamat"));
            edtJmlPesanan.setText(intent.getStringExtra("jumlah pesanan"));
            edtHarga.setText(intent.getStringExtra("harga"));
            edtKeterangan.setText(intent.getStringExtra("keterangan"));
        }
    }

    private void saveData(String nama, String alamat, String jumlah_pesanan, String harga, String keterangan)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("nama", nama);
        user.put("alamat", alamat);
        user.put("jumlah pesanan", jumlah_pesanan);
        user.put("harga", harga);
        user.put("keterangan", keterangan);

        progressDialog.show();
        if(id!=null)
        {
            // Edit Data
            db.collection("keranjang").document(id)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else
        {
            // Insert value ke database
            db.collection("keranjang")
                    .add(user)
                    // Jika Berhasil Insert Data
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    // Jika Gagal Insert Data
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}