package com.example.android_lab5_yenphph34781.services;
//b3

import com.example.android_lab5_yenphph34781.model.Distributor;
//nhớ import cái này
import com.example.android_lab5_yenphph34781.model.Fruit;
import com.example.android_lab5_yenphph34781.model.Response;
import com.example.android_lab5_yenphph34781.model.User;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    //    lab 6
    @Multipart
    @POST("register-send-email")
    Call<Response<User>> register(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part avartar
    );
    @POST("login")
    Call<Response<User>> login (@Body User user);

    @GET("get-list-fruit")
    Call<Response<ArrayList<Fruit>>> getListFruit (@Header("Authorization") String token);

    @Multipart
    @POST("add-fruit-with-file-image")
    Call<Response<Fruit>> addFruit(@PartMap Map<String, RequestBody> requestBodyMap,
                                   @Part ArrayList<MultipartBody.Part> ds_hinh);
}
