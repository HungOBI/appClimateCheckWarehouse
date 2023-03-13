package com.example.appclimatecheckwarehouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appclimatecheckwarehouse.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private EditText edt_name,edt_email,edt_phone,edt_password;
    private TextView btn_sign_in;
    private LinearLayout btn_sign_up;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_name = findViewById(R.id.textEditName);
        edt_email = findViewById(R.id.textEditEmail);
        edt_phone = findViewById(R.id.textEditPhone);
        edt_password = findViewById(R.id.textEditPhone);
        btn_sign_up = findViewById(R.id.register_button);
        btn_sign_in = findViewById(R.id.textViewLogin);
        mAuth = FirebaseAuth.getInstance();

        login();
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
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Registration successful!!!",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getApplicationContext(),"Registration Failed, Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                });
        String userId = "user" + UUID.randomUUID().toString();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        userRef.child("email").setValue(email);
        userRef.child("password").setValue(password);
        userRef.child("name").setValue(name);
        userRef.child("phone").setValue(phone);
    }


    private void login() {
        String text = "Already have an account? Login";
        Spannable spannable = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            public void updateDrawState(@NonNull TextPaint textPaint){
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
                textPaint.setColor(Color.BLUE);
            }
        };
        int startIndex = text.indexOf("Login");
        int endIndex = startIndex + "Login".length();
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        btn_sign_in.setText(spannable);
        btn_sign_in.setMovementMethod(LinkMovementMethod.getInstance());

    }
}