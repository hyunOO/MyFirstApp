package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by q on 2017-07-03.
 */

public class QuestTabC_2 extends AppCompatActivity{

    BluetoothAdapter adapter;
    BluetoothDevice device;
    BluetoothSocket socket = null;
    OutputStream output = null;
    InputStream input = null;

    Thread thd = null;

    TextView ques_text;

    int ban_number = 0;
    int ban_limit = 3;

    byte[] buffer;
    int buf_position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_2);
        Intent intent = getIntent();
        final String answer = intent.getExtras().getString("answer");

        final Button ban_button = (Button)findViewById(R.id.ban);
        Button insert_button = (Button)findViewById(R.id.button3);
        ques_text = (TextView)findViewById(R.id.textView7);
        final EditText ans_text = (EditText)findViewById(R.id.textView4);

        insert_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sendData(ans_text.getText().toString());
                ans_text.setText("");

            }
        });
        listenForData();
        ban_button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (ban_number<ban_limit) {
                        ban_number += 1;
                        Toast.makeText(getApplicationContext(), "You banned " + ban_number + " questions.\nYou can ban " + ban_limit + " more questions.", Toast.LENGTH_LONG).show();
                        ques_text.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(),"You can't ban questions.",Toast.LENGTH_LONG).show();
                    }
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
                                            ques_text.setText(data+"\n");
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
}
