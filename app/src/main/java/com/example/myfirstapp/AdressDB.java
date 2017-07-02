package com.example.myfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by q on 2017-07-01.
 */

public class AdressDB {
    private static final String DATABASE_NAME = "address.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Address.table._CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + Address.table._TABLENAME);
            onCreate(db);
        }
        /*
        public AdressDB(Context context){
            this.mCtx = context;
        }
        */
        public AdressDB.DatabaseHelper open() throws SQLException{
            mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
            mDB = mDBHelper.getWritableDatabase();
            return this;
        }

        public void close(){
            mDB.close();
        }

        public long insertAdress(String name, String phNumber){
            ContentValues values = new ContentValues();
            values.put(Address.table.NAME, name);
            values.put(Address.table.PHNUMBER, phNumber);
            return mDB.insert(Address.table._TABLENAME, null, values);
        }

        public boolean updateAddress(long id, String name, String phNumber){
            ContentValues values = new ContentValues();
            values.put(Address.table.NAME, name);
            values.put(Address.table.PHNUMBER, phNumber);
            return mDB.update(Address.table._TABLENAME, values, "_id=" + id, null) > 0;
        }

        public boolean deleteAddress(long id){
            return mDB.delete(Address.table._TABLENAME, "_id=" + id, null) > 0;
        }

        public Cursor getAddresses(){
            return mDB.query(Address.table._TABLENAME, null, null, null, null, null, null);
        }

        public Cursor getAddress(long id){
            Cursor c = mDB.query(Address.table._TABLENAME, null, "_id=" + id, null, null, null, null, null);
            if(c != null && c.getCount() != 0)
                c.moveToFirst();
            return c;
        }
    }
}
