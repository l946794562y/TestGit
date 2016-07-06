package com.example.administrator.photoviewdemo;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.BookViewHolder> {
    private List<Book> list;
    public void setData(List<Book> data) {
        this.list = data;
    }



    public List<Book> getData() {
        return list;

    }

    public TestRecyclerViewAdapter(List<Book> list) {
        this.list = list;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lisview_book_item,null);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {
//        BookViewHolder holder = (BookViewHolder) viewHolder;
        Book book = list.get(i);
        bookViewHolder.tv1.setText(book.getName());
        bookViewHolder.tv2.setText(book.getType());
        bookViewHolder.tv3.setText(book.getArea());
        bookViewHolder.tv4.setText(book.getDes());
        if(book.isFinish()){
            bookViewHolder.tv5.setText("是");
        }else {
            bookViewHolder.tv5.setText("否");
        }
        bookViewHolder.tv6.setText(String.valueOf(book.getLastUpdate()));
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
        x.image().bind(bookViewHolder.iv, imgUrl, options, new Callback.CommonCallback<Drawable>() {
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
    }

  /*  @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        BookViewHolder holder = (BookViewHolder) viewHolder;
        Book book = list.get(i);
        holder.tv1.setText(book.getName());
        holder.tv2.setText(book.getType());
        holder.tv3.setText(book.getArea());
        holder.tv4.setText(book.getDes());
        if(book.isFinish()){
            holder.tv5.setText("是");
        }else {
            holder.tv5.setText("否");
        }
        holder.tv6.setText(String.valueOf(book.getLastUpdate()));
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
        x.image().bind(holder.iv, imgUrl, options, new Callback.CommonCallback<Drawable>() {
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
        });    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }




    class BookViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView tv4;
        public TextView tv5;
        public TextView tv6;

        public BookViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            tv4 = (TextView) itemView.findViewById(R.id.tv4);
            tv5 = (TextView) itemView.findViewById(R.id.tv5);
            tv6 = (TextView) itemView.findViewById(R.id.tv6);

        }
    }
}
