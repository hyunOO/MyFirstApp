package com.example.myfirstapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 * Created by q on 2017-07-04.
 */

public class CheckableLayout extends RelativeLayout implements Checkable {
    public CheckableLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.check_box) ;
        return cb.isChecked() ;
    }
    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.check_box) ;
        setChecked(cb.isChecked() ? false : true) ;
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.check_box) ;
        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }
    }
}
