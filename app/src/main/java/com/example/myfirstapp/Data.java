package com.example.myfirstapp;

import android.bluetooth.BluetoothSocket;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by q on 2017-07-03.
 */

public class Data implements Serializable {
    private BluetoothSocket bsk;
/*
    public Data(BluetoothSocket bsk){
        this.bsk = bsk;
    }
    public Data(Parcel src){
        bsk = src.readByteArray();
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){

    }
*/
    public BluetoothSocket getData(){
        return bsk;
    }
    public void setData(BluetoothSocket bsk){
        this.bsk = bsk;
    }

}
