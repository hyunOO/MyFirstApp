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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;

/*
    문제를 내는 사람에 대한 클래스
    문제를 내는 사람이 서버의 역할을 하게된다.
    받은 질문에 대한 답을 한다.
*/


public class QuestTabC2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_2);

        Intent intent = getIntent();
        final String hi_ans = intent.getStringExtra("ANSWER");
        final String quest = intent.getStringExtra("QUESTION");
        TextView txt = (TextView) findViewById(R.id.textView7965);
        txt.setText(quest);
        final int get_count = intent.getIntExtra("COUNT", 0);
        TextView txv = (TextView) findViewById(R.id.count_hi);
        txv.setText(""+get_count);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        BluetoothDevice target_device = null;

        if (pairedDevices.size() != 1) {

        } else {
            for (BluetoothDevice device : pairedDevices) {
                target_device = device;
            }
            try {
                String s = "FFFFFFFF-C917-212C-D958-11A94F3D97CA";
                String s2 = s.replace("-", "");
                UUID uuid = new UUID(new BigInteger(s2.substring(0, 16), 16).longValue(), new BigInteger(s2.substring(16), 16).longValue());
                final BluetoothServerSocket serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("", uuid);

                    final BluetoothSocket socket = serverSocket.accept();
                    final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                    Button btn01 = (Button)findViewById(R.id.button36879);

                    btn01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView txv = (TextView) findViewById(R.id.textView4567);
                            String str = txv.getText().toString();
                            try{
                                outputStream.writeObject(str);
                                outputStream.flush();
                                Intent intent = new Intent (getApplicationContext(), QuestTabC3.class);
                                intent.putExtra("QUEST", quest);
                                intent.putExtra("ANSWER", hi_ans);
                                intent.putExtra("COUNT", get_count);
                                socket.close();
                                startActivity(intent);
                            }
                            catch(Exception e) {}
                        }
                    });
            } catch (IOException e) {
            }
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
