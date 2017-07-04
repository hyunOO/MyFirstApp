package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 내는 사람에 대한 네번째 클래스
    문제를 내는 사람이 클라이언트의 역할을 하게된다.
    질문자가 답을 입력할 때 까지 기다린다.
*/


public class QuestTabC3  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_3);

        Intent intent = getIntent();
        final String answer =intent.getStringExtra("ANSWER");
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
                String s = "FFFFFFFF-C421-76C9-7F0D-7EF0253DB6E4";
                String s2 = s.replace("-", "");
                UUID uuid = new UUID(new BigInteger(s2.substring(0, 16), 16).longValue(), new BigInteger(s2.substring(16), 16).longValue());
                final BluetoothSocket clientSocket = target_device.createRfcommSocketToServiceRecord(uuid);
                //clientSocket.connect();

                Thread thread1 = new Thread(new Runnable() {
                    public void run() {
                        try{

                            if(mBluetoothAdapter.isDiscovering()){
                                mBluetoothAdapter.cancelDiscovery();
                            }

                            clientSocket.connect();
                            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                            String obj = (String) ois.readObject();

                            if(obj.equals(answer)){
                                Intent myIntent = new Intent(getApplicationContext(), QuestTabCAnswer.class);
                                clientSocket.close();
                                startActivity(myIntent);
                            }
                            else{
                                if(get_count == 20){
                                    Intent myIntent = new Intent(getApplicationContext(), QuestTabCFail.class);
                                    startActivity(myIntent);
                                }
                                final Intent myIntent = new Intent(getApplicationContext(), QuestTabC1.class);
                                myIntent.putExtra("ANSWER", answer);
                                myIntent.putExtra("COUNT", get_count+1);
                                clientSocket.close();
                                //startActivity(myIntent);

                                Thread thread1 = new Thread(new Runnable() {
                                    public void run() {
                                        startActivity(myIntent);
                                    }
                                });
                                thread1.sleep(2000);
                                thread1.run();

                            }
                        }
                        catch (Exception e) {
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
    public void onBackPressed(){
        super.onBackPressed();
    }
}
