package com.example.lab1_yenphph34781;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add extends AppCompatActivity {
    FirebaseFirestore db;
    private CityAdapter adapter;
    EditText cityNameEditText, stateEditText, countryEditText, populationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = FirebaseFirestore.getInstance();
        cityNameEditText = findViewById(R.id.cityNameEditText);
        stateEditText = findViewById(R.id.stateEditText);
        countryEditText = findViewById(R.id.countryEditText);
        populationEditText = findViewById(R.id.populationEditText);

        findViewById(R.id.addCityButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCityToFirestore();
            }
        });
    }

    private void addCityToFirestore() {
        String cityName = cityNameEditText.getText().toString().trim();
        String state = stateEditText.getText().toString().trim();
        String country = countryEditText.getText().toString().trim();
        String populationString = populationEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(country) && !TextUtils.isEmpty(populationString)) {
            int population = Integer.parseInt(populationString);

            Map<String, Object> city = new HashMap<>();
            city.put("name", cityName);
            city.put("state", state);
            city.put("country", country);
            city.put("population", population);

            db.collection("cities")
                    .add(city)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(Add.this, "Thành phố đã được thêm vào Firestore", Toast.LENGTH_SHORT).show();
                            loadCitiesFromFirestore();
                            startActivity(new Intent(Add.this, Home.class));
                        }
                    });
        } else {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin thành phố", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCitiesFromFirestore() {
        db.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<City> cityList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                City city = document.toObject(City.class);
                                cityList.add(city);
                            }
                            adapter.updateCityList(cityList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
