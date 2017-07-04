package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 푸는 사람에 대한 네번째 클래스
    문제를 푸는 사람이 서버의 역할을 하게된다.
    질문을 입력한다.
*/

public class AnswerTabC3  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_2_2);

        Intent intent = getIntent();
        final String str_ans = intent.getStringExtra("ANSWER");
        final String quest = intent.getStringExtra("ANSWERFORQUEST");
        final String ans_pro = intent.getStringExtra("ANSWERHI");
        final int get_count = intent.getIntExtra("COUNT", 0);

        TextView txt_hi = (TextView) findViewById(R.id.question_to_quest);
        txt_hi.setText(quest);

        TextView txt_hello = (TextView) findViewById(R.id.show_answer);
        txt_hello.setText(ans_pro);

        TextView txt = (TextView) findViewById(R.id.count_hello);
        txt.setText(""+get_count);

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
                final BluetoothServerSocket serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("", uuid);

                    final BluetoothSocket socket = serverSocket.accept();
                    final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                    TextView txt1 = (TextView) findViewById(R.id.question_to_quest);
                    txt1.setText(quest);
                    TextView txt2 = (TextView) findViewById(R.id.show_answer);
                    txt2.setText(ans_pro);

                    Button btn1 = (Button) findViewById(R.id.submit_button);
                    btn1.setOnClickListener((new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText edt = (EditText) findViewById(R.id.submit_possible_dap);
                            Object str = edt.getText().toString();
                            try {
                                outputStream.writeObject(str);
                                outputStream.flush();
                                if(str_ans.equals(str)){
                                    Intent myIntent = new Intent(getApplicationContext(), AnswerTabCAnswer.class);
                                    startActivity(myIntent);
                                }
                                else{
                                    if(get_count == 20){
                                        Intent intent = new Intent(getApplicationContext(), AnswerTabCFail.class);
                                        startActivity(intent);
                                    }
                                    final Intent myIntent = new Intent(getApplicationContext(), AnswerTabC1.class);
                                    myIntent.putExtra("ANSWER", str_ans);
                                    myIntent.putExtra("COUNT", get_count+1);
                                    outputStream.close();
                                    serverSocket.close();
                                    socket.close();
                                    startActivity(myIntent);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }));
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
