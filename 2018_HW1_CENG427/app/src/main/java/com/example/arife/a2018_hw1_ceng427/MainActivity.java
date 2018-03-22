package com.example.arife.a2018_hw1_ceng427;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.todoList);
        arrayList = new ArrayList<>();
        scanner = new Scanner(getResources().openRawResource(R.raw.list));
        while(scanner.hasNext()){
            arrayList.add(scanner.nextLine());
        }

        MyAdapter myAdapter = new MyAdapter(this,arrayList);
        listView.setAdapter(myAdapter);





    }

    public void addNewThings(){

        scanner = new Scanner(getResources().openRawResource(R.raw.list));
       //burada sonuna ekleme yapıyoruz
    }


    public void editText(View view) {

        Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_LONG).show();
    }
}
