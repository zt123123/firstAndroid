package com.nodeveloper.myapplication.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoUtil {
    //默认加载
    public static void loadImageView(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    //默认加载图片  指定大小
    public static void loadImageViewSize(String url, int width, int height, ImageView imageView) {
        Picasso.get()
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //加载图片有默认图片
    public static void loadImageViewHolder(String url, int loadImg, int errorImg, ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }

    //裁剪图片
    public static void loadImageViewCrop(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }

    //按比例裁剪 矩形
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "lgl";
        }
    }
}
