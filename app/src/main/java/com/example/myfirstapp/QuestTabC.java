package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/*
    문제를 내는 사람에 대한 클래스
    문제를 내는 사람이 서버의 역할을 하게된다.
*/

public class QuestTabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_1);


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
                final BluetoothServerSocket serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("", uuid);
                final BluetoothSocket socket = serverSocket.accept();
                final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                Button btn = (Button) findViewById(R.id.answer_button_3343);
                btn.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edt = (EditText) findViewById(R.id.textView3);
                        final Object str = edt.getText().toString();

                        /*
                        Thread thread1 = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    outputStream.writeObject(str);
                                    outputStream.flush();
                                    final Intent myIntent = new Intent(getApplicationContext(), QuestTabC1.class);
                                    myIntent.putExtra("ANSWER", (String) str);
                                    myIntent.putExtra("COUNT", 1);
                                    socket.close();
                                    serverSocket.close();
                                    startActivity(myIntent);
                                } catch (Exception e) {
                                }
                            }
                        });
                        thread1.run();
                        */

                        try {
                            outputStream.writeObject(str);
                            outputStream.flush();
                            final Intent myIntent = new Intent(getApplicationContext(), QuestTabC1.class);
                            myIntent.putExtra("ANSWER", (String) str);
                            myIntent.putExtra("COUNT", 1);
                            socket.close();
                            serverSocket.close();
                            //startActivity(myIntent);

                            Thread thread1 = new Thread(new Runnable() {
                                public void run() {
                                    startActivity(myIntent);
                                }
                            });
                            thread1.sleep(2000);
                            thread1.run();

                        } catch (Exception e) {
                        }
                    }
                }));
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}