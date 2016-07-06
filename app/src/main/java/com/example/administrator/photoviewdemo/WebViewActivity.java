package com.example.administrator.photoviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Administrator on 2016/5/16.
 */
public class WebViewActivity extends Activity{
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("file:///android_asset/aa.html");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true; //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            }
        });
    }
}
