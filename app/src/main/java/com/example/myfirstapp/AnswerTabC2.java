package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 푸는 사람에 대한 세번째 클래스
    문제를 푸는 사람이 클라이언트의 역할을 하게된다.
*/

public class AnswerTabC2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2_1);

        Intent intent = getIntent();
        final String str_ans = intent.getStringExtra("ANSWER");
        final String quest = intent.getStringExtra("ANSWERFORQUEST");
        final int get_count = intent.getIntExtra("COUNT", 0);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device = null;

        if (pairedDevices.size() != 1) {

        } else {
            for (BluetoothDevice device : pairedDevices) {
                target_device = device;
            }
            try {
                String s = "FFFFFFFF-C917-212C-D958-11A94F3D97CA";
                String s2 = s.replace("-", "");
                UUID uuid = new UUID(new BigInteger(s2.substring(0, 16), 16).longValue(), new BigInteger(s2.substring(16), 16).longValue());
                final BluetoothSocket clientSocket = target_device.createRfcommSocketToServiceRecord(uuid);
                //clientSocket.connect();

                Thread thread1 = new Thread(new Runnable() {
                    public void run() {
                        try {
                            if(mBluetoothAdapter.isDiscovering()){
                                mBluetoothAdapter.cancelDiscovery();
                            }

                            clientSocket.connect();
                            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                            Object obj = inputStream.readObject();
                            Intent myIntent = new Intent(getApplicationContext(), AnswerTabC3.class);
                            myIntent.putExtra("ANSWERFORQUEST", quest);
                            myIntent.putExtra("ANSWER", str_ans);
                            myIntent.putExtra("COUNT", get_count);
                            myIntent.putExtra("ANSWERHI", (String) obj);
                            inputStream.close();
                            clientSocket.close();
                            startActivity(myIntent);
                        } catch (Exception e) {
                        }
                    }
                });
                thread1.start();

            }
            catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}