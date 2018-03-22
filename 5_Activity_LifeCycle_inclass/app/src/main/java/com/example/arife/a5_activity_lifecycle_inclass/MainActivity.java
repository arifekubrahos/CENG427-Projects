package com.example.arife.a5_activity_lifecycle_inclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainAct";
    private int clicks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i (TAG, "onCreate was called");

        SharedPreferences pref = this.getSharedPreferences("myPref",MODE_PRIVATE);
        clicks = pref.getInt("clicks", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume was called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart was called");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onReStart was called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause was called");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop was called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"onDestroy was called");

        SharedPreferences pref = this.getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor =pref.edit();
        editor.putInt("clicks", clicks);
        editor.commit();
    }


    public void changeActivity(View view) {
        //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        //startActivity(i);
        clicks++;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(bundle, outPersistentState);
        bundle.putInt("clicks", clicks);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        clicks = savedInstanceState.getInt("clicks");
    }
}
