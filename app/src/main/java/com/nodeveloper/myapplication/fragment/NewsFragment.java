package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.adapter.NewsAdapter;
import com.nodeveloper.myapplication.adapter.WeChatAdapter;
import com.nodeveloper.myapplication.entity.NewsData;
import com.nodeveloper.myapplication.entity.WeChatData;
import com.nodeveloper.myapplication.ui.WebViewActivity;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private ListView news_listview;

    private List<NewsData> mList = new ArrayList<>();

    private List<String> listTitle = new ArrayList<>();
    private List<String> listUrl = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_news, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        news_listview = view.findViewById(R.id.news_listview);
        getData();

        news_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);

                String title = listTitle.get(i);
                String url = listUrl.get(i);

                intent.putExtra("title", title);
                intent.putExtra("url", url);

                startActivity(intent);
            }
        });
    }

    public void getData() {
        String url = StaticClass.NEWS_URL;

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //解析json
                L.w("----start----");
                L.w(t);
                L.w("----end----");
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
            JSONArray jsonList = jsonResult.getJSONArray("data");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);
                NewsData newsData = new NewsData();

                newsData.setTitle(json.getString("title"));
                newsData.setAuthor(json.getString("author_name"));
                newsData.setDate(json.getString("date"));

                newsData.setImgUrlOne(json.getString("thumbnail_pic_s"));
                try{
                    newsData.setImgUrlTwo(json.getString("thumbnail_pic_s02"));
                    newsData.setImgUrlThree(json.getString("thumbnail_pic_s03"));
                }catch (Exception e){
                    e.printStackTrace();
                }

                listTitle.add(json.getString("title"));
                listUrl.add(json.getString("url"));

                mList.add(newsData);
            }


            NewsAdapter adapter = new NewsAdapter(getContext(), mList);
            news_listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
