package com.example.aplikasicoffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasicoffee.R;
import com.example.aplikasicoffee.model.Pesanan;

import java.util.List;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.MyViewHolder>{

    private Context context;
    private List<Pesanan> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog)
    {
        this.dialog = dialog;
    }

    public PesananAdapter(Context context, List<Pesanan> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesanan, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namaCoffee.setText(list.get(position).getNamaCoffee());
        holder.jumlahPesanan.setText(list.get(position).getJumlahPesanan());
        holder.harga.setText(list.get(position).getHarga());
        holder.keterangan.setText(list.get(position).getKeterangan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaCoffee, jumlahPesanan, harga, keterangan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namaCoffee = itemView.findViewById(R.id.namaCoffee);
            jumlahPesanan = itemView.findViewById(R.id.qty);
            harga = itemView.findViewById(R.id.view_harga);
            keterangan = itemView.findViewById(R.id.viewKeterangan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialog!=null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
