package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class TabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_c);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button start_button1 = (Button) findViewById(R.id.start_with_answer);
        start_button1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(TabC.this, "앱 실행 실패-블루투스 문제", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
                        alert.setTitle("앱 실행 오류");
                        alert.setMessage("블루투스를 활성화하세요.");
                        alert.show();
                    } else {
                        Intent myIntent = new Intent(getApplicationContext(), AnswerTabC.class);
                        startActivity(myIntent);
                    }
                }

                /*

                */
            }
        }));

        Button start_button2 = (Button) findViewById(R.id.start_with_problem);
        start_button2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(TabC.this, "앱 실행 실패-블루투스 문제", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
                        alert.setTitle("앱 실행 오류");
                        alert.setMessage("블루투스를 활성화하세요.");
                        alert.show();
                    } else {
                        Intent myIntent = new Intent(getApplicationContext(), QuestTabC.class);
                        startActivity(myIntent);
                    }
                }

                /*

                */
            }
        }));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
