package com.example.arife.a2018_hw1_ceng427;

import android.annotation.SuppressLint;
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
 * Adapter class extends Arrayadapter
 */

public class MyAdapter extends ArrayAdapter<String>{

    private TextView listItemText;
    private ImageButton editButton;
    private ImageButton removeButton;
    private DatabaseReference dbReferance;
    private ArrayList<String> myList;
    private String item;
    private static String TAG ="AdapterActivity";

    /*to get list and database from MainActivity */
    public MyAdapter(@NonNull Context context, @NonNull List<String> objects,DatabaseReference dbReferance) {
        super(context,0, objects);
        this.dbReferance = dbReferance;
        myList = (ArrayList<String>) objects;
    }

    /*getView method return the each item view based on arraylist position*/
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        
        item = getItem(position);

        //item layout instance is created for the inside of listview
        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item,null);
        listItemText = convertView.findViewById(R.id.todoItemText);
        editButton = convertView.findViewById(R.id.editButton);
        removeButton = convertView.findViewById(R.id.removeButton);
        final int pos = position;

        //remove and edit button is activated within view method

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "arraylist pos:"+pos);
                remove(item);
                removefromdatabase(item);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                update(item, view,pos);
            }
        });

        //list item text set here
        listItemText.setText(item);

        return convertView;
    }

    /*remove from list and database to be permanent list and notify changed*/
    public void removefromdatabase(@Nullable final String object) {
        dbReferance.addListenerForSingleValueEvent(new ValueEventListener() {
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
                Log.e(TAG,"error to connect: ",databaseError.toException());
            }
        });
    }

    /*updated with alert dialog to get updated text from it
    * not prefer to start new activity*/
    public void update(final String object, final View view, final int position){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());

        //add edittext in alertdialog
        View v = LayoutInflater.from(view.getContext()).inflate(R.layout.edit_item,null);
        alertDialog.setView(v);
        final EditText editText = v.findViewById(R.id.editText);

        //changed text set as title for user
        alertDialog.setTitle(object);
        alertDialog.setMessage("change to: ");

        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(!editText.getText().toString().equals("")){
                    dbReferance.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String item = ds.getValue(String.class);
                                if(item.equals(object)){
                                    dbReferance.child(ds.getKey()).setValue(editText.getText().toString());
                                    myList.set(position,editText.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG,"error to connect: ",databaseError.toException());
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
