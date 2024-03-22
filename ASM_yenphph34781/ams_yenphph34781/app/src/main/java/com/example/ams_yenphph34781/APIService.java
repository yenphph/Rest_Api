package com.example.ams_yenphph34781;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.1.206:5000/";

    @GET("api/clothes/")
    Call<List<Clothes>> getClothes();

    @POST("api/clothes/")
    Call<Void> addClothes(@Body Clothes newItem);

    @DELETE("api/clothes/{id}") // Sửa lại đường dẫn để chấp nhận tham số là chuỗi
    Call<Void> deleteClothes(@Path("id") String id); // Sửa kiểu dữ liệu của tham số ID thành String
    @PUT("api/clothes/{id}") // Phương thức cập nhật
    Call<Void> updateClothes(@Path("id") String id, @Body Clothes updatedItem);
}
