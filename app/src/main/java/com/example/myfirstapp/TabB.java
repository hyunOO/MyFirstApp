package com.example.myfirstapp;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

public class TabB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_b);

        String[] web = {"google", "github", "instagram", "facebook","d","f","g","h","lk","ds"};

        GalleryAdapter adapter = new GalleryAdapter(TabB.this, web, img);
        GridView gv = (GridView)findViewById(R.id.gridView);
        gv.setAdapter(adapter);

    }
}
public class GalleryAdapter extends BaseAdapter{
    private Context mContext;
    private final String[] mtable;
    private final int[] imageid;

    public GalleryAdapter(Context context){
        super();

        mContext = context;
    }
    public GalleryAdapter(Context context, String[] table, int[] image){
        mContext = context;
        mtable = table;
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
        return mtable.length;
    }
}