package com.example.aplikasicoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    private EditText edtRegistNama, edtRegistEmail, edtRegistPass, edtConfirmPass;
    private Button btnRegist, btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edtRegistNama = findViewById(R.id.registNama);
        edtRegistEmail = findViewById(R.id.registUser);
        edtRegistPass = findViewById(R.id.registPassword);
        edtConfirmPass = findViewById(R.id.registConfirmPassword);
        btnRegist = findViewById(R.id.btnFormRegist);
        btnLogin = findViewById(R.id.btnFormLogin);

        // Koneksi Firebase
        mAuth = FirebaseAuth.getInstance();

        // Loading Data
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan Tunggu");
        progressDialog.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtRegistNama.getText().length() > 0 && edtRegistEmail.getText().length() > 0 && edtRegistPass.getText().length() > 0 && edtConfirmPass.getText().length() > 0)
                {
                    if (edtRegistPass.getText().toString().equals(edtConfirmPass.getText().toString()))
                    {
                        register(edtRegistNama.getText().toString(), edtRegistEmail.getText().toString(), edtRegistPass.getText().toString());
                    } else {
                        Toast.makeText(getApplicationContext(), "Silahkan Masukkan Password Yang Sama !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Silahkan Isi Data Dengan Lengkap !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void register(String nama, String email, String password)
    {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null)
                {
                    // Sign in success, Update UI with the signed-in user's information
                    // Mengecek apakah user sudah memiliki account
                    FirebaseUser user = task.getResult().getUser();
                    if (user != null)
                    {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(nama).build();

                        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    } else {
                        // Jika register gagal
                        Toast.makeText(getApplicationContext(), "Register Gagal!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
        progressDialog.dismiss();
    }

    private void reload()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}