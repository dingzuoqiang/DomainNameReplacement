package com.dingzuoqiang.contentprovider.bean;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class UrlBean {
    public Integer id;
    public String url;
    public int selected;

    public UrlBean() {
    }

    public UrlBean(Integer id, String url, int selected) {
        this.id = id;
        this.url = url;
        this.selected = selected;
    }
}
