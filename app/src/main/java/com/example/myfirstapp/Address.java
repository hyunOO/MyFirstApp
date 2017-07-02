package com.example.myfirstapp;

import android.provider.BaseColumns;

/**
 * Created by q on 2017-07-01.
 */

public final class Address {
    public static final class table implements BaseColumns{
        public static final String NAME = "name";
        public static final String PHNUMBER = "phNumber";
        public static final String _TABLENAME = "address";
        public static final String _CREATE = "create table " +  _TABLENAME + "(" + _ID + " integer primary key autoincrement, " + NAME + " text not null, " + PHNUMBER + " text not null);";
    }
}
