package com.nodeveloper.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.GridData;
import com.nodeveloper.myapplication.utils.PicassoUtil;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private List<GridData> mList;
    private GridData data;
    private LayoutInflater inflater;
    private int width;
    private WindowManager wm;

    public GridAdapter(Context mContext, List<GridData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

            convertView = inflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = convertView.findViewById(R.id.girlImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        PicassoUtil.loadImageViewSize(mList.get(position).getImgUrl(), width / 2, 500, viewHolder.imageView);
//        viewHolder.imageView.setBackgroundResource(R.mipmap.ic_launcher);

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
