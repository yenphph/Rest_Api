package com.example.android_lab5_yenphph34781.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_lab5_yenphph34781.R;
import com.example.android_lab5_yenphph34781.adapter.FruitAdapter;
import com.example.android_lab5_yenphph34781.databinding.ActivityHomeBinding;
import com.example.android_lab5_yenphph34781.model.Fruit;
import com.example.android_lab5_yenphph34781.model.Response;
import com.example.android_lab5_yenphph34781.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity implements FruitAdapter.FruitClick {
    ActivityHomeBinding binding;
    private HttpRequest httpRequest;
    private SharedPreferences sharedPreferences;
    private String token;
    private FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();
        sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);

        token = sharedPreferences.getString("token","");
        httpRequest.callApi().getListFruit("Bearer " + token).enqueue(getListFruitResponse);
        userListener();
    }
    private void userListener () {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this , AddFruitActivity.class));
            }
        });
    }


//nếu hiên do chõ callback thi là do sai ở apiserver
    Callback<Response<ArrayList<Fruit>>> getListFruitResponse = new Callback<Response<ArrayList<Fruit>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Fruit>>> call, retrofit2.Response<Response<ArrayList<Fruit>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() ==200) {
                    ArrayList<Fruit> ds = response.body().getData();
                    getData(ds);
//                    Toast.makeText(HomeActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Fruit>>> call, Throwable t) {

        }
    };
    private void getData (ArrayList<Fruit> ds) {
        adapter = new FruitAdapter(this, ds,this );
        binding.rcvFruit.setAdapter(adapter);
    }

    @Override
    public void delete(Fruit fruit) {

    }

    @Override
    public void edit(Fruit fruit) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        httpRequest.callApi().getListFruit("Bearer "+token).enqueue(getListFruitResponse);
    }
}