package com.example.aplikasicoffee;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CoffeeAdapter extends RecyclerView.ViewHolder {

    ImageView imgCoffee;
    TextView namaCoffee, hargaCoffee;
    RelativeLayout mLayout;
    public CoffeeAdapter(@NonNull View itemView) {
        super(itemView);

        this.imgCoffee = itemView.findViewById(R.id.imgFoto);
        this.namaCoffee = itemView.findViewById(R.id.titleCoffee);
        this.hargaCoffee = itemView.findViewById(R.id.hargaCoffee);
        this.mLayout = itemView.findViewById(R.id.mainLayout);

    }
}
