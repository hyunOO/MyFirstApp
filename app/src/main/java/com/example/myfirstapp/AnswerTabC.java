package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 푸는 사람에 대한 클래스
    문제를 푸는 사람이 클라이언트의 역할을 하게된다.
*/

public class AnswerTabC extends AppCompatActivity {
    InputStream input = null;
    String answer= null;// the final answer for 스무고개

    Thread thd= null;

    byte[] buffer;
    int buf_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_1);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device = null;


        Intent intent = new Intent(getApplicationContext(), AnswerTabC.class);

        if (answer!=null){
            intent.putExtra("answer",answer);
            startActivity(intent);
        }
        listenForData();

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
                clientSocket.connect();

                Thread thread1 = new Thread(new Runnable() {
                    public void run() {
                        try {
                            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                            Object obj = inputStream.readObject();

                            Toast.makeText(getApplicationContext(), ""+obj, Toast.LENGTH_LONG).show();
                            Data data = new Data();
                            data.setData(clientSocket);
                            Intent myIntent = new Intent(getApplicationContext(), AnswerTabC1.class);
                            myIntent.putExtra("OBJECT", data);
                            startActivity(myIntent);
                        } catch (Exception e) {
                        }
                    }
                });
                thread1.start();


/*
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                Object obj = inputStream.readObject();
                Toast.makeText(getApplicationContext(), ""+obj, Toast.LENGTH_LONG).show();
                Data data = new Data();
                data.setData(clientSocket);
                Intent myIntent = new Intent(getApplicationContext(), AnswerTabC1.class);
                myIntent.putExtra("OBJECT", data);
                startActivity(myIntent);
*/
            }
            catch (Exception e) {
            }
        }
    }

    public void listenForData(){
        final Handler handler = new Handler();
        buf_position=0;
        buffer= new byte[1024];

        thd = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        int byteAvailable = input.available(); //input.available은 다른 쓰레드에서 blocking 하기 전까지 읽을 수 있는 문자열 개수
                        if (byteAvailable>0){
                            byte[] packet = new byte[byteAvailable];
                            input.read(packet);
                            for (int i =0; i<byteAvailable; i++){
                                byte b = packet[i];
                                if (b =='\n'){
                                    byte[] encodedBytes = new byte[buf_position];
                                    System.arraycopy(buffer,0, encodedBytes,0,encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    buf_position= 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            answer=data;
                                        }
                                    });
                                }else{
                                    buffer[buf_position++]=b;
                                }
                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),"데이터 수신 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }

    private class BackgroundThread extends Thread {
        private BluetoothSocket bsk;

        public void BackgroundThread(BluetoothSocket bsk) {
            this.bsk = bsk;
        }

        public void run() {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(bsk.getInputStream());
                Object obj = inputStream.readObject();

                Data data = new Data();
                data.setData(bsk);
                Intent myIntent = new Intent(getApplicationContext(), AnswerTabC1.class);
                myIntent.putExtra("OBJECT", data);
                startActivity(myIntent);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

