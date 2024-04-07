package com.example.asm_yenphph34781_android;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.asm_yenphph34781_android.Adapter.ClothesAdapter;
import com.example.asm_yenphph34781_android.databinding.ActivityHomeBinding;
import com.example.asm_yenphph34781_android.model.Clothes;
import com.example.asm_yenphph34781_android.server.HttpRequest;
import com.example.asm_yenphph34781_android.server.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity implements ClothesAdapter.ClothesClick{
ActivityHomeBinding binding;
private HttpRequest httpRequest;
private ClothesAdapter adapter;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Clothes clothes; // Biến để lưu đối tượng quần áo cần chỉnh sửa
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      binding = ActivityHomeBinding.inflate(getLayoutInflater());
      super.onCreate(savedInstanceState);
      setContentView(binding.getRoot());
       httpRequest = new HttpRequest();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvclothes.setLayoutManager(layoutManager);
        addClothes();
//        userListener();
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //xu ly truoc khi text thay doi
                String key = charSequence.toString();
                if(!key.isEmpty()){
//  httpRequest.callApi(): Có thể đây là một đối tượng đại diện cho việc gọi API từ máy chủ.
// .searchDistributor(key): Gọi phương thức searchDistributor(key) để thực hiện tìm kiếm các nhà phân phối với từ khóa được nhập (key).
// .enqueue(getDistributorApi): Bắt đầu một cuộc gọi không đồng bộ để gửi yêu cầu tìm kiếm đến máy chủ.
// Kết quả của cuộc gọi này sẽ được xử lý trong đối tượng getDistributorApi, có thể là một callback hoặc một đối tượng xử lý kết quả.
                    httpRequest.callApi()
                            .searchClothes(key)
                            .enqueue(getListFruitResponse);//xu ly bat dong bo
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void userListener() {
        binding.edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    String key = binding.edSearch.getText().toString().trim();
                    httpRequest.callApi()
                            .searchClothes(key)
                            .enqueue(getListFruitResponse);
                    Log.d(TAG, "onEditorAction: " + key);
                    return true;
                }
                return false;
            }
        });
    }
    public  void addClothes(){
binding.add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(HomeActivity.this, AddClothesActivity.class));
    }
});
    }
//Retrofit2, modle
   Callback<Response<ArrayList<Clothes>>> getListFruitResponse = new Callback<Response<ArrayList<Clothes>>>() {
    @Override
    public void onResponse(Call<Response<ArrayList<Clothes>>> call, retrofit2.Response<Response<ArrayList<Clothes>>> response) {
        if(response.isSuccessful()){
            if(response.body().getStatus() == 200){
                ArrayList<Clothes> ds = response.body().getData();
                getData(ds);
            }
        }
    }

    @Override
    public void onFailure(Call<Response<ArrayList<Clothes>>> call, Throwable t) {

    }
};
    private void getData(ArrayList<Clothes> list){
        adapter = new ClothesAdapter(this, list, this);
        binding.rcvclothes.setAdapter(adapter);
    }

    @Override
    public void delete(Clothes clothes) {

    }

    @Override
    public void edit(Clothes clothes) {
        this.clothes = clothes;
        // Tạo Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update);

        // Ánh xạ các thành phần trong layout
        EditText etName = dialog.findViewById(R.id.et_nameU);
        EditText etPrice = dialog.findViewById(R.id.et_priceU);
        EditText etDescription = dialog.findViewById(R.id.et_descriptionU);
        ImageView imageView = dialog.findViewById(R.id.imageU);
        Button btnSubmit = dialog.findViewById(R.id.btn_submitU);

        // Hiển thị dữ liệu của Clothes trên Dialog
        etName.setText(clothes.getName());
        etPrice.setText(String.valueOf(clothes.getPrice()));
        etDescription.setText(clothes.getDescription());
        // Hiển thị hình ảnh nếu có
        if (!clothes.getImage().isEmpty()) {
            String url = clothes.getImage().get(0);
            String newUrl = url.replace("localhost", "10.0.2.2");
            Glide.with(this)
                    .load(newUrl)
                    .into(imageView);
        }
        // Xử lý sự kiện khi nhấn nút "Select Image"
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start image selection activity
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Submit"
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String name = etName.getText().toString();
                int price = Integer.parseInt(etPrice.getText().toString()); // Ép kiểu sang int
                String description = etDescription.getText().toString();

                // Cập nhật dữ liệu của Clothes
                clothes.setName(name);
                clothes.setPrice(price);
                clothes.setDescription(description);
                binding.rcvclothes.setAdapter(adapter);
                // Đóng Dialog sau khi cập nhật dữ liệu
                dialog.dismiss();
                adapter.notifyDataSetChanged();

                // Gọi phương thức cập nhật dữ liệu trong ClothesAdapter (nếu cần)
//                 notifyDataSetChanged(); // Đảm bảo cập nhật giao diện hiển thị
                // Hoặc có thể gọi phương thức cập nhật dữ liệu từ máy chủ tại đây
            }
        });

        // Hiển thị Dialog
        dialog.show();
    }
