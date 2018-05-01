package com.example.arife.a2018_hw2_ceng427;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Arife on 1.05.2018.
 */

public class FoodListFragment extends Fragment{
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ListView listView;
    private String TAG ="Error into fragment";
    private static String URL = "http://www.ybu.edu.tr/sks/";
    ProgressDialog progressDialog;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments, container, false);

        listView = view.findViewById(R.id.lv_main);
        textView = view.findViewById(R.id.textView);
        getList();

        adapter = new ArrayAdapter<String>(getContext(),R.layout.adapter_foodlist,R.id.text,list);
        listView.setAdapter(adapter);
        return view;
    }

    //getting arraylist of food to put in listview
    public void getList() {
        GetFoodListTask task = new GetFoodListTask();
        task.execute(URL);

    }

    private class GetFoodListTask extends AsyncTask<String,Integer, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            String uri = urls[0];
            Document doc = null;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements element = doc.select("td");
            ArrayList<String> liste = (ArrayList<String>) element.eachText();
            return liste;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {

            if(s != null){
                s.remove(0);
                list.clear();
                list.addAll(s);
                adapter.notifyDataSetChanged();
            }
            else
                list.add("There is no lunch for today");
        }


    }
}
