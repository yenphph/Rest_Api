package com.example.ams_yenphph34781;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder>{
    private List<Clothes> itemList;
    private Context context;
    private APIService apiService;
    public Adapter(List<Clothes> itemList, Context context, APIService apiService) {
        this.itemList = itemList;
        this.context = context;
        this.apiService = apiService;
    }
    public void refreshData(List<Clothes> newData) {
        itemList.clear();
        itemList.addAll(newData);
        notifyDataSetChanged();
    }
    public void deleteItem(int position) {
        Clothes clothesToDelete = itemList.get(position);
        String id = clothesToDelete.get_id();

        apiService.deleteClothes(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa thành công từ MongoDB, tiếp tục load lại danh sách từ MongoDB
                    loadDataFromMongoDB();
                } else {
                    Toast.makeText(context, "Failed to delete item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loadDataFromMongoDB() {
        Call<List<Clothes>> call = apiService.getClothes();
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                if(response.isSuccessful()){
                    List<Clothes> newData = response.body();
                    updateData(newData);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }
    @NonNull
    @Override
    public Adapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemclothes, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ItemViewHolder holder, int position) {
        Clothes clothes = itemList.get(position);
        holder.typeTextView.setText("Type: " + clothes.getType());
        holder.nameTextView.setText("Name: " + clothes.getName());
        holder.priceTextView.setText("Price: " + clothes.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến Activity Add_Update và truyền đối tượng Clothes
                Intent intent = new Intent(context, Add_Update.class);
                intent.putExtra("clothes", clothes); // Truyền đối tượng Clothes qua Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Thêm cờ FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent);
            }
        });

        // Xử lý sự kiện nhấn vào nút "Xóa"
        holder.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateData(List<Clothes> newData) {
        itemList.clear();
        itemList.addAll(newData);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;
        TextView nameTextView;
        TextView priceTextView;
        TextView deleteTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            deleteTextView = itemView.findViewById(R.id.deleteTextView);
        }
    }
    public void  getDATA(  List<Clothes> newData){
        Call<List<Clothes>> call = apiService.getClothes();
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                if(response.isSuccessful()){
                    itemList.clear();
                    itemList.addAll(newData);
                    notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }
}
