package com.nodeveloper.myapplication.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class UtilTools {
    public static void setFont(Context mContext, TextView textview){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/Fang.ttf");
        textview.setTypeface(fontType);
    }
}
