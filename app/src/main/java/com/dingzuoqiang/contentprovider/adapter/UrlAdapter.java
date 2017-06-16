package com.dingzuoqiang.contentprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dingzuoqiang.contentprovider.R;
import com.dingzuoqiang.contentprovider.bean.UrlBean;

import java.util.List;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class UrlAdapter extends BaseAdapter {
    private Context mContext;
    private List<UrlBean> mList;

    public UrlAdapter(Context c, List<UrlBean> l) {
        // TODO Auto-generated constructor stub
        mContext = c;
        mList = l;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup arg2) {
        ViewHoldel holdel;
        if (view == null) {
            holdel = new ViewHoldel();
            view = LayoutInflater.from(mContext).inflate(R.layout.url_item,
                    null);
            holdel.tvContent = (TextView) view.findViewById(R.id.tv_content);
            holdel.imageView = (ImageView) view.findViewById(R.id.imv_right);
            view.setTag(holdel);
        } else {
            holdel = (ViewHoldel) view.getTag();
        }
        holdel.tvContent.setText(mList.get(i).url);
        if (mList.get(i).selected == 1) {
            holdel.imageView.setVisibility(View.VISIBLE);
        } else {
            holdel.imageView.setVisibility(View.GONE);
        }

        return view;
    }

    class ViewHoldel {
        TextView tvContent;
        ImageView imageView;
    }

}
