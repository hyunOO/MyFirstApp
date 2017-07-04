package com.example.myfirstapp;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
    문제를 푸는 사람에 대한 두번째 클래스
    문제를 푸는 사람이 클라이언트의 역할을 하게된다.
    질문을 입력하고 답을 받는다.
*/


public class AnswerTabC1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2);

        Intent intent = getIntent();
        Data data = (Data) intent.getSerializableExtra("OBJECT");
        final BluetoothSocket clientSocket = data.getData();

        try{
            final ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            Button btn = (Button) findViewById(R.id.submit_dap_to_quest);
            btn.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edt = (EditText) findViewById(R.id.question_to_quest);
                    Object str = edt.getText().toString();
                    try{
                        oos.writeObject(str);
                        oos.flush();
                        Data data = new Data();
                        data.setData(clientSocket);
                        Intent myIntent = new Intent(getApplicationContext(), AnswerTabC2.class);
                        myIntent.putExtra("OBJECT", data);
                        myIntent.putExtra("STRING", (String) str);
                        startActivity(myIntent);
                    }
                    catch(Exception e){}
                }
            }));
        }
        catch(Exception e){ }


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
