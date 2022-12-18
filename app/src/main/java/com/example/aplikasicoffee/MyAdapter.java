package com.example.aplikasicoffee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<CoffeeAdapter> {

    Context context;
    ArrayList<Coffee> listCoffee;

    public MyAdapter(Context context, ArrayList<Coffee> listCoffee) {
        this.context = context;
        this.listCoffee = listCoffee;
    }

    @NonNull
    @Override
    public CoffeeAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        return new CoffeeAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeAdapter holder, @SuppressLint("RecyclerView") int position) {
        holder.namaCoffee.setText(listCoffee.get(position).getNama());
        holder.hargaCoffee.setText(listCoffee.get(position).getHarga());
        holder.imgCoffee.setImageResource(listCoffee.get(position).getImage());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mLayout.getContext(), listCoffee.get(position).getNama(), Toast.LENGTH_SHORT).show();
                Intent it = new Intent(holder.mLayout.getContext(), DetailCoffee.class);
                it.putExtra("NamaCoffee", listCoffee.get(position).getNama());
                it.putExtra("HargaCoffee", listCoffee.get(position).getHarga());
                it.putExtra("ImageCoffee", listCoffee.get(position).getImage());
                it.putExtra("DeskripsiCoffee", listCoffee.get(position).getDeskripsi());
                holder.mLayout.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCoffee.size();
    }
}
