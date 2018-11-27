package somejiek;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * @创建者 AndyYan
 * @创建时间 2018/2/12 13:39
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class AndroidtoJs extends Object {
    Context mContext;
    String  message;

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {   //msg="JS调用了Android的hello方法"
        System.out.println("JS调用了Android的hello方法");
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    public void setSomeData(Context context, String imgMessage) {
        this.mContext = context;
        this.message=imgMessage;
    }
}
