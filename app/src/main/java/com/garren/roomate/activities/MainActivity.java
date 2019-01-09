package com.garren.roomate.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garren.roomate.R;
import com.garren.roomate.fragments.SecretPasswordDialogFragment;
import com.garren.roomate.models.Room;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private Context mContext;
    private ListView mRoomsList;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoomsList = findViewById(R.id.roomsList);

        mContext = this;

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getRooms();
    }

    private void getRooms() {
        final List<Room> rooms = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("rooms");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Room room = snapshot.getValue(Room.class);
                    String key = snapshot.getKey();
                    room.setKey(key);
                    rooms.add(room);
                }

                displayRooms(rooms);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void displayRooms(final List<Room> rooms) {
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < rooms.size(); i++) {
            titles.add(rooms.get(i).getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, titles );
        mRoomsList.setAdapter(arrayAdapter);
        mRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Room clickedRoom = rooms.get(position);

                promptPassword(clickedRoom);
            }
        });
    }

    private void promptPassword(Room room) {
        FragmentManager fm = getFragmentManager();
        SecretPasswordDialogFragment secretPasswordFragment = new SecretPasswordDialogFragment();
        secretPasswordFragment.setRoom(room);
        secretPasswordFragment.show(fm, "TAG");
    }
}
