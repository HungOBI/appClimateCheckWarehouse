package com.example.appclimatecheckwarehouse.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appclimatecheckwarehouse.MyAdapter;
import com.example.appclimatecheckwarehouse.R;
import com.example.appclimatecheckwarehouse.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Room> list;
    ImageView logout;
    ImageView sortAZ;
    EditText search;
    boolean isAscendingOrder = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.edt_search);
        sortAZ = findViewById(R.id.img_sort);
        logout = findViewById(R.id.img_logout);
        recyclerView = findViewById(R.id.room_list);
        database = FirebaseDatabase.getInstance().getReference("Rooms");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Room> roomList = new ArrayList<>();
        myAdapter = new MyAdapter(roomList);
        recyclerView.setAdapter(myAdapter);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sortAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAscendingOrder) {
                    sortAZNameRooms();
                    isAscendingOrder = false;
                } else {
                    sortZANameRooms();
                    isAscendingOrder = true;
                }
                myAdapter.notifyDataSetChanged();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(MainActivity.this, TemperatureSettingActivity.class);
                startActivity(intent);
            }
        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Room roomdata = dataSnapshot.getValue(Room.class);
                    roomList.add(roomdata);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error getting data", error.toException());
            }
        });


    }
    private void sortAZNameRooms() {
        List<Room> roomList = myAdapter.getRoomList();
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });
        myAdapter.setRoomList(roomList);
    }

    private void sortZANameRooms() {
        List<Room> roomList = myAdapter.getRoomList();
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o2.getRoomName().compareTo(o1.getRoomName());
            }
        });
        myAdapter.setRoomList(roomList);
    }



}