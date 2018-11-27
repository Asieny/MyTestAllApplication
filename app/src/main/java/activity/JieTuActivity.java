package activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.ithei.andy.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import videoView.otherDemo.ToastUtils;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/7 15:40
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class JieTuActivity extends Activity implements View.OnClickListener {
    //用于展示选择的图片
    private ImageView     mImageView;
    private CropImageView cropImageView;
    private int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;
    private TextView tv_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jietu);
        //初始化百度文字识别
        initAccessToken();
        initView();
        initData();
    }

    private void initAccessToken() {
        OCR.getInstance(JieTuActivity.this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
//                result.getAccessToken();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("获取百度识别Token成功" + "lalala");
                    }
                });
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("获取百度识别Token失败");
                    }
                });
            }
        }, getApplicationContext(), "Npw59a3srgjmW16xA21HGaHw", "bZF3eZCdnI4nn0jB2um75fijFmpjGviW");
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.show_image);
        tv_cont = (TextView) findViewById(R.id.tv_cont);
        Button chooseGallery = (Button) findViewById(R.id.choose_gallery);
        Button bt_crop = (Button) findViewById(R.id.bt_crop);
        Button bt_dist = (Button) findViewById(R.id.bt_dist);
        chooseGallery.setOnClickListener(this);
        bt_crop.setOnClickListener(this);
        bt_dist.setOnClickListener(this);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
    }

    private void initData() {

    }

    private static final int IMAGE = 1;//调用系统相册-选择图片n小于0

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            //            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            //            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            //            c.moveToFirst();
            //            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            //            String imagePath = c.getString(columnIndex);
            showImage(selectedImage);
            //            c.close();
        }
    }

    //加载图片
    private void showImage(Uri imgPath) {
        ToastUtils.showToast("获取到图片地址：" + imgPath);
        cropImageView.load(imgPath).execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        cropImageView.crop(imgPath).execute(new CropCallback() {
            @Override
            public void onSuccess(Bitmap cropped) {
                Uri saveUri = Uri.parse(Environment.getDataDirectory() + "123.jpg");
                cropImageView.save(cropped).execute(saveUri, new SaveCallback() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ToastUtils.showToast("图片地址：" + uri);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    private Bitmap croppedBitmap;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_gallery:
                //从相册选取
                //第二个参数是需要申请的权限
                if (ContextCompat.checkSelfPermission(JieTuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(JieTuActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    //调用相册
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE);
                }
                break;
            case R.id.bt_crop:
                croppedBitmap = cropImageView.getCroppedBitmap();
                mImageView.setImageBitmap(croppedBitmap);
                break;
            case R.id.bt_dist:
                //通用文字识别
                saveBitmapFile(croppedBitmap);
                break;
            default:
                break;
        }
    }

    public void saveBitmapFile(Bitmap bitmap) {
        String mFilePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
        mFilePath = mFilePath + "/temp123.jpg";// 指定路径
        File file = new File(mFilePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            DistKinds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void DistKinds() {
        String mFilePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
        mFilePath = mFilePath + "/temp123.jpg";// 指定路径
        // 通用文字识别参数设置
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(mFilePath));
        final StringBuffer sb = new StringBuffer();
        // 调用通用文字识别服务。通用版
        normalDist(param, sb);

        // 调用通用文字识别服务。高精度版
        //        highDist(param, sb);

    }

    private void normalDist(GeneralBasicParams param, final StringBuffer sb) {
        OCR.getInstance(this).recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                // 调用成功，返回GeneralResult对象
                for (WordSimple wordSimple : result.getWordList()) {
                    // wordSimple不包含位置信息
                    WordSimple word = wordSimple;
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                ToastUtils.showToast(String.valueOf(sb));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_cont.setText(String.valueOf(sb));
                    }
                });
                // json格式返回字符串
                String jsonRes = result.getJsonRes();
                System.out.println(jsonRes);
                // listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
                ToastUtils.showToast("调用失败");
            }
        });

    }

    private void highDist(GeneralBasicParams param, final StringBuffer sb) {
        OCR.getInstance(this).recognizeAccurateBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                // 调用成功，返回GeneralResult对象
                for (WordSimple wordSimple : result.getWordList()) {
                    // wordSimple不包含位置信息
                    WordSimple word = wordSimple;
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                ToastUtils.showToast(String.valueOf(sb));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_cont.setText(String.valueOf(sb));
                    }
                });
                // json格式返回字符串
                //                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }
}
