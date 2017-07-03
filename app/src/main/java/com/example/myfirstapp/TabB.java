package com.example.myfirstapp;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class TabB extends AppCompatActivity {
    int[] imgid ={R.drawable.img20,R.drawable.img19,R.drawable.img18, R.drawable.img17, R.drawable.img16,R.drawable.img15,R.drawable.img12, R.drawable.img11,R.drawable.img10, R.drawable.img9,R.drawable.img1, R.drawable.img2, R.drawable.img3,R.drawable.img4, R.drawable.img5,R.drawable.img6, R.drawable.img7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_b);

        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imgid);
        GridView gv = (GridView)findViewById(R.id.gridView);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(galleryListener);
    }

    private AdapterView.OnItemClickListener galleryListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long l_position){
            Intent intent = new Intent(getApplicationContext(), Image.class);
            intent.putExtra("img",imgid[position]);
            startActivity(intent);

        }
    };
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
        return imageid[position];
    }
}