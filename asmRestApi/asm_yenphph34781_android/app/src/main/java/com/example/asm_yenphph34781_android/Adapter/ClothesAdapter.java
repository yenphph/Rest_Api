package com.example.asm_yenphph34781_android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_yenphph34781_android.R;
import com.example.asm_yenphph34781_android.databinding.ItemClothesBinding;
import com.example.asm_yenphph34781_android.model.Clothes;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Clothes> list;
    private ClothesClick clothesClick;

    public ClothesAdapter(Context context, ArrayList<Clothes> list, ClothesClick clothesClick) {
        this.context = context;
        this.list = list;
        this.clothesClick = clothesClick;
    }
    public interface ClothesClick{
        void delete(Clothes clothes);
        void edit(Clothes clothes);
        void delete(int position);

        void deleteItem(int position, ArrayList<Clothes> list);
    }

    @NonNull
    @Override
    public ClothesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemClothesBinding binding = ItemClothesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesAdapter.ViewHolder holder, int position) {
        Clothes clothes = list.get(position);
        if(list != null && position < list.size()){

            holder.binding.name.setText(clothes.getName());
            holder.binding.description.setText(clothes.getDescription());
            holder.binding.price.setText("Price: " + Integer.toString(list.get(position).getPrice()));
            if(!clothes.getImage().isEmpty()){
                String url = clothes.getImage().get(0);
                String newurl = url.replace("localhost", "10.0.2.2");
                Glide.with(context)
                        .load(newurl)
                        .thumbnail(Glide.with(context).load(R.drawable.ic_launcher_background))
                        .into(holder.binding.img);
                Log.d("321321", "onBindViewHolder: " + url);

            }else{

            }
        }else{

        }

        // Khi người dùng nhấn vào nút xóa
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clothesClick != null) {
                    clothesClick.delete(position); // Gọi phương thức deleteItem
                }
            }
        });
        // Khi người dùng nhấn vào nút cập nhật
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clothesClick != null) {
                    clothesClick.edit(list.get(position)); // Gọi phương thức edit
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemClothesBinding binding;
        public ViewHolder(ItemClothesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
