package com.example.myfirstapp;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.database.Cursor;
import android.widget.BaseAdapter;
import android.view.View;

public class TabB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_b);

        String[] img = {MediaStore.Images.Thumbnails._ID};
        Cursor imageCursor = managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,img, null,null,MediaStore.Images.Thumbnails.IMAGE_ID+"");
        imageColumnIndex = imageCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

        gridAdapter adapter = new gridAdapter();
        GridView gv = (GridView)findViewById(R.id.gridView);
        gv.setAdapter(adapter);

    }
}
public class gridAdapter extends BaseAdapter{

    public gridAdapter(Context context){
        super();

        mContext = context;
    }
}
