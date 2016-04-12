package com.timmy.ui.technologypoint;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.ui.BaseActivity;
import com.timmy.view.BottomSelectDialog;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraPictureActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView iv_image;


//    @Bind(R.id.tv_take_photo)
//    TextView tv_takePhoto;
//    @Bind(R.id.tv_pick_photo)
//    TextView tv_pickPhoto;
//    @Bind(R.id.tv_cancel)
//    TextView tv_cancel;
    private BottomSelectDialog selectDialog;
    private Uri photoUri;

    //使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    //图片裁剪
    private static final int CROP_RESULT = 3;
    private BottomSelectDialog takepd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_picture);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick({R.id.btn_picture})
    public void selectStyle(View view) {
        switch (view.getId()) {
            case R.id.btn_picture:
                showTakePhoto();
                break;
        }
    }


//    @OnClick({R.id.tv_take_photo, R.id.tv_pick_photo, R.id.tv_cancel})
    public void getPhoto(View view) {

        switch (view.getId()) {
            case R.id.tv_cancel:

                if (selectDialog != null) {
                    selectDialog.dismiss();
                }
                break;
            case R.id.tv_take_photo:
                takePhoto();
                break;
            case R.id.tv_pick_photo:
                pickPhoto();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    final Intent data) {
        if (resultCode == Activity.RESULT_OK) { // 头像上传,不光要上传到即来社区的头像库中,也需要上传到聊天的头像库中
            doPhoto(requestCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 选择图片后，获取图片的路径
     *
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode, Intent data) {
        // 从相册取图片，有些手机有异常情况，请注意
        if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
            if (data == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if (photoUri == null) {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            this.startPhotoZoom(photoUri);
        } else if (requestCode == SELECT_PIC_BY_TACK_PHOTO) {
            this.startPhotoZoom(photoUri);
        } else if (requestCode == CROP_RESULT) {
            uploadimg(data);
        }
    }

    private void uploadimg(final Intent data) {
        Bitmap bitmap = data.getExtras().getParcelable("data");
        Bitmap imageBitmap = Util.getRoundedCornerBitmap(bitmap, iv_image.getHeight());
        iv_image.setImageBitmap(imageBitmap);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_RESULT);
    }

    private void takePhoto() {
// 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            /** ----------------- */
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        } else {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    private void pickPhoto() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "output_image.jpg");
        Uri imageUri = Uri.fromFile(outputImage);

        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 此处调用了图片选择器
        // 如果直接写intent.setDataAndType("image/*");
        // 调用的是系统图库
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    private void showTakePhoto() {
//        View contentView = LayoutInflater.from(this).inflate(
//                R.layout.view_select_picture, null);
//        ButterKnife.bind(this,contentView);
//
//        selectDialog = Util.showDialog(this, contentView);
//        selectDialog.show();



        View contentView = LayoutInflater.from(this).inflate(
                R.layout.view_select_picture, null);
        TextView takePhotoBtn = (TextView) contentView
                .findViewById(R.id.tv_take_photo);

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    takePhoto();
                if (takepd != null) {
                    takepd.dismiss();
                }
            }
        });
        TextView pickPhotoBtn = (TextView) contentView
                .findViewById(R.id.tv_pick_photo);
        pickPhotoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    pickPhoto();
                if (takepd != null) {
                    takepd.dismiss();
                }
            }
        });
        TextView canceltv = (TextView) contentView.findViewById(R.id.tv_cancel);
        canceltv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (takepd != null) {
                    takepd.dismiss();
                }
            }
        });
        takepd = Util.showDialog(this, contentView);
        takepd.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
