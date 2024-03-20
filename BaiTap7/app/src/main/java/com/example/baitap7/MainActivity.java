package com.example.baitap7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Clothes> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        itemList = generateItemList(); // Phương thức này để tạo danh sách các mục
        adapter = new Adapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fabAdd = findViewById(R.id.addButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng sang activity mới khi click vào FAB
                startActivity(new Intent(MainActivity.this, Add_Update.class));
            }
        });
    }

    // Phương thức này tạo danh sách các mục (chỉ để minh họa)
    private List<Clothes> generateItemList() {
        List<Clothes> list = new ArrayList<>();
        list.add(new Clothes("Type1", "Name1", 10.0));
        list.add(new Clothes("Type2", "Name2", 20.0));
        list.add(new Clothes("Type3", "Name3", 30.0));
        // Thêm các mục khác nếu cần
        return list;
    }
}