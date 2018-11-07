package com.nodeveloper.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.ShareUtils;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.TextUtils;

public class CourierActivity extends BaseActivity implements View.OnClickListener {
    private EditText express_name;
    private EditText express_order;
    private Button express_query_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }

    private void initView() {
        express_name = findViewById(R.id.express_name);
        express_order = findViewById(R.id.express_order);

        express_query_btn = findViewById(R.id.express_query_btn);

        express_query_btn.setOnClickListener(this);
    }

    public void expressQuery() {
        String name = express_name.getText().toString().trim();
        String order = express_order.getText().toString().trim();
        String url = StaticClass.EXPRESS_QUERY_URL + "&com=" + name + "&no=" + order;

        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(order)) {
            RxVolley.get(url, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    L.i(t);
                    Toast.makeText(CourierActivity.this, "请求到的数据:" + t, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                    Toast.makeText(CourierActivity.this, "查询失败：" + strMsg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(CourierActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.express_query_btn:
                expressQuery();
                break;
        }
    }
}
