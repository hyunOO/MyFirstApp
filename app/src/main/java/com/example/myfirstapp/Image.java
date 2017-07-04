package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by q on 2017-07-03.
 */

public class Image extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView iv = (ImageView)findViewById(R.id.imageView3);
        TextView show_name = (TextView)findViewById(R.id.textView9);
        Button btn = (Button)findViewById(R.id.button);

        Intent intent = getIntent();
        iv.setImageResource(intent.getIntExtra("img",0));
        show_name.setText(intent.getStringExtra("name"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
}
}
