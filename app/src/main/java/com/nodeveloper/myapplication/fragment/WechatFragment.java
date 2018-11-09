package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.adapter.CourierAdapter;
import com.nodeveloper.myapplication.adapter.WeChatAdapter;
import com.nodeveloper.myapplication.entity.CourierData;
import com.nodeveloper.myapplication.entity.WeChatData;
import com.nodeveloper.myapplication.ui.CourierActivity;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WechatFragment extends Fragment {
    private ListView wechat_listview;

    private List<WeChatData> mList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_wechat, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        wechat_listview = view.findViewById(R.id.wechat_listview);
        getData();
    }

    public void getData() {
        String url = StaticClass.WECHAT_ARTICLE_URL;

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //解析json
                L.w(t);
                parseJSON(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                Toast.makeText(getActivity(), "查询失败：" + strMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //解析数据
    private void parseJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData weChatData = new WeChatData();

                weChatData.setTitle(json.getString("title"));
                weChatData.setSource(json.getString("source"));
                weChatData.setImgUrl(json.getString("firstImg"));

                mList.add(weChatData);
            }


            WeChatAdapter adapter = new WeChatAdapter(getContext(), mList);
            wechat_listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
