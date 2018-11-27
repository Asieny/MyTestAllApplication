package com.ithei.andy.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Set;

import somejiek.AndroidtoJs;

/**
 * @创建者 AndyYan
 * @创建时间 2018/2/12 13:35
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 */
public class WebJSActivity extends Activity {

    private WebView mWebView;
    private WebSettings mWebSettings;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_js);
        initView();
        initData();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.web);
    }

    private void initData() {
        mWebSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        mWebSettings.setJavaScriptEnabled(true);
//        functionOne();//简便JS调用android中的方法，但存在严重漏洞问题
//        functionTwo();//不存在one的漏洞

//        functionThree();//试验
        functionFour();//试验点击图片关闭activity,获取图片链接

    }

    private void functionFour() {
        // 设置允许JS弹窗
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("https://www.baidu.com/");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.contains("https")){
                    Toast.makeText(getBaseContext(),url,Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    private void functionThree() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        // 特别要注意这行代码,意思是在js中条用android中的第一个参数的实际名字。这里第一个参数是this。
        //也就是本类的实例。imgelistener是本类的实例在js中的名字。
        // 也就是说你要在js中调用MainActivity这个类中的方法的话就必须给MainActivity这个类在js中的名字，
        //这样你才能在js中调用android中的类中的方法。
        mWebView.addJavascriptInterface(this, "imagelistner");
        mWebView.loadUrl("file:///android_asset/javascript003.html");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                mWebView.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.imagelistner.openImage(this.src);  "
                        + "    }  " + "}" + "})()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return super.shouldOverrideUrlLoading(view, url);
            }

        });
    }
    // 下面的@SuppressLint("JavascriptInterface")最好加上。防止在某些版本中js和java的交互不支持。
    @SuppressLint("JavascriptInterface")
    public void openImage(String img) {
        Toast.makeText(this, img, Toast.LENGTH_SHORT).show();
    }
    private void functionTwo() {
        // 设置允许JS弹窗
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.loadUrl("file:///android_asset/javascript002.html");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                //js://webview?arg1=111&arg2=222
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        Set<String> collection = uri.getQueryParameterNames();
                        for (String str : collection) {
                            System.out.println(str);
                        }
                    }
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void functionOne() {
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        AndroidtoJs androidtoJs = new AndroidtoJs();
        androidtoJs.setSomeData(getBaseContext(),"这是图片地址");
        mWebView.addJavascriptInterface(androidtoJs,"test");
        mWebView.loadUrl("file:///android_asset/javascript.html");
    }
}
