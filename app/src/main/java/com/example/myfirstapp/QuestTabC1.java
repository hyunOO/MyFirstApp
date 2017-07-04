package com.example.myfirstapp;

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

/**
 * Created by q on 2017-07-03.
 */

public class QuestTabC1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_1_2);


        Intent intent = getIntent();
        Data data = (Data) intent.getSerializableExtra("OBJECT");
        final BluetoothSocket serverSocket = data.getData();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try{
                    ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream());
                    Object obj = ois.readObject();
                    TextView txv = (TextView) findViewById(R.id.textView7);
                    txv.setText(obj.toString());

                    final ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
                    Button btn = (Button) findViewById(R.id.button3);
                    btn.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText edt = (EditText) findViewById(R.id.textView4);
                            Object str = edt.getText().toString();
                            try{
                                oos.writeObject(str);
                                oos.flush();
                            }
                            catch(Exception e){}
                        }
                    }));
                }
                catch(Exception e) { }
            }
        });
        thread1.start();


        /*
        try{
            ObjectInputStream ois = new ObjectInputStream(serverSocket.getInputStream());
            Object obj = ois.readObject();
            TextView txv = (TextView) findViewById(R.id.textView7);
            txv.setText(obj.toString());

            final ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
            Button btn = (Button) findViewById(R.id.button3);
            btn.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText edt = (EditText) findViewById(R.id.textView4);
                    Object str = edt.getText().toString();
                    try{
                        oos.writeObject(str);
                        oos.flush();
                    }
                    catch(Exception e){}
                }
            }));
        }
        catch(Exception e) { }
        */
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}