package com.example.ams_yenphph34781;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.ams_yenphph34781.databinding.ActivityAddClothesBinding;
import com.example.ams_yenphph34781.model.Clothes;
import com.example.ams_yenphph34781.server.HttpRequest;
import com.example.ams_yenphph34781.server.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddClothesActivity extends AppCompatActivity {
    ActivityAddClothesBinding binding;
    private HttpRequest httpRequest;
    private String id_Clothes;
    private ArrayList<Clothes> clothesArrayList;
    private ArrayList<File> ds_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAddClothesBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        ds_image = new ArrayList<>();
        httpRequest = new HttpRequest();
//        configSpinner();
        userListener();
    }

    private void userListener() {
        binding.avatarA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        binding.btnBackA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//         thông tin về quần áo sẽ được thu thập và gửi đi qua API để thêm mới.
        binding.btnRegisterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , RequestBody> mapRequestBody = new HashMap<>();
                String _name = binding.edNameA.getText().toString().trim();
                String _quantity = binding.edDescriptionA.getText().toString().trim();
                String _price = binding.edPriceA.getText().toString().trim();
                String _description = binding.edDescriptionA.getText().toString().trim();

                mapRequestBody.put("name", getRequestBody(_name));
                mapRequestBody.put("quantity", getRequestBody(_quantity));
                mapRequestBody.put("price", getRequestBody(_price));
                mapRequestBody.put("description", getRequestBody(_description));
                ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
                ds_image.forEach(file1 -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file1);
                    MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(),requestFile);
                    _ds_image.add(multipartBodyPart);
                });
                httpRequest.callApi().addCLothes(mapRequestBody, _ds_image).enqueue(responseFruit);


            }
        });
    }

    Callback<Response<Clothes>> responseFruit = new Callback<Response<Clothes>>() {
        @Override
        public void onResponse(Call<Response<Clothes>> call, retrofit2.Response<Response<Clothes>> response) {
            if (response.isSuccessful()) {
                Log.d("123123", "onResponse: " + response.body().getStatus());
                if (response.body().getStatus()==200) {
//                    Toast.makeText(AddFruitActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Clothes>> call, Throwable t) {
//            Toast.makeText(AddFruitActivity.this, "Thêm  thành công", Toast.LENGTH_SHORT).show();
            Log.e("zzzzzzzzzz", "onFailure: "+t.getMessage());
        }
    };

    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),value);
    }
    //    Phương thức này được gọi khi người dùng muốn chọn ảnh từ bộ nhớ thiết bị.
    private void chooseImage() {
        Log.d("123123", "chooseAvatar: " +123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getImage.launch(intent);

    }
    //mở màn hình chọn ảnh và lắng nghe kết quả trả về.
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {

                        Uri tempUri = null;

                        ds_image.clear();
                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                tempUri = imageUri;

                                File file = createFileFormUri(imageUri, "image" + i);
                                ds_image.add(file);
                            }


                        } else if (data.getData() != null) {
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();

                            tempUri = imageUri;
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image" );
                            ds_image.add(file);

                        }

                        if (tempUri != null) {
                            Glide.with(AddClothesActivity.this)
                                    .load(tempUri)
                                    .thumbnail(Glide.with(AddClothesActivity.this).load(R.drawable.baseline_broken_image_24))
                                    .centerCrop()
                                    .circleCrop()
                                    .skipMemoryCache(true)
                                    .into(binding.avatarA);
                        }

                    }
                }
            });
    //Phương thức này tạo một tệp từ URI của hình ảnh được chọn.
//Tạo tệp trong bộ nhớ cache của ứng dụng và lưu trữ hình ảnh được chọn vào đó.
    private File createFileFormUri (Uri path, String name) {
        File _file = new File(AddClothesActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = AddClothesActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    Callback<Response<ArrayList<Clothes>>> getDistributorAPI = new Callback<Response<ArrayList<Clothes>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Clothes>>> call, retrofit2.Response<Response<ArrayList<Clothes>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    clothesArrayList = response.body().getData();
                    String[] items = new String[clothesArrayList.size()];

                    for (int i = 0; i< clothesArrayList.size(); i++) {
                        items[i] = clothesArrayList.get(i).getName();
                    }
//                    ArrayAdapter<String> adapterSpin = new ArrayAdapter<>(AddClothesActivity.this, android.R.layout.simple_spinner_item, items);
//                    adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    binding.spDistributor.setAdapter(adapterSpin);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Clothes>>> call, Throwable t) {
            t.getMessage();
        }

    };

}