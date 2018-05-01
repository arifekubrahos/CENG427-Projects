package com.example.arife.a2018_hw2_ceng427;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ListView listView;
    private ArrayList<String> links = new ArrayList<>();
    private static String URL = "http://www.ybu.edu.tr/muhendislik/bilgisayar/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments, container, false);
        listView = view.findViewById(R.id.lv_main);
        getList();

        adapter = new ArrayAdapter<String>(getContext(),R.layout.adapter_news,R.id.text,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    //getting arraylist of food to put in listview
    public void getList() {
        GetNewsListTask task = new GetNewsListTask();
        task.execute(URL);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Uri uri = Uri.parse(links.get(i));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private class GetNewsListTask extends AsyncTask<String,Integer, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            String uri = urls[0];
            ArrayList<String> liste = new ArrayList<String>();
            try {
                Document doc = Jsoup.connect(URL).get();
                int i =0;
                while (i!=3){
                    Elements elem = doc.select("div.cncItem a#ContentPlaceHolder1_ctl01_rpData_hplink_"+i);
                    Elements link = doc.select("div.cncItem a#ContentPlaceHolder1_ctl01_rpData_hplink_"+i+"[href]");
                    i++;
                    liste.add(elem.text());
                    links.add(link.attr("abs:href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return liste;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {

            if(s != null){
                list.clear();
                list.addAll(s);
                adapter.notifyDataSetChanged();
            }
            else
                list.add("There is no news.");
        }


    }
}
