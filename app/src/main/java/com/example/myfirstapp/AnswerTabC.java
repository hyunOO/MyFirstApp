package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 푸는 사람에 대한 클래스
    문제를 푸는 사람이 클라이언트의 역할을 하게된다.
*/

public class AnswerTabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_1);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device = null;

        if (pairedDevices.size() != 1) {

        } else {
            for (BluetoothDevice device : pairedDevices) {
                target_device = device;
            }
            try {
                String s = "0f14d0ab-9605-4a62-a9e4-5ed26688389b";
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
                            Intent myIntent = new Intent(getApplicationContext(), AnswerTabC1.class);
                            myIntent.putExtra("ANSWER", (String) obj);
                            myIntent.putExtra("COUNT", 1);
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

