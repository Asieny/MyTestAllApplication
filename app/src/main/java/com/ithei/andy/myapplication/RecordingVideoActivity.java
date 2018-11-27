package com.ithei.andy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * @创建者 AndyYan
 * @创建时间 2018/3/16 16:40
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class RecordingVideoActivity extends Activity {

    private Button mBt_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_video);
        initView();
        initData();
    }

    private void initView() {
        mBt_start = (Button) findViewById(R.id.bt_start);
    }

    private void initData() {
        mBt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startRecording();
                Intent intent =new Intent(RecordingVideoActivity.this, MediaRecorderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startRecording() {
        // 激活系统的照相机进行录像
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        // 保存录像到指定的路径
        File file = new File("/sdcard/video.mp4");
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
