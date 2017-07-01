package com.example.myfirstapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabA extends AppCompatActivity {
    ListView listView;
    int add = 0;
    Button addButton, modifyButton, deleteButton, synchButton;
    CustomChoiceListViewAdapter adapter1 = new CustomChoiceListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter1);

        addButton = (Button) findViewById(R.id.add);
        modifyButton = (Button) findViewById(R.id.modify);
        deleteButton = (Button) findViewById(R.id.delete);
        synchButton = (Button) findViewById(R.id.synchro);

        adapter1.addItem("강현우", "010-2423-7147");
        adapter1.addItem("김근우", "010-2014-9608");
        adapter1.addItem("황재영", "010-2227-2487");
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.dialog, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(TabA.this);
                alert.setTitle("전화번호 추가");
                alert.setView(layout);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        final EditText eName = (EditText) layout.findViewById(R.id.name);
                        final EditText ephNumber = (EditText) layout.findViewById(R.id.phNumber);
                        String name = eName.getText().toString();
                        String phNumber = ephNumber.getText().toString();
                        adapter1.addItem(name, phNumber);
                        adapter1.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int  checked;
                checked = listView.getCheckedItemPosition();
                if(checked > -1){
                    adapter1.deleteItem(checked);
                    adapter1.notifyDataSetChanged();
                }
            }
        });

        synchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadContactsAsync lca = new LoadContactsAsync();
                lca.execute();
            }
        });
    }

    class LoadContactsAsync extends AsyncTask<Void, Void, ArrayList<String>>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params){
            ArrayList<String> contacts = new ArrayList<String>();
            Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while(c.moveToNext()) {
                String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(contactName+":"+phNumber);
                adapter1.addItem(contactName, phNumber);
            }
            c.close();
            return contacts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> contacts){
            super.onPostExecute(contacts);
            listView.setAdapter(adapter1);
        }
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
