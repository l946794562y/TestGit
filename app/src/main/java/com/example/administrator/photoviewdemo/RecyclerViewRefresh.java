package com.example.administrator.photoviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class RecyclerViewRefresh extends Activity {
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private TestRecyclerViewAdapter adapter;
    private    List<Book> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerviewrefresh);
        pullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        pullLoadMoreRecyclerView.setLinearLayout();
        pullLoadMoreRecyclerView.setFooterViewText("loading");//设置上拉刷新文字
        loadData();

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                setRefresh();
                adapter.getData().addAll(loadData());
                adapter.notifyDataSetChanged();
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

            }

            @Override
            public void onLoadMore() {
                Log.i("LoadMore","ddddddddddddddddddddddd");
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

            }
        });


        };
    private void setRefresh(){
        adapter.getData().clear();
    }
    private List<Book> loadData(){

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
                 list = g.fromJson(s, classOfT);
                if (adapter == null) {
                    adapter = new TestRecyclerViewAdapter(list);
                    pullLoadMoreRecyclerView.setAdapter(adapter);
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
        return list;
    }
}
