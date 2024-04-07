package com.example.ams_yenphph34781.server;

import static com.example.ams_yenphph34781.server.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiServices requestInterface;
    public  HttpRequest(){
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);
    }
    public  ApiServices callApi(){
        return requestInterface;
    }
}
