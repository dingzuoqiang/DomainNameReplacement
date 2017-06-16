package com.dingzuoqiang.contentprovider.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dingzuoqiang.contentprovider.R;
import com.dingzuoqiang.contentprovider.adapter.UrlAdapter;
import com.dingzuoqiang.contentprovider.bean.UrlBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class UrlListActivity extends AppCompatActivity {
    private static final String TAG = "UrlListActivity";
    String uriString = "content://com.dingzuoqiang.contentprovider.myprovider/";
    private String table = "";
    private List<UrlBean> mList = new ArrayList<UrlBean>();
    private ListView listView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if (intent != null) {
            table = intent.getStringExtra("table");
            if (TextUtils.isEmpty(table))
                return;
            setTitle(table);
            uriString = uriString + table;
        }
        initUI();
        getUrlList();

    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.list_url);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showAddPop();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                showClickPop();
            }
        });
    }

    private void showAddPop() {
        final EditText editText = new EditText(this);
        editText.setMinLines(5);
        editText.setPadding(30, 30, 30, 30);
        editText.setHint("请输入完整的域名如下：\n http://www.baidu.com/");
        final AlertDialog alertDialog = new AlertDialog.Builder(this).
                setTitle("添加")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = editText.getText().toString();
                        insertUrl(url);
                    }
                }).setView(editText).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(UrlListActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertUrl(url);
                alertDialog.dismiss();
                getUrlList();
            }
        });
    }

    private void showClickPop() {
        final String[] arrayFruit = new String[]{"选择", "删除", "复制内容"};

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("编辑")
                .setItems(arrayFruit, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                for (int i = 0; i < mList.size(); i++) {
                                    UrlBean urlBean = mList.get(i);
                                    urlBean.selected = (position == i ? 1 : 0);
                                    update(urlBean);
                                }
                                UrlAdapter urlAdapter = new UrlAdapter(UrlListActivity.this, mList);
                                listView.setAdapter(urlAdapter);
                                break;
                            case 1:
                                int id = mList.get(position).id;
                                deleteById(id);
                                getUrlList();
                                break;

                            case 2:
                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(mList.get(position).url);
                                Toast.makeText(UrlListActivity.this, "复制成功", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).
                        create();
        alertDialog.show();
    }

    //往内容提供者添加数据
    public void insertUrl(String url) {
        try {
            ContentResolver contentResolver = this.getContentResolver();
            Uri insertUri = Uri.parse(uriString);
            ContentValues values = new ContentValues();
            values.put("url", url);
            values.put("selected", 0);
            Uri uri = contentResolver.insert(insertUri, values);
            Log.i(TAG, uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新内容提供者中的数据
    public void update(UrlBean urlBean) {
        try {
            ContentResolver contentResolver = this.getContentResolver();
            Uri updateUri = Uri.parse(uriString + "/" + urlBean.id);
            ContentValues values = new ContentValues();
            values.put("url", urlBean.url);
            values.put("selected", urlBean.selected);
            contentResolver.update(updateUri, values, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从内容提供者中删除数据
    public void deleteById(Integer id) {
        try {
            ContentResolver contentResolver = this.getContentResolver();
            Uri deleteUri = Uri.parse(uriString + "/" + id);
            contentResolver.delete(deleteUri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取内容提供者中的数据
    public void getUrlList() {
        try {
            ContentResolver contentResolver = this.getContentResolver();
            Uri selectUri = Uri.parse(uriString);
            Cursor cursor = contentResolver.query(selectUri, null, null, null, null);
            if (cursor == null)
                return;
            mList.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                int selected = cursor.getInt(cursor.getColumnIndex("selected"));
                Log.i(TAG, "id=" + id + ",url=" + url + ",selected=" + selected);
                UrlBean urlBean = new UrlBean(id, url, selected);
                mList.add(urlBean);
            }
            UrlAdapter urlAdapter = new UrlAdapter(this, mList);
            listView.setAdapter(urlAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
