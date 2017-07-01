package com.example.myfirstapp;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class TabB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_b);

        String[] projection = {MediaStore.Images.Media.DATA};

        ArrayList<Integer> img = new ArrayList<>();
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_index ;

        column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        /*img.add(column_index);

        cursor.close();

        int[] imgid= new int[img.size()];
        for (int i =0; i<img.size(); i++){
            imgid[i] = img.get(i);
        }
*/
        int[] imgid = new int[1];
        imgid[0] = column_index;

        GalleryAdapter adapter = new GalleryAdapter(TabB.this, imgid);
        GridView gv = (GridView)findViewById(R.id.gridView);
        gv.setAdapter(adapter);
    }
}

 class GalleryAdapter extends BaseAdapter{
    private Context mContext;
    private final int[] imageid;

    public GalleryAdapter(Context context, int[] image){
        mContext = context;
        imageid=image;
    }

    public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null){
                gridView = inflater.inflate(R.layout.image_view, null);
                ImageView imageView = (ImageView)gridView.findViewById(R.id.imageView);
                imageView.setImageResource(imageid[position]);
            }else{
                gridView = (View) convertView;
            }
            return gridView;
    }

    public int getCount() {
        return imageid.length;
    }

    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return 0;
    }
}