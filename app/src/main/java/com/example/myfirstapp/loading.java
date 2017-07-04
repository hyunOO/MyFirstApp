package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by q on 2017-07-04.
 * 로딩화면을 위한 class
 */

public class loading extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
           @Override
            public void run(){
               Intent intent = new Intent(getBaseContext(), MainActivity.class);
               startActivity(intent);
               finish();
           }
        },2000); //로딩화면 시간


    }
}
