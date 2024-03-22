package com.example.ams_yenphph34781;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextInputEditText name, pass;
    private Button button;
    private TextView forgotpw;
    private TextView moveDangKy;
    private TextView backL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.buttonLoginL);
        name = findViewById(R.id.editTextUsernameL);
        pass = findViewById(R.id.editTextPasswordL);

        forgotpw = findViewById(R.id.forgotpw);
        moveDangKy = findViewById(R.id.moveDangKy);

        backL = findViewById(R.id.backL);
        backL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String password = pass.getText().toString();
                if(!username.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    if(!password.isEmpty()){
                        auth.signInWithEmailAndPassword(username, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(LoginActivity.this, "Login successfful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }else{
                        pass.setError("Pass cannot be empty");
                    }
                }else if(username.isEmpty()){
                    name.setError("Email cannot be empty");
                }else{
                    name.setError("Please input");
                }
            }
        });

        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPasswordResetEmail();
            }
        });
        moveDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
    private void sendPasswordResetEmail() {
        String emailAddress = name.getText().toString().trim();
        if (isValidEmail(emailAddress)) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra hộp thư để cập nhật lại mật khẩu", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Lỗi gửi mail: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập địa chỉ email hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}