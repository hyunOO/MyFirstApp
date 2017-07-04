package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn01 = (Button)findViewById(R.id.button01);
        Button btn02 = (Button)findViewById(R.id.button02);
        Button btn03 = (Button)findViewById(R.id.button03);


        btn01.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), TabA.class);
        startActivity(intent);
        }
        });



        btn02.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), TabB.class);
        startActivity(intent);
        }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), TabC.class);
        startActivity(intent);
        }
        });
        }
@Override
public void onBackPressed(){
        super.onBackPressed();
        }
        }
