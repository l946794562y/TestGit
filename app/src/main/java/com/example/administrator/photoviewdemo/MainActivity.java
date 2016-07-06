package com.example.administrator.photoviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends Activity implements View.OnClickListener{
    // 短信注册，随机产生头像
    private static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };
    class MyImageCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> lruCache;

        public MyImageCache() {
            int max = (int) (Runtime.getRuntime().maxMemory()/8);
            lruCache = new LruCache<String,Bitmap>(max){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()*value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {

        }
    }
    class BookAdapter extends BaseAdapter{
        private List<Book> data;
        private LayoutInflater inflater;
        private MyImageCache imageCache;



        public void setData(List<Book> data) {
            this.data = data;
        }

        public BookAdapter(List<Book> data) {
            this.data = data;
            inflater = getLayoutInflater();
            imageCache = new MyImageCache();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = data.get(position);
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.lisview_book_item, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv1);
                viewHolder.type = (TextView) convertView.findViewById(R.id.tv2);
                viewHolder.area = (TextView) convertView.findViewById(R.id.tv3);
                viewHolder.des = (TextView) convertView.findViewById(R.id.tv4);
                viewHolder.finish = (TextView) convertView.findViewById(R.id.tv5);
                viewHolder.lastUpdate = (TextView) convertView.findViewById(R.id.tv6);
                viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//                TextView name = (TextView) convertView.findViewById(R.id.tv1);
//                TextView type = (TextView) convertView.findViewById(R.id.tv2);
//                TextView area = (TextView) convertView.findViewById(R.id.tv3);
//                TextView des = (TextView) convertView.findViewById(R.id.tv4);
//                TextView finish = (TextView) convertView.findViewById(R.id.tv5);
//                TextView lastUpdate = (TextView) convertView.findViewById(R.id.tv6);
//                NetworkImageView niv = (NetworkImageView) convertView.findViewById(R.id.iv);
//                ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
                viewHolder.name.setText(book.getName());
                viewHolder.type.setText(book.getType());
                viewHolder.area.setText(book.getArea());
                viewHolder.des.setText(book.getDes());
                if(book.isFinish()){
                    viewHolder.finish.setText("是");
                }else {
                    viewHolder.finish.setText("否");
                }
                viewHolder.lastUpdate.setText(String.valueOf(book.getLastUpdate()));
                String imgUrl = book.getCoverImg();
//                ImageLoader imageLoader = new ImageLoader(rq,imageCache);
//                niv.setImageUrl(imgUrl,imageLoader);
            // 设置加载图片的参数  
            ImageOptions options = new ImageOptions.Builder()
                    //  是否忽略GIF格式的图片     
                    .setIgnoreGif(false)
                            // 图片缩放模式
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            // 下载中显示的图片 
                    .setLoadingDrawableId(R.mipmap.ic_launcher)
                            // 下载失败显示的图片 
                    .setFailureDrawableId(R.mipmap.ic_launcher)
                            // 得到ImageOptions对象    
                    .build();
            // 加载图片 
            x.image().bind(viewHolder.iv, imgUrl, options, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable drawable) {

                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
                return convertView;


        }
        class ViewHolder{
            TextView name;
            TextView type;
            TextView area;
            TextView des;
            TextView finish;
            TextView lastUpdate;
            ImageView iv;
        }
    }
    private Button btn ;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private ListView lv;
    private TextView tv;
    private RequestQueue rq;
    private BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.tv);
        rq = Volley.newRequestQueue(this);
        SMSSDK.initSDK(this, "12a0316db2467", "f3078542b7d7acd922602b1bdcfc9586",true);
        ShareSDK.initSDK(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn) {
            //这是用volley做的
/*        StringRequest strReq = new StringRequest(Request.Method.GET," http://japi.juhe.cn/comic/book?name=&type=&skip=&finish=&key=844d38750bdf9cdfd765e8c29a62e77c", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int a = response.indexOf("[");
                int b = response.indexOf("]");
                String s = response.substring(a,b+1);
                Gson g = new Gson();
                Type classOfT = new TypeToken<List<Book>>(){}.getType();
                List<Book> data = g.fromJson(s, classOfT);
                if(adapter==null){
                    adapter = new BookAdapter(data);
                    lv.setAdapter(adapter);
                }else {
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(strReq);*/
            //这是用xutils做的

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
                        adapter = new BookAdapter(list);
                        lv.setAdapter(adapter);
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
        }else if(id == R.id.btn2){
            Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
            startActivity(intent);
        }else if(id == R.id.btn3){
            Intent intentTwo = new Intent(MainActivity.this,TestRecyclerViewActivity.class);
            startActivity(intentTwo);

        }else if (id == R.id.btn4){
            Intent intentThree = new Intent(MainActivity.this,RecyclerViewRefresh.class);
            startActivity(intentThree);
        }else if (id == R.id.btn5){
            // 打开注册页面
            RegisterPage registerPage = new RegisterPage();
            registerPage.setRegisterCallback(new EventHandler() {
                public void afterEvent(int event, int result, Object data) {
                    // 解析注册结果
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        @SuppressWarnings("unchecked")
                        HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");
                        // 提交用户信息
                        registerUser(country, phone);
                    }
                }
            });
            registerPage.show(this);
        }else if (id == R.id.btn6){

                    ShareSDK.initSDK(this);
                    OnekeyShare oks = new OnekeyShare();
                    //关闭sso授权
                    oks.disableSSOWhenAuthorize();

                    // title标题：微信、QQ（新浪微博不需要标题）
                    oks.setTitle("我是分享标题");  //最多30个字符

                    // text是分享文本：所有平台都需要这个字段
                    oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符

                    // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
                    //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

                    //网络图片的url：所有平台
                    oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

                    // url：仅在微信（包括好友和朋友圈）中使用
                    oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

                    // Url：仅在QQ空间使用
                    oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

                    // 启动分享GUI
                    oks.show(this);


        }else if (id == R.id.btn7){
            Intent intentFour = new Intent(MainActivity.this, WebViewActivity.class);
            startActivity(intentFour);
        }
    }
    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
