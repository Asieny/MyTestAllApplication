package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ithei.andy.myapplication.R;

import java.util.List;

/**
 * @创建者 AndyYan
 * @创建时间 2018/8/7 15:28
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class TitleAdapter extends BaseAdapter {
    private Context      mContext;
    private List<String> mTiList;

    public TitleAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mTiList = data;
    }

    @Override
    public int getCount() {
        return mTiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new MyViewHolder();
            convertView = View.inflate(mContext, R.layout.list_item_title, null);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(mTiList.get(position));
        return convertView;
    }

    private class MyViewHolder {
        TextView tv_title;
    }
}
