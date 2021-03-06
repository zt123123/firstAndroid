package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.adapter.GridAdapter;
import com.nodeveloper.myapplication.adapter.WeChatAdapter;
import com.nodeveloper.myapplication.entity.GridData;
import com.nodeveloper.myapplication.entity.WeChatData;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.PicassoUtil;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GirlFragment extends Fragment {
    private GridView mGridView;
    private List<GridData> mList = new ArrayList<>();
    private GridAdapter adapter;
    private CustomDialog customDialog;
    private ImageView imageView;
    private List<String> mListUrl = new ArrayList<>();
    private PhotoViewAttacher photoViewAttacher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_girl, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mGridView = view.findViewById(R.id.mGridView);
        customDialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        imageView = customDialog.findViewById(R.id.iv_img);

        getData();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtil.loadImageView(mListUrl.get(position),imageView);
                photoViewAttacher = new PhotoViewAttacher(imageView);
                //刷新
                photoViewAttacher.update();
                //显示你dialog
                customDialog.show();
            }
        });

    }

    private void getData() {
        String url = StaticClass.GIRL_URL;

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

    public void parseJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonList = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonList.length(); i++) {
                JSONObject json = (JSONObject) jsonList.get(i);

                String url = json.getString("url");

                mListUrl.add(url);

                GridData data = new GridData();
                data.setImgUrl(url);

                mList.add(data);
            }


            adapter = new GridAdapter(getContext(), mList);
            mGridView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
