package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.adapter.ChatListAdapter;
import com.nodeveloper.myapplication.entity.ChatListData;
import com.nodeveloper.myapplication.utils.L;

import java.util.ArrayList;
import java.util.List;

public class ButlerFragment extends Fragment implements View.OnClickListener {
    private ListView chat_listview;
    private Button btn_left;
    private Button btn_right;
    private List<ChatListData> listData = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_butler, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        chat_listview = view.findViewById(R.id.chat_listview);

        btn_left = view.findViewById(R.id.btn_left);
        btn_right = view.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        adapter = new ChatListAdapter(getActivity(), listData);

        chat_listview.setAdapter(adapter);

        addLeftItem("你好啊 小管家111");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                addLeftItem("左边");
                break;
            case R.id.btn_right:
                addRightItem("右边");
                break;
        }
    }

    public void addLeftItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        listData.add(data);
        adapter.notifyDataSetChanged();
        chat_listview.setSelection(chat_listview.getBottom());
    }

    public void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        listData.add(data);
        adapter.notifyDataSetChanged();
        chat_listview.setSelection(chat_listview.getBottom());

    }
}
