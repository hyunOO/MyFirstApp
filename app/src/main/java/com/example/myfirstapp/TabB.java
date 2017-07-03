package com.example.myfirstapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
    int[] imgid ={R.drawable.img1, R.drawable.img2, R.drawable.img3,R.drawable.img4, R.drawable.img5,R.drawable.img6, R.drawable.img7, R.drawable.img8};
    private final int MY_PERMISSION = 159;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_b);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION);
        }else{

            GalleryAdapter adapter = new GalleryAdapter(TabB.this, imgid);
            GridView gv = (GridView)findViewById(R.id.gridView);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(galleryListener);
        }
    }
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case MY_PERMISSION:{
                if (grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,null, null, null);
                    cursor.moveToFirst();

                    int columnindex = cursor.getInt(cursor.getColumnIndex(projection[0]));
                    int[] temp = {columnindex};
                    setimgid(temp);
                    cursor.close();
                }else{

                }
            }
        }
    }
    public void setimgid(int[] list){
        imgid = list;
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