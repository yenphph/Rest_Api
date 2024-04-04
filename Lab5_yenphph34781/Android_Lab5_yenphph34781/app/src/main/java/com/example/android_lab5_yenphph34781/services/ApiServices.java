package com.example.android_lab5_yenphph34781.services;
//b3

import com.example.android_lab5_yenphph34781.model.Distributor;
//nhớ import cái này
import com.example.android_lab5_yenphph34781.model.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    public static String BASE_URL = "http://192.168.1.206:3000/api/";

    //nhớ import cái nàyResponse theo tên dự án
    @GET("get-list-distributor")
    Call<Response<ArrayList<Distributor>>> getListDistributor();

    @GET("search-distributor")
    Call<Response<ArrayList<Distributor>>> searchDistributor(@Query("key") String key);

    @POST("add-distributor")
    Call<Response<Distributor>> addDistributor(@Body Distributor distributor);

    @DELETE("del-distributor/{id}")
    Call<Response<Distributor>> delDistributor(@Path("id") String id);

    @PUT("update-distribute/{id}")
    Call<Response<Distributor>> updateDistributor(@Path("id") String id, @Body Distributor distributor);
}
