package com.example.arife.a2018_hw1_ceng427;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arife on 15.03.2018.
 */

public class MyAdapter extends ArrayAdapter<String>{
    private LayoutInflater layoutInflater;
    private TextView listItemText;
    private ImageButton editButton;
    private ImageButton removeButton;

    public MyAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        
        String item = getItem(position);

        convertView = layoutInflater.from(this.getContext()).inflate(R.layout.list_item,null);
        listItemText = convertView.findViewById(R.id.todoItemText);
        editButton = convertView.findViewById(R.id.editButton);
        removeButton = convertView.findViewById(R.id.removeButton);

        listItemText.setText(item);
        return convertView;
    }
}
