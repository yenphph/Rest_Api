package com.example.android_lab5_yenphph34781.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_lab5_yenphph34781.R;
import com.example.android_lab5_yenphph34781.databinding.ItemFruitBinding;
import com.example.android_lab5_yenphph34781.model.Distributor;
import com.example.android_lab5_yenphph34781.model.Fruit;

import java.util.ArrayList;

public class FruitAdapter  extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Fruit> ds;
    private FruitClick fruitClick;

    public FruitAdapter(Context context, ArrayList<Fruit> ds, FruitClick fruitClick) {
        this.context = context;
        this.ds = ds;
        this.fruitClick = fruitClick;
    }

    public interface FruitClick {
        void delete(Fruit fruit);
        void edit(Fruit fruit);
    }

    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {

        if (ds != null && position < ds.size()) {
            Fruit fruit = ds.get(position);
            holder.binding.tvName.setText(fruit.getName());
            holder.binding.tvPriceQuantity.setText("price :" + fruit.getPrice() + " - quantity:" + fruit.getQuantity());
            holder.binding.tvDes.setText(fruit.getDescription());
            if (!fruit.getImage().isEmpty()) {
                String url = fruit.getImage().get(0);
                String newUrl = url.replace("localhost", "10.0.2.2");
                Glide.with(context)
                        .load(newUrl)
                        .thumbnail(Glide.with(context).load(R.drawable.baseline_broken_image_24))
                        .into(holder.binding.img);
                Log.d("321321", "onBindViewHolder: " + url);
            } else {
                // Handle case where image list is empty
            }
        } else {
            // Handle case where list is null or position is out of bounds
        }
    }


    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFruitBinding binding;
        public ViewHolder(ItemFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