//
//// Xử lý kết quả từ việc chọn ảnh từ thư viện
//@Override
//protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//        // Lấy URI của ảnh đã chọn từ Intent
//        Uri imageUri = data.getData();
//
//        // Thêm đường dẫn ảnh mới vào danh sách ảnh của Clothes
//        if (clothes != null) {
//            if (clothes.getImage() == null) {
//                clothes.setImage(new ArrayList<>());
//            }
//            clothes.getImage().add(imageUri.toString());
//        }
//
//        // Cập nhật giao diện người dùng
//        adapter.notifyDataSetChanged();
//
//        // Thực hiện gọi API để cập nhật quần áo
//        updateClothes(clothes.get_id(), clothes);
//    }
//}
//
//    // Phương thức để gọi API cập nhật quần áo
//    private void updateClothes(String clothesId, Clothes updatedClothes) {
//        // Tạo request body map để chứa thông tin cập nhật của quần áo
//        Map<String, RequestBody> requestBodyMap = new HashMap<>();
//        // Thêm các thông tin cần cập nhật vào requestBodyMap
//        // Ví dụ:
//        requestBodyMap.put("name", RequestBody.create(MediaType.parse("text/plain"), updatedClothes.getName()));
//        requestBodyMap.put("price", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(updatedClothes.getPrice())));
//        requestBodyMap.put("description", RequestBody.create(MediaType.parse("text/plain"), updatedClothes.getDescription()));
//
//        // Tạo list parts để chứa danh sách ảnh của quần áo
//        ArrayList<MultipartBody.Part> imageParts = new ArrayList<>();
//        if (updatedClothes.getImage() != null) {
//            for (String imageUrl : updatedClothes.getImage()) {
//                // Tạo MultipartBody.Part từ đường dẫn ảnh
//                File imageFile = new File(Uri.parse(imageUrl).getPath());
//                RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
//                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
//                imageParts.add(imagePart);
//            }
//        }
//
//        // Gọi API để cập nhật quần áo
//        httpRequest.callApi().updateClothes(clothesId, requestBodyMap, imageParts).enqueue(new Callback<Response<Clothes>>() {
//            @Override
//            public void onResponse(Call<Response<Clothes>> call, retrofit2.Response<Response<Clothes>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body().getStatus() == 200) {
//                        // Cập nhật thành công
//                        // Có thể thực hiện các xử lý phụ trợ nếu cần
//                    }
//                } else {
//                    // Xử lý lỗi khi cập nhật thất bại
//                    Log.e("updateClothes", "Failed to update clothes on server");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response<Clothes>> call, Throwable t) {
//                // Xử lý lỗi khi gọi API cập nhật thất bại
//                Log.e("updateClothes", "Failed to update clothes: " + t.getMessage());
//            }
//        });
//    }

    //
    @Override
    public void delete(int position) {

    }


    @Override
    public void deleteItem(int position, ArrayList<Clothes> list) {
        // Xóa mục từ danh sách dữ liệu
        Clothes deletedClothes = list.remove(position);
        // Thông báo cho adapter biết dữ liệu đã thay đổi
        adapter.notifyItemRemoved(position);

        // Gọi API để xóa dữ liệu trên máy chủ
        if (deletedClothes != null) {
            httpRequest.callApi().deleteClothes(deletedClothes.get_id()).enqueue(new Callback<Response<Clothes>>() {
                @Override
                public void onResponse(Call<Response<Clothes>> call, retrofit2.Response<Response<Clothes>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            // Xóa thành công trên máy chủ
                            // Có thể thực hiện các xử lý phụ trợ nếu cần
                        }
                    } else {
                        // Xử lý lỗi khi xóa trên máy chủ thất bại
                        Log.e("deleteClothes", "Failed to delete clothes on server");
                    }
                }

                @Override
                public void onFailure(Call<Response<Clothes>> call, Throwable t) {
                    // Xử lý lỗi khi gọi API xóa thất bại
                    Log.e("deleteClothes", "Failed to delete clothes: " + t.getMessage());
                }
            });
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        httpRequest.callApi().getListClothes().enqueue(getListFruitResponse);
    }
}