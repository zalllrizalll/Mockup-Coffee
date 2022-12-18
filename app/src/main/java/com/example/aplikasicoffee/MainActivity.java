package com.example.aplikasicoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rec_coffee;
    MyAdapter myAdapter;
    private FirebaseUser firebaseUser;
    private FloatingActionButton addCart;
    private FloatingActionButton logout;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec_coffee = findViewById(R.id.recyclerView);
        rec_coffee.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this, getMyList());
        rec_coffee.setAdapter(myAdapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        addCart = findViewById(R.id.addCart);
        logout = findViewById(R.id.btn_logout);

        // Logout
        logout();

        // Pindah ke form Pemesanan
        addCart.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DaftarKeranjang.class));
        });
    }

    private ArrayList<Coffee> getMyList ()
    {
        ArrayList<Coffee> listCoffee = new ArrayList<>();
        Coffee coffee = new Coffee();
        coffee.setNama("Cappuccino");
        coffee.setHarga("Rp. 25.000");
        coffee.setDeskripsi("Cappuccino adalah latte yang dibuat dengan lebih banyak busa(foam) daripada steamed milk, seringkali ditambah dengan taburan bubuk kakao atau kayu manis di atasnya. Terdapat juga variasi cappuccino yang menggunakan krim sebagai pengganti susu atau yang memberikan penambah rasa");
        coffee.setImage(R.drawable.americano_coffee);
        listCoffee.add(coffee);

        coffee = new Coffee();
        coffee.setNama("Cafe Latte");
        coffee.setHarga("Rp. 20.000");
        coffee.setDeskripsi("Cafe latte adalah jenis minuman kopi perpaduan antara campuran susu sapi dan kopi espresso, tapi komposisi susu di kopi jenis ini mampu menyamarkan pahitnya kopi");
        coffee.setImage(R.drawable.robusta_coffee);
        listCoffee.add(coffee);

        coffee = new Coffee();
        coffee.setNama("Macchiato");
        coffee.setHarga("Rp. 30.000");
        coffee.setDeskripsi("Macchiato adalah jenis minuman kopi berbasis espresso lainnya yang memiliki sedikit busa di atasnya. Kata 'macchiato' berarti tanda atau noda. Ini mengacu pada tanda bahwa steamed milk meninggalkan permukaan espresso saat dituangkan ke dalam minuman");
        coffee.setImage(R.drawable.brewed_coffee);
        listCoffee.add(coffee);

        coffee = new Coffee();
        coffee.setNama("Mocha Latte");
        coffee.setHarga("Rp. 22.000");
        coffee.setDeskripsi("Mocha latte adalah jenis minuman kopi yang pas bagi Anda pecinta kopi. Mocha adalah jenis minuman kopi yang terdiri dari espresso, coklat, steamed milk dan foam. Mocha merupakan perpaduan unik antara kopi dan cokelat panas");
        coffee.setImage(R.drawable.espresso_coffee);
        listCoffee.add(coffee);

        return listCoffee;
    }

    public void logout()
    {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}