package com.example.myfirstapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;

import static com.example.myfirstapp.R.layout.dialog;
import static com.example.myfirstapp.R.layout.search;

public class TabA extends AppCompatActivity {

    ListView listView;
    Button addButton, modifyButton, deleteButton, synchButton;
    CustomChoiceListViewAdapter adapter1 = new CustomChoiceListViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(adapter1);

        AssetManager assetManager = getResources().getAssets();
        try{
            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream) assetManager.open("address.jason");
            BufferedReader br = new BufferedReader(new InputStreamReader(ais));
            StringBuilder sb = new StringBuilder();
            int bufferSize = 1024 * 1024;
            char readBuf[] = new char[bufferSize];
            int resultSize = 0;
            while((resultSize = br.read(readBuf)) != -1){
                if(resultSize == bufferSize)
                    sb.append(readBuf);
                else{
                    for(int i = 0; i< resultSize; i++)
                        sb.append(readBuf[i]);
                }
            }
            String jString = sb.toString();
            JSONObject jsonObject = new JSONObject(jString);
            JSONArray jArray = new JSONArray(jsonObject.getString("address"));
            int addrlen = jArray.length();
            for(int i = 0; i<addrlen; i++){
                String name = jArray.getJSONObject(i).getString("name").toString();
                String phNumber = jArray.getJSONObject(i).getString("phNumber").toString();
                ListViewItem data = new ListViewItem();
                data.setText1(name);
                data.setText2(phNumber);
                adapter1.add(data);
            }
        } catch(Exception e){

        }

        addButton = (Button) findViewById(R.id.add);
        modifyButton = (Button) findViewById(R.id.modify);
        deleteButton = (Button) findViewById(R.id.delete);
        synchButton = (Button) findViewById(R.id.synchro);

        //ArrayList<ListViewItem> lvi = getContactList();
        //for(int i = 0; i< lvi.size(); i++){
          //  adapter1.add(lvi.get(i));
        //}

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(dialog, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(TabA.this);
                alert.setTitle("전화번호 추가");
                alert.setView(layout);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText eName = (EditText) layout.findViewById(R.id.name);
                        final EditText ephNumber = (EditText) layout.findViewById(R.id.phNumber);
                        String name = eName.getText().toString();
                        String phNumber = ephNumber.getText().toString();
                        ListViewItem data = new ListViewItem();
                        data.setText1(name);
                        data.setText2(phNumber);
                        adapter1.add(data);
                        try{
                            JSONObject obj = new JSONObject();
                            obj.put("name", name);
                            obj.put("phNumber", phNumber);
                            String jsonString = obj.toString();
                            FileWriter fw = new FileWriter("address.jason");
                            fw.write(jsonString);
                        } catch(Exception e){

                        }
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int checked = listView.getCheckedItemPosition();
                AlertDialog.Builder alert = new AlertDialog.Builder(TabA.this);
                alert.setTitle("정말");
                alert.setMessage(checked);
                alert.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = listView.getCheckedItemPosition();
                adapter1.delete(checked);
            }
        });

        synchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(search, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(TabA.this);
                alert.setTitle("전화번호 검색");
                alert.setView(layout);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText eName = (EditText) layout.findViewById(R.id.search_name);
                        AssetManager assetManager = getResources().getAssets();
                        try{
                            AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream) assetManager.open("address.jason");
                            BufferedReader br = new BufferedReader(new InputStreamReader(ais));
                            StringBuilder sb = new StringBuilder();
                            int bufferSize = 1024 * 1024;
                            char readBuf[] = new char[bufferSize];
                            int resultSize = 0;
                            while((resultSize = br.read(readBuf)) != -1){
                                if(resultSize == bufferSize)
                                    sb.append(readBuf);
                                else{
                                    for(int i = 0; i< resultSize; i++)
                                        sb.append(readBuf[i]);
                                }
                            }
                            String jString = sb.toString();
                            JSONObject jsonObject = new JSONObject(jString);
                            JSONArray jArray = new JSONArray(jsonObject.getString("address"));
                            int addrlen = jArray.length();
                            boolean eXist = true;
                            for(int i = 0; i<addrlen; i++){
                                String name = jArray.getJSONObject(i).getString("name").toString();
                                String searchedname = eName.getText().toString();
                                if(searchedname.equals(name)){
                                    eXist = false;
                                    String phNumber = jArray.getJSONObject(i).getString("phNumber").toString();
                                    final AlertDialog.Builder result = new AlertDialog.Builder(TabA.this);
                                    result.setTitle("전화번호 검색결과");
                                    result.setMessage(phNumber);
                                    result.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                       public void onClick(DialogInterface di, int whichButton){

                                       }
                                    });
                                    result.show();
                                }
                            }
                            if(eXist) {
                                final AlertDialog.Builder result = new AlertDialog.Builder(TabA.this);
                                result.setTitle("전화번호 검색결과");
                                result.setMessage("검색결과가 없습니다");
                                result.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface di, int whichButton) {

                                    }
                                });
                                result.show();
                            }
                        } catch(Exception e){

                        }
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }

    private ArrayList<ListViewItem> getContactList(){
        Cursor cursor = null;
        ArrayList<ListViewItem> lvi = new ArrayList<>();
        try {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String phoneName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
            String[] ad = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
            cursor = getContentResolver().query(uri, ad, null, null, phoneName);
            cursor.moveToFirst();
             do {
                if (cursor.getString(1) != null) {
                    ListViewItem lv = new ListViewItem();
                    lv.setText1(cursor.getString(0));
                    lv.setText2(cursor.getString(1));
                    lvi.add(lv);
                }
            } while (cursor.moveToNext());
        } catch(Exception e){

        } finally{
            if(cursor != null){
                cursor.close();
                cursor = null;
            }
            return lvi;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
