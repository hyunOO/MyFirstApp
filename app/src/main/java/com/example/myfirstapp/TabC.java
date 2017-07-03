package com.example.myfirstapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public class TabC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_c);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice mRemoteDevice;
        BluetoothSocket mSocket = null;
        OutputStream mOutputStream = null;
        InputStream mInputStream = null;

        if(!mBluetoothAdapter.isEnabled()){
            AlertDialog.Builder alert = new AlertDialog.Builder(TabC.this);
            alert.setTitle("앱 실행 오류");
            alert.setMessage("블루투스를 활성화하세요.");
            alert.show();
        }

        else{
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            for(BluetoothDevice device : pairedDevices){
                device.getName();
                device.getAddress();
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
