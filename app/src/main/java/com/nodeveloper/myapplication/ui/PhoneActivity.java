package com.nodeveloper.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.adapter.CourierAdapter;
import com.nodeveloper.myapplication.entity.CourierData;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Collections;

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private EditText phone_query;
    private ImageView business_pic;
    private TextView query_result;

    private Button
            btn_num0,
            btn_num1,
            btn_num2,
            btn_num3,
            btn_num4,
            btn_num5,
            btn_num6,
            btn_num7,
            btn_num8,
            btn_num9,
            btn_numdel,
            phone_query_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        phone_query = findViewById(R.id.phone_query);
        business_pic = findViewById(R.id.business_pic);
        query_result = findViewById(R.id.query_result);

        btn_num0 = findViewById(R.id.btn_num0);
        btn_num1 = findViewById(R.id.btn_num1);
        btn_num2 = findViewById(R.id.btn_num2);
        btn_num3 = findViewById(R.id.btn_num3);
        btn_num4 = findViewById(R.id.btn_num4);
        btn_num5 = findViewById(R.id.btn_num5);
        btn_num6 = findViewById(R.id.btn_num6);
        btn_num7 = findViewById(R.id.btn_num7);
        btn_num8 = findViewById(R.id.btn_num8);
        btn_num9 = findViewById(R.id.btn_num9);
        btn_numdel = findViewById(R.id.btn_numdel);
        phone_query_btn = findViewById(R.id.phone_query_btn);

        btn_num0.setOnClickListener(this);
        btn_num1.setOnClickListener(this);
        btn_num2.setOnClickListener(this);
        btn_num3.setOnClickListener(this);
        btn_num4.setOnClickListener(this);
        btn_num5.setOnClickListener(this);
        btn_num6.setOnClickListener(this);
        btn_num7.setOnClickListener(this);
        btn_num8.setOnClickListener(this);
        btn_num9.setOnClickListener(this);
        btn_numdel.setOnClickListener(this);
        phone_query_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str = phone_query.getText().toString();

        switch (v.getId()) {
            case R.id.btn_num0:
            case R.id.btn_num1:
            case R.id.btn_num2:
            case R.id.btn_num3:
            case R.id.btn_num4:
            case R.id.btn_num5:
            case R.id.btn_num6:
            case R.id.btn_num7:
            case R.id.btn_num8:
            case R.id.btn_num9:
                phone_query.setText(str + ((Button) v).getText());
                phone_query.setSelection(str.length() + 1);
                break;
            case R.id.btn_numdel:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    phone_query.setText(str.substring(0, str.length() - 1));
                    phone_query.setSelection(str.length() - 1);
                }
                break;
            case R.id.phone_query_btn:
                getPhone(str);
                break;
        }
    }

    private void getPhone(String str) {
        String url = StaticClass.BELONG_QUERY_URL + "&phone=" + str;

        query_result.setText("");

        if (!TextUtils.isEmpty(str)) {
            RxVolley.get(url, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    //解析json
                    parseJSON(t);
                }

                @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                    Toast.makeText(PhoneActivity.this, "查询失败：" + strMsg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(PhoneActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    //解析数据
    private void parseJSON(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");

            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            query_result.setText(
                    "归属地:" + province + city + "\n" +
                            "区号:" + areacode + "\n" +
                            "邮编:" + zip + "\n" +
                            "运营商:" + company + "\n" +
                            "类型:" + card + "\n"
            );
            switch (company) {
                case "移动":
                    business_pic.setBackgroundResource(R.drawable.icon_mobile);
                    break;
                case "联通":
                    business_pic.setBackgroundResource(R.drawable.icon_unicom);
                    break;
                case "电信":
                    business_pic.setBackgroundResource(R.drawable.icon_telecom);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
