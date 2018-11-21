package com.nodeveloper.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.NewsData;
import com.nodeveloper.myapplication.utils.PicassoUtil;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<NewsData> mList;
    private NewsData data;
    private int width, height;
    private WindowManager windowManager;

    public NewsAdapter(Context mContext, List<NewsData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        height = windowManager.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_item, null);

            viewHolder.news_author = convertView.findViewById(R.id.news_author);
            viewHolder.news_date = convertView.findViewById(R.id.news_date);
            viewHolder.news_title = convertView.findViewById(R.id.news_title);

            viewHolder.news_pic1 = convertView.findViewById(R.id.news_pic1);
            viewHolder.news_pic2 = convertView.findViewById(R.id.news_pic2);
            viewHolder.news_pic3 = convertView.findViewById(R.id.news_pic3);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        viewHolder.news_author.setText(data.getAuthor());
        viewHolder.news_date.setText(data.getDate());
        viewHolder.news_title.setText(data.getTitle());

        PicassoUtil.loadImageViewSize(data.getImgUrlOne(), width / 3, 200, viewHolder.news_pic1);
        PicassoUtil.loadImageViewSize(data.getImgUrlTwo(), width / 3, 200, viewHolder.news_pic2);
        PicassoUtil.loadImageViewSize(data.getImgUrlThree(), width / 3, 200, viewHolder.news_pic3);
        return convertView;
    }

    class ViewHolder {
        private ImageView news_pic1;
        private ImageView news_pic2;
        private ImageView news_pic3;
        private TextView news_author;
        private TextView news_date;
        private TextView news_title;
    }
}
