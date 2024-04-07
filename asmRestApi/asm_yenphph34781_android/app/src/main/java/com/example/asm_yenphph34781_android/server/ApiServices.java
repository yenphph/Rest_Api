package com.example.asm_yenphph34781_android.server;

import com.example.asm_yenphph34781_android.model.Clothes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    public static String BASE_URL = "http://192.168.1.206:3000/api/";
//model
    @GET("clothes")
    Call<Response<ArrayList<Clothes>>> getListClothes();
   @Multipart
    @POST("clothes")
    Call<Response<Clothes>> addCLothes (@PartMap Map<String, RequestBody> requestBodyMap,
                                       @Part ArrayList<MultipartBody.Part> ds_hinh);
//    @PUT("clothes/{id}")
//    Call<Response<Clothes>> updateClothes(@Path("id") String id,
//                                @PartMap Map<String, RequestBody> requestBodyMap,
//                                @Part ArrayList<MultipartBody.Part> images);

    Call<Response<Clothes>> updateClothes(
            @Part("clothes/{id}") String clothesId, // Giả sử bạn cần truyền ID quần áo như là một trường biểu mẫu
            @PartMap Map<String, RequestBody> clothesData, // Trường biểu mẫu
            @Part List<MultipartBody.Part> images // Ảnh multipart
    );
    @GET("search")
    Call<Response<ArrayList<Clothes>>> searchClothes(@Query("key") String key);

    @DELETE("clothes/{id}")
    Call<Response<Clothes>> deleteClothes(@Path("id") String id);
}
