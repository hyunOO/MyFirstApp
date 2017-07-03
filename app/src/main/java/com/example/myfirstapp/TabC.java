package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class TabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_c);
/*
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button start_button = (Button) findViewById(R.id.start_game);
        start_button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBluetoothAdapter.isEnabled()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
                    alert.setTitle("앱 실행 오류");
                    alert.setMessage("블루투스를 활성화하세요.");
                    alert.show();
                }
                else{
                    boolean b = false;
                    // b가 true이면 출제자
                    if(b){
                        Intent intent1 = new Intent(getApplicationContext(), QuestTabC.class);
                        startActivity(intent1);
                    }
                    // b가 false이면 답안자
                    else{
                        Intent intent2 = new Intent(getApplicationContext(), AnswerTabC.class);
                        startActivity(intent2);
                    }
                }
            }
        }));
        */
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
