package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_c);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button start_button1 = (Button) findViewById(R.id.start_with_answer);
        start_button1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(TabC.this, "앱 실행 실패-블루투스 문제", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
                        alert.setTitle("앱 실행 오류");
                        alert.setMessage("블루투스를 활성화하세요.");
                        alert.show();
                    } else {
                        Intent myIntent = new Intent(getApplicationContext(), AnswerTabC.class);
                        startActivity(myIntent);
                    }
                }

                /*

                */
            }
        }));

        Button start_button2 = (Button) findViewById(R.id.start_with_problem);
        start_button2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBluetoothAdapter == null) {
                    Toast.makeText(TabC.this, "앱 실행 실패-블루투스 문제", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
                        alert.setTitle("앱 실행 오류");
                        alert.setMessage("블루투스를 활성화하세요.");
                        alert.show();
                    } else {
                        Intent myIntent = new Intent(getApplicationContext(), QuestTabC.class);
                        startActivity(myIntent);
                    }
                }

                /*

                */
            }
        }));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Created by q on 2017-07-03.
     */

    public static class AnswerTabC3 extends AppCompatActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dap_3);

            Intent past = getIntent();
            final String answer = past.getExtras().getString("answer");

            Button submit = (Button)findViewById(R.id.submit_button);
            final EditText box = (EditText)findViewById(R.id.submit_possible_dap);
            final Intent again_intent = new Intent(getApplicationContext(),AnswerTabC.class);
            again_intent.putExtra("answer",answer);

            submit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String possible_answer = box.getText().toString();
                    if (possible_answer==answer){
                        Toast.makeText(getApplicationContext(),"정답입니다!",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplicationContext(), "오답입니다.\n다시 시도해보세요!",Toast.LENGTH_LONG).show();
                        startActivity(again_intent);
                    }
                }
            });
        }
    }
}
