package com.nodeveloper.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {
    public static void setFont(Context mContext, TextView textview) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/Fang.ttf");
        textview.setTypeface(fontType);
    }

    //保存图片到SharedPreferences
    public static void putImageToShare(Context context, ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();

        Bitmap bitmap = bitmapDrawable.getBitmap();
        //1. 将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //2. Base64转换成string
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //3. 将String保存在shareUtils
        ShareUtils.putString(context, StaticClass.IMAGE_TITLE, imgString);
    }

    //从SharedPreferences读取图片
    public static void getImageFromShare(Context context, ImageView imageView) {
        //1. 拿到string
        String imgString = ShareUtils.getString(context, StaticClass.IMAGE_TITLE, "");
        if (!imgString.equals("")) {
            //2. Base64转换string
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

            //3. 生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}
