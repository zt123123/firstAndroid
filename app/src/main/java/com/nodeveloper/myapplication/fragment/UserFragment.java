package com.nodeveloper.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nodeveloper.myapplication.R;
import com.nodeveloper.myapplication.entity.MyUser;
import com.nodeveloper.myapplication.ui.LoginActivity;
import com.nodeveloper.myapplication.utils.L;
import com.nodeveloper.myapplication.utils.ShareUtils;
import com.nodeveloper.myapplication.utils.StaticClass;
import com.nodeveloper.myapplication.utils.UtilTools;
import com.nodeveloper.myapplication.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener {
    private Button btn_logout, confirm__update_btn;

    private CircleImageView profile_image;

    private TextView et_user_info;

    private CustomDialog dialog;

    private EditText user_update_sex, user_update_age, user_update_desc, user_update_name;

    private Button btn_camera, btn_picture, btn_cancel;

    private File file;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_user, null);

        initView(view);

        return view;
    }

    private void initView(View view) {
        //初始化Dialog
        dialog = new CustomDialog(
                getActivity(),
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                R.layout.dialog_photo,
                R.style.pop_anim_style,
                Gravity.BOTTOM,
                0
        );

        //点击屏幕以外无效
        dialog.setCancelable(false);

        btn_logout = view.findViewById(R.id.user_logout);
        confirm__update_btn = view.findViewById(R.id.confirm__update_btn);

        user_update_sex = view.findViewById(R.id.user_update_sex);
        user_update_age = view.findViewById(R.id.user_update_age);
        user_update_desc = view.findViewById(R.id.user_update_desc);
        user_update_name = view.findViewById(R.id.user_update_name);

        profile_image = view.findViewById(R.id.profile_image);

        et_user_info = view.findViewById(R.id.et_user_info);

        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_logout.setOnClickListener(this);
        confirm__update_btn.setOnClickListener(this);
        et_user_info.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //读取图片
        UtilTools.getImageFromShare(getActivity(), profile_image);

        //默认不可输入
        setEnable(false);

        //设置默认值
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);

        user_update_sex.setText(userInfo.isSex() ? "男" : "女");
        user_update_age.setText(userInfo.getAge() + "");
        user_update_desc.setText(userInfo.getDesc());
        user_update_name.setText(userInfo.getUsername());
    }

    public void setEnable(boolean is) {
        user_update_sex.setEnabled(is);
        user_update_age.setEnabled(is);
        user_update_desc.setEnabled(is);
        user_update_name.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_logout:

                //退出登录  清除缓存
                MyUser.logOut();

                //user为null
                BmobUser currentUser = MyUser.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.et_user_info:
                setEnable(true);
                confirm__update_btn.setVisibility(View.VISIBLE);
                break;
            case R.id.confirm__update_btn:
                String sex = user_update_sex.getText().toString().trim();
                String age = user_update_age.getText().toString().trim();
                String desc = user_update_desc.getText().toString().trim();
                String name = user_update_name.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(sex) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(desc)) {
                    MyUser newUser = new MyUser();
                    newUser.setUsername(name);
                    newUser.setAge(Integer.parseInt(age));
                    newUser.setDesc(!TextUtils.isEmpty(desc) ? desc : "这个人很懒，什么都没留下");
                    newUser.setSex(age.equals("男"));

                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                setEnable(false);
                                confirm__update_btn.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新用户信息成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "更新用户信息失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int CROP_REQUEST_CODE = 102;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    file = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(file));
                    break;
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CROP_REQUEST_CODE:
                    //有可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //删除原来的
                        if (file != null) {
                            file.delete();
                        }
                    }
                    break;
            }
        }
    }

    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }


    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            return;
        }

        //设置裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", true);
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        //截取图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);

        //发送数据
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_REQUEST_CODE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存图片
        UtilTools.putImageToShare(getActivity(), profile_image);
    }
}
