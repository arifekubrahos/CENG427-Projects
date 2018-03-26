package com.example.arife.a2018_hw1_ceng427;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private ListView listView;
    private ArrayList<String> arrayList;
    private static String TAG = "ListActivity";
    private MyAdapter myAdapter;
    private FirebaseDatabase fb;
    private DatabaseReference dbReferance;
    private Date currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.addListText);
        listView = findViewById(R.id.todoList);
        arrayList = new ArrayList<>();

        fb = FirebaseDatabase.getInstance();
        dbReferance = fb.getReference("ToDoList");


        myAdapter = new MyAdapter(this, arrayList, dbReferance);
        listView.setAdapter(myAdapter);
        readData();
    }
    public void readData(){

        dbReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String item = ds.getValue(String.class);
                    arrayList.add(item);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "error to read database", databaseError.toException());
            }
        });
    }
    public void addNewItem(View v){
        currentTime = Calendar.getInstance().getTime();
        String newItem= editText.getText().toString();
        dbReferance.child(currentTime.toString()).setValue(newItem);
    }

}
