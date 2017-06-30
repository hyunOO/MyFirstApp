package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn01 = (Button)findViewById(R.id.button01);

        Button btn02 = (Button)findViewById(R.id.button02);
        Button btn03 = (Button)findViewById(R.id.button03);

    }
}
