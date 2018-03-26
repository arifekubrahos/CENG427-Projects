package com.example.arife.a2018_hw1_ceng427;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arife on 15.03.2018.
 */

public class MyAdapter extends ArrayAdapter<String>{

    private TextView listItemText;
    private ImageButton editButton;
    private ImageButton removeButton;
    private DatabaseReference dbReferance;
    private ArrayList<String> myList;
    private String item;
    private static String TAG ="AdapterActivity";


    public MyAdapter(@NonNull Context context, @NonNull List<String> objects,DatabaseReference dbReferance) {
        super(context,0, objects);
        this.dbReferance = dbReferance;
        myList = (ArrayList<String>) objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        
        item = getItem(position);

        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item,null);
        listItemText = convertView.findViewById(R.id.todoItemText);
        editButton = convertView.findViewById(R.id.editButton);
        removeButton = convertView.findViewById(R.id.removeButton);
        final int pos = position;

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(myList.get(pos));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                update(myList.get(pos), view);

            }
        });


        listItemText.setText(item);
        return convertView;
    }


    @Override
    public void remove(@Nullable final String object) {

        dbReferance.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        String item = ds.getValue(String.class);
                        if(item.equals(object) ){
                            dbReferance.child(ds.getKey()).removeValue();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        notifyDataSetChanged();
    }

    public void update(final String object, final View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_item,null);

        alertDialog.setView(v);
        final EditText editText = v.findViewById(R.id.editText);

        alertDialog.setTitle(object);
        alertDialog.setMessage("Change to: ");

        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editText.getText().toString().equals("")){
                    dbReferance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String item = ds.getValue(String.class);
                                if(item.equals(object)){
                                    dbReferance.child(ds.getKey()).setValue(editText.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                else
                    Toast.makeText(view.getContext(),"Can not be empty",Toast.LENGTH_LONG).show();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog a = alertDialog.create();
        a.show();
    }


}
