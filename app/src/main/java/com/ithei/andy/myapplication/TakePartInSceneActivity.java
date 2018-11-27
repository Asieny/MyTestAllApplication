package com.ithei.andy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nanchen.compresshelper.CompressHelper;

import net.bither.util.CompressTools;
import net.bither.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/3/9 9:06
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class TakePartInSceneActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<Bitmap> mBitmapList = new ArrayList<Bitmap>();//给recyclerview添加的bitmap集合
    private GridLayoutManager mLayoutManager;
    private MyAdapter         mMyAdapter;
    private static final int IMAGE = 1;//调用系统相册-选择图片
    private Button mButton;
    private String mImgPath = "";//图片本地地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_img);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_take_part_in);
        mButton = (Button) findViewById(R.id.bt_submit);
    }

    private void initData() {
        //添加初始展示的图片
        Resources res = getResources();
        Bitmap mBm = BitmapFactory.decodeResource(res, R.drawable.add_picture);
        mBitmapList.add(mBm);
        mLayoutManager = new GridLayoutManager(TakePartInSceneActivity.this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mMyAdapter = new MyAdapter((ArrayList<Bitmap>) mBitmapList);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mMyAdapter);

    }

    //RecyclerView的适配器
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private ArrayList<Bitmap> mData;

        public MyAdapter(ArrayList<Bitmap> data) {
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 实例化展示的view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recy_item, parent, false);
            // 实例化viewholder
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (position == 0) {
                //第一个条目不显示删除按键
                holder.img_delet.setVisibility(View.GONE);
                holder.img_add_photo.setImageBitmap(mBitmapList.get(position));
                // 设置点击事件添加图片
                holder.img_add_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //调用相册
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, IMAGE);
                    }
                });
            } else {
                holder.img_add_photo.setImageBitmap(mBitmapList.get(position));
            }

            holder.img_delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBitmapList.remove(position);
                    mImgPath = "";
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            //展示条目数
            return mData == null ? 0 : mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_add_photo;
            ImageView img_delet;

            public ViewHolder(View itemView) {
                super(itemView);
                img_add_photo = (ImageView) itemView.findViewById(R.id.img_add_photo);
                img_delet = (ImageView) itemView.findViewById(R.id.img_delet);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相册返回，获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getBaseContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
//        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        File file = new File(imaePath);

        //压缩bitmap
//        yashoBit(file);

        //另一种压缩方法
//        File newFile = CompressHelper.getDefault(this).compressToFile(file);
        File newFile = new CompressHelper.Builder(this)
                .setMaxWidth(720)  // 默认最大宽度为720
                .setMaxHeight(960) // 默认最大高度为960
                .setQuality(100)    // 默认压缩质量为80
                .setFileName("xintu") // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(file);
        Bitmap bm = BitmapFactory.decodeFile(newFile.getPath());

        //添加到bitmap集合中
        if (mBitmapList.size() < 2) {
            mBitmapList.add(bm);
        } else {
            mBitmapList.set(1, bm);
        }
        mImgPath = imaePath;
        mMyAdapter.notifyDataSetChanged();
    }

    private void yashoBit(File file) {
        CompressTools.newBuilder(this).setMaxWidth(1080) // 默认最大宽度为720
                .setMaxHeight(1920) // 默认最大高度为960
                .setQuality(50) // 默认压缩质量为60,60足够清晰
//                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setFileName("test2").setDestinationDirectoryPath(FileUtil.getPhotoFileDir().getAbsolutePath()).build()
                .compressToBitmap(file, new CompressTools.OnCompressBitmapListener()
                {
                    @Override
                    public void onStart()
                    {

                    }

                    @Override
                    public void onSuccess(Bitmap bitmap)
                    {
                        //添加到bitmap集合中
                        if (mBitmapList.size() < 2) {
                            mBitmapList.add(bitmap);
                        } else {
                            mBitmapList.set(1, bitmap);
                        }
//                        mImgPath = imaePath;
                        mMyAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFail(String error)
                    {

                    }
                });
    }
}

