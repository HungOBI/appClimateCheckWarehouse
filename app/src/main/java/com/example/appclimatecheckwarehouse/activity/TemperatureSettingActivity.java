package com.example.appclimatecheckwarehouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appclimatecheckwarehouse.R;
import com.example.appclimatecheckwarehouse.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TemperatureSettingActivity extends AppCompatActivity {

    private TextView tv_fan;
    private TextView roomName;
    private TextView temperature;
    private TextView humidity;
    private ImageView imgBack;
    private ImageView imgDecrease;
    private ImageView imgIncrease;
    private int fanValue = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_setting);

        roomName = findViewById(R.id.tv_title);
        temperature= findViewById(R.id.tv_temperature);
        humidity = findViewById(R.id.tv_humidity);
        imgBack = findViewById(R.id.img_back);
        imgDecrease = findViewById(R.id.img_decrease);
        imgIncrease = findViewById(R.id.img_increase);
        tv_fan = findViewById(R.id.tv_fan);

        Intent intent = getIntent();
        String name = intent.getStringExtra("roomName");
        double temp = intent.getDoubleExtra("roomTemp", 0);
        double humid = intent.getDoubleExtra("roomHumid", 0);

        roomName.setText(name);
        temperature.setText(String.valueOf(temp));
        humidity.setText(String.valueOf(humid));
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Rooms");
        database.orderByChild("roomName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Room room = dataSnapshot.getValue(Room.class);
                        fanValue = room.getFan();
                        tv_fan.setText(room.getFan() + "%");
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error getting data", error.toException());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemperatureSettingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        imgIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanValue += 10;
                if (fanValue > 100) {
                    fanValue = 100;
                }
                tv_fan.setText(fanValue + "%");

                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Rooms");
                database.orderByChild("roomName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().child("fan").setValue(fanValue);
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error getting data", error.toException());
                    }
                });
            }
        });

        imgDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanValue -= 10;
                if (fanValue < 0) {
                    fanValue = 0;
                }
                tv_fan.setText(fanValue + "%");


                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Rooms");
                database.orderByChild("roomName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                dataSnapshot.getRef().child("fan").setValue(fanValue);
                            }
                        } else {
                            // Không tìm thấy phòng tương ứng
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error getting data", error.toException());
                    }
                });
            }
        });
    }


}