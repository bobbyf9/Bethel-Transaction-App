package com.example.betheltransactionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreate;
    private EditText editEmailRegister, editPasswordRegister;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Open the desired activity when back is pressed
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        btnCreate = findViewById(R.id.btnRegisterAccount);
        editEmailRegister = findViewById(R.id.editEmailSignup);
        editPasswordRegister = findViewById(R.id.editPasswordSignup);



        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email=editEmailRegister.getText().toString();
                password= editPasswordRegister.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Detected empty fields. Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully, Please Login", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegisterActivity.this, TransactionActivity.class));
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Account Creation Failed, Email Already Exists Or Invalid Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}