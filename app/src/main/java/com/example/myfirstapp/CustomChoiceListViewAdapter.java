package com.example.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myfirstapp.CheckableLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by q on 2017-07-01.
 */

public class CustomChoiceListViewAdapter extends BaseAdapter {

    private ArrayList <ListViewItem> listViewItemList= new ArrayList<ListViewItem>();

    public CustomChoiceListViewAdapter(){
    }

    @Override
    public int getCount(){
        return listViewItemList.size();
    }

    public void setChecked(int position){
        ListViewItem item = getItem(position);
        boolean original = item.getChecked();
        item.setCheck(!original);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }
        ListViewItem lv = listViewItemList.get(position);
        CheckBox cb_check = (CheckBox)convertView.findViewById(R.id.check_box);
        cb_check.setChecked(((ListView)parent).isItemChecked(position));

        if(lv != null){
            TextView textTextView1 = (TextView) convertView.findViewById(R.id.textView1) ;
            TextView textTextView2 = (TextView) convertView.findViewById(R.id.textView2) ;
            if(textTextView1 != null)
                textTextView1.setText(lv.getText1());
            if(textTextView2 != null)
                textTextView2.setText(lv.getText2());
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }
    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String text1, String text2){
        ListViewItem item = new ListViewItem();
        item.setText1(text1);
        item.setText2(text2);
        listViewItemList.add(item);
    }

    public void add(ListViewItem data){
        listViewItemList.add(data);
        notifyDataSetChanged();
    }



    /*public void deleteItem(int checked){
        ListViewItem item = listViewItemList.get(checked);
        listViewItemList.remove(checked);
    }

    public void delete(ListViewItem data){
        listViewItemList.remove(data);
        notifyDataSetChanged();
    }

    public void delete(int index){
        listViewItemList.remove(index);
        notifyDataSetChanged();
    }*/
}
