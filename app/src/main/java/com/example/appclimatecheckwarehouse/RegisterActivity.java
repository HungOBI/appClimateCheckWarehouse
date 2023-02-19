package com.example.appclimatecheckwarehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText edt_name,edt_email,edt_phone,edt_password;
    private TextView btn_sign_up,btn_sign_in;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_name = findViewById(R.id.textEditName);
        edt_email = findViewById(R.id.textEditEmail);
        edt_phone = findViewById(R.id.textEditPhone);
        edt_password = findViewById(R.id.textEditPhone);
        btn_sign_up = findViewById(R.id.register_text);
        btn_sign_in = findViewById(R.id.textViewLogin);
        mAuth = FirebaseAuth.getInstance();
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String email,password,name,phone;
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        name = edt_name.getText().toString();
        phone = edt_phone.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please enter name!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please enter phone!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password!!",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Registration successful!!!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Registration Failed, Please Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void login() {
        Intent intent =  new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}