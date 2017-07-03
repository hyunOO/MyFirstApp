package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by q on 2017-07-03.
 */

public class AnswerTabC_2 extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap_3);

        Intent past = getIntent();
        final String answer = past.getExtras().getString("answer");

        Button pass = (Button)findViewById(R.id.pass_button);
        Button submit = (Button)findViewById(R.id.submit_button);
        final EditText box = (EditText)findViewById(R.id.submit_possible_dap);
        final Intent again_intent = new Intent(getApplicationContext(),AnswerTabC.class);
        again_intent.putExtra("answer",answer);

        pass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(again_intent);
            }
        });

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
