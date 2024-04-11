package com.example.android_lab5_yenphph34781;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_lab5_yenphph34781.adapter.Recycle_Item_Distributor;
import com.example.android_lab5_yenphph34781.handle.Item_Distributor_Handle;
import com.example.android_lab5_yenphph34781.model.Distributor;
import com.example.android_lab5_yenphph34781.model.Response;
import com.example.android_lab5_yenphph34781.services.HttpRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements Item_Distributor_Handle{
private HttpRequest httpRequest;
private Recycle_Item_Distributor adapter;
private RecyclerView rclview;
public EditText edSearch;
private FloatingActionButton btnAdd;
private Item_Distributor_Handle handle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        rclview = findViewById(R.id.rclView);

        handle = new Item_Distributor_Handle() {
            @Override
            public void onDelete(String id) {
                showDelete(id);
            }

            @Override
            public void Update(String id, Distributor distributor) {
                showEditDialog(id, distributor);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rclview.setLayoutManager(layoutManager);
        btnAdd = findViewById(R.id.addButton);
        //khoi tao service request
        httpRequest = new HttpRequest();
        //thuc thi call api
        httpRequest.callApi()
                .getListDistributor()
                .enqueue(getDistributorApi);

        //tim kiếm
        edSearch = findViewById(R.id.edSearch);

        edSearch.addTextChangedListener(new TextWatcher() {
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
                            .searchDistributor(key)
                            .enqueue(getDistributorApi);//xu ly bat dong bo
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        thêm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }
    //b1
    public  void getData(ArrayList<Distributor> ds){
        adapter = new Recycle_Item_Distributor(this, ds, handle);
        rclview.setAdapter(adapter);
    }
//
    public void Delete(String id){
        httpRequest.callApi()
                .delDistributor(id)
                .enqueue(responseDistributor);
    }

    //    b2
    //dem new thi so code
    Callback<Response<ArrayList<Distributor>>> getDistributorApi = new Callback<Response<ArrayList<Distributor>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Distributor>>> call, retrofit2.Response<Response<ArrayList<Distributor>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    //lay data
                   ArrayList<Distributor> ds = response.body().getData();
                   //set du lieu len recycle
                    getData(ds);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Distributor>>> call, Throwable t) {
            Log.d(">>>GetListDistributor", "onFailure" + t.getMessage());
        }
    };
    //b3 show add, delete
    Callback<Response<Distributor>> responseDistributor = new Callback<Response<Distributor>>() {
        @Override
        public void onResponse(Call<Response<Distributor>> call, retrofit2.Response<Response<Distributor>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    //call lai danh sach
                    httpRequest.callApi()
                            .getListDistributor()
                            .enqueue(getDistributorApi);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Distributor>> call, Throwable t) {
            Log.d(">>>GetListDistributor", "onFailure" + t.getMessage());
        }
    };
    private void showAddDialog(){
        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add, null);
        diaBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);

        final AlertDialog dialog = diaBuilder.create();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                if(!name.isEmpty()){
                    Distributor distributor = new Distributor();
                    distributor.setName(name);
                    httpRequest.callApi()
                            .addDistributor(distributor)
                            .enqueue(responseDistributor);
                    Toast.makeText(MainActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Please nhap ten", Toast.LENGTH_SHORT).show();
                }
//                an dong hop thoai
                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    xoa
    private void showDelete (final  String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xac nhan xoa");
        builder.setMessage("Ban co chac chan muon xoa");
        builder.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Delete(id);
                Toast.makeText(MainActivity.this, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Huy bo xoa
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

//    cap nhat
    private void showEditDialog(String id, Distributor distributor){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chinh sua thong tin");
//         // Inflate layout cho dialog
        LayoutInflater inflater = getLayoutInflater();
        View diaView = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(diaView);

        final  EditText editTextName = diaView.findViewById(R.id.edTextNameU);
        editTextName.setText(distributor.getName());

        builder.setPositiveButton("Xac nhan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newName = editTextName.getText().toString();
                if(!newName.isEmpty()){
                    Distributor distributor1 = new Distributor();
                    distributor1.setName(newName);
                    httpRequest.callApi()
                            .updateDistributor(id, distributor1)
                            .enqueue(responseDistributor);
                    //hien thi thong bao cap nhat thanh cong
                    Toast.makeText(MainActivity.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Vui long nhap ten", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Huy bo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onDelete(String id) {

    }

    @Override
    public void Update(String id, Distributor distributor) {

    }
}