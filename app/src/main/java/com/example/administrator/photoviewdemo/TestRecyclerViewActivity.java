package com.example.administrator.photoviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class TestRecyclerViewActivity extends Activity implements View.OnClickListener{
    private Button btn_One;
    private RecyclerView recyclerView;
    private TestRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testrecyclerview);
        btn_One = (Button) findViewById(R.id.btn_One);
        btn_One.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_One){
            RequestParams params = new RequestParams("http://japi.juhe.cn/comic/book?name=&type=&skip=&finish=&key=844d38750bdf9cdfd765e8c29a62e77c");
//        params.setSslSocketFactory(...); // 设置ssl
//        params.addQueryStringParameter("wd", "xUtils");
//        params.addQueryStringParameter("team_id","1");
//        params.addQueryStringParameter("key","f3dce6962a99f2e62f02210a04bcba76");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                    int a = result.indexOf("[");
                    int b = result.indexOf("]");
                    String s = result.substring(a, b + 1);
                    Gson g = new Gson();
                    Type classOfT = new TypeToken<List<Book>>() {
                    }.getType();
                    List<Book> list = g.fromJson(s, classOfT);
                    if (adapter == null) {
                        adapter = new TestRecyclerViewAdapter(list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFinished() {

                }
            });
        }

    }
}
