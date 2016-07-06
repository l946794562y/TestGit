package com.example.administrator.photoviewdemo;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Book {
    /**
     * name : 好漫画
     * type : 少年漫画
     * area : 国漫
     * des :
     * finish : false
     * lastUpdate : 20150406
     * coverImg : http://imgs.juheapi.com/comic_xin/5559b86938f275fd560ad617.jpg
     */

    private String name;//这是漫画的名称
    private String type;//这是类型
    private String area;
    private String des;
    private boolean finish;
    private int lastUpdate;
    private String coverImg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Book(String name, String type, String area, String des, boolean finish, int lastUpdate, String coverImg) {
        this.name = name;
        this.type = type;
        this.area = area;
        this.des = des;
        this.finish = finish;
        this.lastUpdate = lastUpdate;
        this.coverImg = coverImg;
    }

    public Book() {
    }
}
