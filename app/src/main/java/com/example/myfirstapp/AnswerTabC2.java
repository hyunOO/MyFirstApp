package com.example.myfirstapp;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.ObjectInputStream;

/**
 * Created by q on 2017-07-03.
 */

public class AnswerTabC2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2_2);

        Intent intent = getIntent();
        Data data = (Data) intent.getSerializableExtra("OBJECT");
        final BluetoothSocket clientSocket = data.getData();
        String str = intent.getStringExtra("STRING");

        TextView txv = (TextView) findViewById(R.id.insert_question);
        txv.setText(str);

        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            Object obj = ois.readObject();
            TextView txv1 = (TextView) findViewById(R.id.below_is_answer);
            txv1.setText((String) obj);
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}