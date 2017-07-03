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

public class AnswerTabC extends AppCompatActivity {

    //bluetooth 관련한 생성자들
    BluetoothAdapter adapter;
    BluetoothDevice device;
    BluetoothSocket socket = null;
    OutputStream output = null;
    InputStream input = null;

    Thread thd = null;

    TextView view;

    byte[] buffer;
    int buf_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2);

        final EditText texts = (EditText)findViewById(R.id.question_to_quest);
        Button btn03 = (Button)findViewById(R.id.submit_dap_to_quest);
        view = (TextView)findViewById(R.id.show_answer);

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(texts.getText().toString());
                texts.setText("");
            }
        });

        listenForData();

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
                                            view.setText(data+"\n");
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
