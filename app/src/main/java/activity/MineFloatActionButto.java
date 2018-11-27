package activity;

import android.os.Bundle;
import android.widget.ListView;

import com.ithei.andy.myapplication.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.TitleAdapter;
import videoView.otherDemo.BaseActivity;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/20 11:30
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class MineFloatActionButto extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_float_button);
        initView();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(android.R.id.list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToListView(listView);
        final List<String> mData = new ArrayList<>();
        mData.add("截图识别Demo");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        mData.add("floatButton");
        TitleAdapter titleAdapter = new TitleAdapter(MineFloatActionButto.this, mData);
        listView.setAdapter(titleAdapter);
    }
}
