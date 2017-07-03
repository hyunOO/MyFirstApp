package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;

/**
 * Created by q on 2017-07-03.
 */

public class QuestTabC extends AppCompatActivity {

    OutputStream output = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_1);

        Button btn01 = (Button)findViewById(R.id.button);
        final EditText texts = (EditText)findViewById(R.id.textView3);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(texts.getText().toString());
                Intent intent = new Intent(getApplicationContext(), QuestTabC_2.class);
                intent.putExtra("answer",texts.getText().toString());
                startActivity(intent);
            }
        });
    }
    public void sendData(String  msg){
        msg +="\n";
        try{
            output.write(msg.getBytes());
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Bluetooth connection failed.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}