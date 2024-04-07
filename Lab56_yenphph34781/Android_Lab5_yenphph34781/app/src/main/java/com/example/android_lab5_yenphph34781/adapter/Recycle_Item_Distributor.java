package com.example.android_lab5_yenphph34781.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_lab5_yenphph34781.R;
import com.example.android_lab5_yenphph34781.handle.Item_Distributor_Handle;
import com.example.android_lab5_yenphph34781.model.Distributor;

import java.util.ArrayList;

public class Recycle_Item_Distributor extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Distributor> ds;
    private Item_Distributor_Handle handle;

    public Recycle_Item_Distributor(Context context, ArrayList<Distributor> ds, Item_Distributor_Handle handle) {
        this.context = context;
        this.ds = ds;
        this.handle = handle;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_distributor, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Distributor distributor = ds.get(position);
        MyViewHolder viewHolder =(MyViewHolder) holder;

        viewHolder.tvName.setText(distributor.getName());
//        xóa
        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handle.onDelete(distributor.getId());
            }
        }) ;
//cap nhat
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Distributor distributor1 = ds.get(position);
                handle.Update(distributor1.getId(), distributor1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvId;
        ImageView btnEdit, btnDel;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtName);
            tvId = itemView.findViewById(R.id.tvId);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDel = itemView.findViewById(R.id.btnDelete);
        }
    }
}
