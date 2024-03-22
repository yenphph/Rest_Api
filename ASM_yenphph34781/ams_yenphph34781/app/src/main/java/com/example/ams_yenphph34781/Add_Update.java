package com.example.ams_yenphph34781;

import static com.example.ams_yenphph34781.APIService.DOMAIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Update extends AppCompatActivity {
    private TextInputEditText typeEditText;
    private TextInputEditText nameEditText;
    private TextInputEditText priceEditText;
    private Button saveButton;
    private Button cancelButton;
    private Clothes clothesToUpdate;
    private APIService apiService;
    private Adapter adapter;
    Clothes newItem;
   List<Clothes> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_update);
            typeEditText = findViewById(R.id.typeEditText);
            nameEditText = findViewById(R.id.nameEditText);
            priceEditText = findViewById(R.id.priceEditText);
            saveButton = findViewById(R.id.saveButton);
            cancelButton = findViewById(R.id.cancelButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("clothes")) {
            clothesToUpdate = (Clothes) intent.getSerializableExtra("clothes");
            typeEditText.setText(clothesToUpdate.getType());
            nameEditText.setText(clothesToUpdate.getName());
            priceEditText.setText(String.valueOf(clothesToUpdate.getPrice()));
        }
        adapter = new Adapter(new ArrayList<>(), this, apiService);
        itemList = new ArrayList<>();
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = typeEditText.getText().toString();
                    String name = nameEditText.getText().toString();
                    double price = Double.parseDouble(priceEditText.getText().toString());

                    if (clothesToUpdate != null) {
                        clothesToUpdate.setType(type);
                        clothesToUpdate.setName(name);
                        clothesToUpdate.setPrice(price);
                        updateItemOnServer(clothesToUpdate);
                    } else {
                        addItemToServer(type, name, price);
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }

    private void updateItemOnServer(Clothes clothes) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        Call<Void> call = apiService.updateClothes(clothes.get_id(), clothes);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadDataFromMongoDB();
                    Toast.makeText(Add_Update.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                    loadDataFromMongoDB(); //bị lôi đoạn này
                } else {
                    Log.e("updateItemOnServer", "Unsuccessful response: " + response.code());
                    Toast.makeText(Add_Update.this, "Failed to update item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("updateItemOnServer", "Error updating item: " + t.getMessage());
                Toast.makeText(Add_Update.this, "Failed to update item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataFromMongoDB() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        Call<List<Clothes>> call = apiService.getClothes();
        call.enqueue(new Callback<List<Clothes>>() {
            @Override
            public void onResponse(Call<List<Clothes>> call, Response<List<Clothes>> response) {
                if(response.isSuccessful()){
                    List<Clothes> newData = response.body();
//                    adapter.refreshData(newData);
                    itemList.clear();//sai
                    itemList.addAll(newData);
//                    adapter.updateData(newData);
                    adapter.notifyDataSetChanged();// chỗ này bị
                }
            }

            @Override
            public void onFailure(Call<List<Clothes>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }

    private void addItemToServer(String type, String name, double price) {
        newItem = new Clothes(type, name, price);
        Call<Void> call = apiService.addClothes(newItem);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadDataFromMongoDB();
                    Toast.makeText(Add_Update.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e("addItemToServer", "Unsuccessful response: " + response.code());
                    Toast.makeText(Add_Update.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("addItemToServer", "Error adding item:"
                        + t.getMessage());
                Toast.makeText(Add_Update.this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
