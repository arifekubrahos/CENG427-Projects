package com.example.arife.a2018_hw2_ceng427;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Arife on 1.05.2018.
 */

public class AnnouncementFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    private ListView listView;
    private String TAG ="Error into fragment";
    private static String URL = "http://www.ybu.edu.tr/muhendislik/bilgisayar/";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments, container, false);
        listView = view.findViewById(R.id.lv_main);

        getList();

        list.add("Waiting...");
        adapter = new ArrayAdapter<String>(getContext(),R.layout.adapter_announcement,R.id.text,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    //getting arraylist of food to put in listview
    public void getList() {
        GetAnnListTask task = new GetAnnListTask();
        task.execute(URL);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Uri uri = Uri.parse(links.get(i));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private class GetAnnListTask extends AsyncTask<String,Integer, ArrayList<String>> {

        //connection done here we get data from internet
        @Override
        protected ArrayList<String> doInBackground(String... urls) {
            String uri = urls[0];
            ArrayList<String> liste = new ArrayList<String>();
            try {
                Document doc = Jsoup.connect(URL).get();
                int i =0;
                while (i!=6){
                    Elements elem = doc.select("div.cncItem a#ContentPlaceHolder1_ctl02_rpData_hplink_"+i);
                    Elements link = doc.select("div.cncItem a#ContentPlaceHolder1_ctl02_rpData_hplink_"+i+"[href]");
                    i++;
                    liste.add(elem.text());
                    links.add(link.attr("abs:href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return liste;
        }

        //add all news into the list that we get from doInBackground and notify to refresh
        @Override
        protected void onPostExecute(ArrayList<String> s) {

            if(s != null){
                list.clear();
                list.addAll(s);
                adapter.notifyDataSetChanged();
            }
            else
                list.add("There is no announcement");
        }


    }

}
