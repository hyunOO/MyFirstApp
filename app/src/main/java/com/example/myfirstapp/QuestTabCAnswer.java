package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by q on 2017-07-04.
 */

public class QuestTabCAnswer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_4);
/*
        final Intent myIntent = new Intent(getApplicationContext(), TabC.class);
        try{
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    startActivity(myIntent);
                }
            });
            thread1.sleep(2000);
            thread1.run();
        }
        catch(Exception e) { }
        */
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}