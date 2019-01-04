package activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ithei.andy.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import messageInfo.ForTXAIFaceInfo;
import messageInfo.ForTXFace;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import utils.HttpOkhUtils;
import utils.ImageCompress;
import utils.MD5Util;
import utils.ProgressDialogUtil;
import utils.RequestParamsFM;
import videoView.otherDemo.BaseActivity;
import videoView.otherDemo.ToastUtils;

/**
 * @创建者 AndyYan
 * @创建时间 2019/1/2 9:06
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MineTXFaceKnowlegeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_01;
    private ImageView img_02;
    private Button    bt01;
    private Button    bt02;
    private Button    bt_submit;
    private Bitmap    mBitmap01;
    private Bitmap    mBitmap02;
    private String    mImgFile01;
    private String    mImgFile02;
    private int       which;
    private String TAG = "haha:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tx_face_test);
        setView();
        setData();
    }

    private void setView() {
        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }

    private void setData() {
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt01:
                setImg01();
                break;
            case R.id.bt02:
                setImg02();
                break;
            case R.id.bt_submit:
                sendImg();
                break;
        }
    }

    private static final int IMAGE                              = 1;//调用系统相册-选择图片
    private              int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 1001;

    private void setImg01() {
        which = 0;
        getLocalImg();
    }

    private void setImg02() {
        which = 2;
        getLocalImg();
    }

    public static final String SOURCES =
            "abcdefghijklmnopqrstuvwxyz1234567890";//ABCDEFGHIJKLMNOPQRSTUVWXYZ

    private void sendImg() {
        ProgressDialogUtil.startShow(this, "正在比对...");//"&sign=" + "" +

        int secondTimestamp = getSecondTimestamp(new Date(System.currentTimeMillis()));
        String ranNum = generateString(new Random(), SOURCES, 20);
        //        String strBMap1 = Bitmap2StrByBase64(mBitmap01);//"data:image/jpeg;base64," +
        //        String strBMap2 = Bitmap2StrByBase64(mBitmap02);//"data:image/jpeg;base64," +

        String strBMap1 = getImgStr(mImgFile01);
        String strBMap2 = getImgStr(mImgFile02);

        String mT = "app_id=" + URLEncoder.encode("" + 2110988103) + "&image_a=" + URLEncoder.encode(strBMap1) + "&image_b=" + URLEncoder.encode(strBMap2) + "&nonce_str=" + URLEncoder.encode(ranNum) + "&time_stamp=" + URLEncoder.encode("" + secondTimestamp);
        String mS = mT + "&app_key=" + "Ms9E45XqiF8TqrVi";
        String MD5 = MD5Util.MD5Encode(mS, "utf-8", false).toUpperCase();

        RequestParamsFM params = new RequestParamsFM();
        params.put("app_id", 2110988103);
        params.put("time_stamp", secondTimestamp);
        params.put("nonce_str", ranNum);
        params.put("sign", MD5);
        params.put("image_a", strBMap1);
        params.put("image_b", strBMap2);
        HttpOkhUtils.getInstance().doPostBean("https://api.ai.qq.com/fcgi-bin/face/face_facecompare", params, new HttpOkhUtils.HttpCallBack() {
            @Override
            public void onError(Request request, IOException e) {
                ProgressDialogUtil.hideDialog();
                ToastUtils.showToast(MineTXFaceKnowlegeActivity.this, "网络错误");
            }

            @Override
            public void onSuccess(int code, String resbody) {
                ProgressDialogUtil.hideDialog();
                Gson gson = new Gson();
                String resbodysd=resbody;
                System.out.println(resbody);
                ForTXAIFaceInfo forTXAIFaceInfo = gson.fromJson(resbody, ForTXAIFaceInfo.class);
                ToastUtils.showToast(MineTXFaceKnowlegeActivity.this,forTXAIFaceInfo.getMsg());
                if (0==forTXAIFaceInfo.getRet()){
                    ToastUtils.showToast(MineTXFaceKnowlegeActivity.this,"比对成功，相似度为："+forTXAIFaceInfo.getData().getSimilarity());
                }
            }
        });


        //        int secondTimestamp = getSecondTimestamp(new Date(System.currentTimeMillis()));
        //        String orignal  = "a=[" + 1258431101 + "]&k=[" + "AKIDalkANUQLlwVs1ZvD1tY1CHMRQQJyL4jA" + "]&e=[" + (secondTimestamp + 100) + "]&t=[" + secondTimestamp + "]&r=[" + 100086 + "]";
        //
        //       byte[] SignTmp = HMAC_SHA1.genHMAC("DCiaXHIWaE7qukpiMnPr32oKkwtdy4we", orignal);
        //
        //        RequestParamsFM headerParams = new RequestParamsFM();
        //        headerParams.put("host", "recognition.image.myqcloud.com");
        //        headerParams.put("content-type", "multipart/form-data");
        //        headerParams.put("authorization","" );
        //        RequestParamsFM params = new RequestParamsFM();
        //        params.put("appid", "1258431101");
        //        params.put("imageA", getImgByteData(mImgFile01));
        //        params.put("imageB", getImgByteData(mImgFile01));
        //        HttpOkhUtils.getInstance().doPostWithHeader("https://recognition.image.myqcloud.com/face/compare", headerParams, params, new HttpOkhUtils.HttpCallBack() {
        //            @Override
        //            public void onError(Request request, IOException e) {
        //                ProgressDialogUtil.hideDialog();
        //                ToastUtils.showToast(MineTXFaceKnowlegeActivity.this, "网络错误");
        //            }
        //
        //            @Override
        //            public void onSuccess(int code, String resbody) {
        //                ProgressDialogUtil.hideDialog();
        //                ToastUtils.showToast(MineTXFaceKnowlegeActivity.this, "网络" + code + "返回值:" + resbody);
        //            }
        //        });


    }

    private void requestIntnet(String baseUrl, int app_id, String strBMap1, String strBMap2, String ranNum, int secondTimestamp, String md5) {
        //        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj);
        Retrofit retrofit = new Retrofit.Builder()
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        Call<ForTXFace> news = retrofit.create(APi.class).getNews(app_id, strBMap1, strBMap2, ranNum, secondTimestamp, md5);
        news.enqueue(new Callback<ForTXFace>() {
            @Override
            public void onResponse(Call<ForTXFace> call, Response<ForTXFace> response) {
                ToastUtils.showToast("" + response.body().getMsg());
            }

            @Override
            public void onFailure(Call<ForTXFace> call, Throwable t) {

            }
        });
    }

    public interface APi {
        @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
        @POST("")
        Call<ForTXFace> getNews(@Query("app_id") Integer userid, @Query("image_a") String image_a, @Query("image_b") String image_b, @Query("nonce_str") String nonce_str, @Query("time_stamp") Integer time_stamp, @Query("sign") String sign);
    }

    private void getLocalImg() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            //调用相册
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            if (which == 2) {
                showImage(imagePath, img_02);
            } else {
                showImage(imagePath, img_01);
            }
            c.close();
        }
    }

    //加载图片
    private void showImage(String imgPath, ImageView img) {
        //压缩图片
        //        File file = new File(imgPath);
        //        if (null != file) {
        //            File newFile = new CompressHelper.Builder(this)
        //                    .setMaxWidth(1080)  // 默认最大宽度为720
        //                    .setMaxHeight(1920) // 默认最大高度为960
        //                    .setQuality(100)    // 默认压缩质量为80
        //                    .setFileName("sendPic") // 设置你需要修改的文件名
        //                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
        //                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
        //                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
        //                    .build()
        //                    .compressToFile(file);
        //            Bitmap bm = BitmapFactory.decodeFile(newFile.getPath());

        ImageCompress compress = new ImageCompress();
        ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
        options.uri = Uri.fromFile(new File(imgPath));
        options.maxWidth = 400;
        options.maxHeight = 800;
        Bitmap bm = compress.compressFromUri(this, options);


        if (which == 2) {
            mImgFile02 = imgPath;
            mBitmap02 = bm;
        } else {
            mImgFile01 = imgPath;
            mBitmap01 = bm;
        }
        img.setImageBitmap(bm);
    }


    /*bitmap转base64*/
    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 80, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
        //return new String(Base64.encodeToString(bytes, Base64.NO_WRAP));
    }


    /**
     * * 将图片转换成Base64编码
     * * @param imgFile 待处理图片
     * * @return
     */
    public static String getImgStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理


        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(data));
    }

    //    public String bitmapToBase64(Bitmap bitmap) {
    //        String result = "";
    //        ByteArrayOutputStream bos = null;
    //        try {
    //            if (null != bitmap) {
    //                bos = new ByteArrayOutputStream();
    //                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos);//将bitmap放入字节数组流中
    //
    //                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
    //                bos.close();
    //
    //                byte[] bitmapByte = bos.toByteArray();
    //                result = Base64.encodeToString(bitmapByte, Base64.DEFAULT);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            if (null != null) {
    //                try {
    //                    bos.close();
    //                } catch (IOException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }
    //        return result;
    //    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    public byte[] getImgByteData(String imgFile) {
       /*
        *将图片转换成二进制字节流
        */
        byte[] imageByte;
        File file1 = new File(imgFile);//需要转换成二进制字节流的文件的绝对路径
        try {
            FileInputStream fls = new FileInputStream(file1);
            imageByte = new byte[(int) file1.length()];
            fls.read(imageByte);
            fls.close();
            return imageByte;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
