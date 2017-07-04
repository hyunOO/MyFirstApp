package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 푸는 사람에 대한 두번째 클래스
    문제를 푸는 사람이 서버의 역할을 하게된다.
    질문을 입력한다.
*/


public class AnswerTabC1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2);

        Intent intent = getIntent();
        final String str_ans = intent.getStringExtra("ANSWER");
        final int get_count = intent.getIntExtra("COUNT", 0);
        TextView txv = (TextView) findViewById(R.id.count);
        txv.setText(""+get_count);

        if(get_count == 1){
            TextView txt = (TextView) findViewById(R.id.when_wrong);
            txt.setVisibility(View.GONE);
        }

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device = null;

        if (pairedDevices.size() != 1) {

        } else {
            for (BluetoothDevice device : pairedDevices) {
                target_device = device;
            }
            try {
                String s = "550e8400-e29b-41d4-a716-446655440000";
                String s2 = s.replace("-", "");
                UUID uuid = new UUID(new BigInteger(s2.substring(0, 16), 16).longValue(), new BigInteger(s2.substring(16), 16).longValue());
                final BluetoothServerSocket serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("", uuid);
                final BluetoothSocket socket = serverSocket.accept();
                final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                Button btn = (Button) findViewById(R.id.submit_dap_to_quest);
                btn.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edt = (EditText) findViewById(R.id.question_to_quest);
                        final Object str = edt.getText().toString();
                        try {

                            Thread thread1 = new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        outputStream.writeObject(str);
                                        outputStream.flush();
                                        final Intent myIntent = new Intent(getApplicationContext(), AnswerTabC2.class);
                                        myIntent.putExtra("ANSWER", str_ans);
                                        myIntent.putExtra("ANSWERFORQUEST", (String) str);
                                        myIntent.putExtra("COUNT", get_count);
                                        outputStream.close();
                                        serverSocket.close();
                                        socket.close();
                                        startActivity(myIntent);
                                    } catch (Exception e) {
                                    }
                                }
                            });
                            thread1.sleep(2000);
                            thread1.run();
                        }
                        catch(Exception e) { }

/*
                        try {
                            outputStream.writeObject(str);
                            outputStream.flush();
                            final Intent myIntent = new Intent(getApplicationContext(), AnswerTabC2.class);
                            myIntent.putExtra("ANSWER", str_ans);
                            myIntent.putExtra("ANSWERFORQUEST", (String) str);
                            myIntent.putExtra("COUNT", get_count);
                            outputStream.close();
                            serverSocket.close();
                            socket.close();

                            Thread thread1 = new Thread(new Runnable() {
                                public void run() {
                                    startActivity(myIntent);
                                }
                            });
                            thread1.sleep(2000);
                            thread1.run();

                            startActivity(myIntent);
                        } catch (Exception e) {
                        }
                        */
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
