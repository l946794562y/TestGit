package com.example.administrator.photoviewdemo;

import android.app.Application;

import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/4/25.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }
}
