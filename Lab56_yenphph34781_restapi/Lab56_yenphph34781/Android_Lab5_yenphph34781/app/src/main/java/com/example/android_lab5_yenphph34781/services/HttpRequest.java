package com.example.android_lab5_yenphph34781.services;
//b4
import static com.example.android_lab5_yenphph34781.services.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiServices requestInterface;
    public  HttpRequest(){
//        tạo retrofit
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);
    }
    public  ApiServices callApi(){
        return  requestInterface;
    }
}
