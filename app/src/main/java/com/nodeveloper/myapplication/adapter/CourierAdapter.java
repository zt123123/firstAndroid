package com.nodeveloper.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.CourierData;

import java.util.List;

public class CourierAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourierData> mList;
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context mContext, List<CourierData> mList) {
        this.mContext = mContext;
        this.mList = mList;
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
            convertView = inflater.inflate(R.layout.layout_courier_item, null);
            viewHolder.express_remark = convertView.findViewById(R.id.express_remark);
            viewHolder.express_zone = convertView.findViewById(R.id.express_zone);
            viewHolder.express_datetime = convertView.findViewById(R.id.express_datetime);
            //设置缓存
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.express_remark.setText(data.getRemark());
        viewHolder.express_zone.setText(data.getZone());
        viewHolder.express_datetime.setText(data.getDatetime());

        return convertView;
    }

    class ViewHolder {
        private TextView express_zone, express_datetime, express_remark;

    }
}
