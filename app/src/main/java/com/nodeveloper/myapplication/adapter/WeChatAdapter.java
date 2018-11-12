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
import com.nodeveloper.myapplication.entity.WeChatData;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.PicassoUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<WeChatData> mList;
    private WeChatData data;
    private int width, height;
    private WindowManager windowManager;

    public WeChatAdapter(Context mContext, List<WeChatData> mList) {
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
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item, null);
            viewHolder.wechat_title = convertView.findViewById(R.id.wechat_title);
            viewHolder.wechat_source = convertView.findViewById(R.id.wechat_source);
            viewHolder.wechat_img = convertView.findViewById(R.id.wechat_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        viewHolder.wechat_title.setText(data.getTitle());
        viewHolder.wechat_source.setText(data.getSource());

        String imgUrl = "https://www.baidu.com/img/bd_logo1.png";


        //PicassoUtil.loadImageView(data.getImgUrl(),viewHolder.wechat_img);
        PicassoUtil.loadImageViewSize(imgUrl, width/3, 200, viewHolder.wechat_img);
        return convertView;
    }

    class ViewHolder {
        private ImageView wechat_img;
        private TextView wechat_title;
        private TextView wechat_source;
    }
}
