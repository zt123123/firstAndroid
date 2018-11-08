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
import android.widget.TextView;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.utils.L;

public class ButlerFragment extends Fragment implements View.OnClickListener {
    private TextView btn_test;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_butler, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btn_test = view.findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:

                break;
        }
    }
}
