package com.nodeveloper.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.ChatListData;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ChatListData data;
    private List<ChatListData> chatListDataList;

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    public ChatListAdapter(Context context, List<ChatListData> chatListDataList) {
        this.context = context;
        this.chatListDataList = chatListDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatListDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatListDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;

        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = view.findViewById(R.id.chat_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = view.findViewById(R.id.chat_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        ChatListData chatListData = chatListDataList.get(i);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(chatListData.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(chatListData.getText());
                break;
        }


        return view;
    }

    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = chatListDataList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
