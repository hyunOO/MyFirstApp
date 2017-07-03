package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by q on 2017-07-03.
 */

public class AnswerTabC_0 extends AppCompatActivity{

    InputStream input = null;
    String answer= null;// the final answer for 스무고개

    Thread thd= null;

    byte[] buffer;
    int buf_position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_1);

        Intent intent = new Intent(getApplicationContext(), AnswerTabC.class);

        if (answer!=null){
            intent.putExtra("answer",answer);
            startActivity(intent);
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
}
