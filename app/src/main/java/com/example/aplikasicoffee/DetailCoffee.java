package com.example.aplikasicoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailCoffee extends AppCompatActivity {

    private ImageView imgDetail;
    private TextView detailNama, descCoffee, detailHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_coffee);

        imgDetail = findViewById(R.id.imgDetail);
        detailNama = findViewById(R.id.txtDetailNama);
        descCoffee = findViewById(R.id.txtDesc);
        detailHarga = findViewById(R.id.txtDetailHarga);

        // Load Data
        getData();

    }
    @SuppressLint("SetTextI18n")
    public void getData()
    {
        int imageDetail = getIntent().getIntExtra("ImageCoffee", 0);
        String namaCoffee = getIntent().getStringExtra("NamaCoffee");
        String ketCoffee = getIntent().getStringExtra("DeskripsiCoffee");
        String hargaCoffee = getIntent().getStringExtra("HargaCoffee");

        imgDetail.setImageResource(imageDetail);
        detailNama.setText(namaCoffee);
        descCoffee.setText(ketCoffee);
        detailHarga.setText(hargaCoffee);
    }
}