package com.dingzuoqiang.contentprovider.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingzuoqiang.contentprovider.R;
import com.dingzuoqiang.contentprovider.adapter.UrlAdapter;
import com.dingzuoqiang.contentprovider.bean.UrlBean;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";
    String uriString = "content://com.dingzuoqiang.contentprovider.myprovider/";
    private String table = "";
    private List<UrlBean> mList = new ArrayList<UrlBean>();
    UrlAdapter urlAdapter = null;
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
        urlAdapter = new UrlAdapter(this, mList);
        listView.setAdapter(urlAdapter);
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
        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView tvCity = new TextView(this);
        tvCity.setText("  城市名称");
        TextView tvLat = new TextView(this);
        tvLat.setText("  纬度（lat）");
        TextView tvLng = new TextView(this);
        tvLng.setText("  经度（lng）");
        final EditText etCity = new EditText(this);
        etCity.setMinLines(2);
        etCity.setPadding(10, 10, 10, 10);
        etCity.setHint("请输入城市名称");

        final EditText etlat = new EditText(this);
        etlat.setMinLines(2);
        etlat.setPadding(10, 10, 10, 10);
        etlat.setHint("请输入纬度");
        etlat.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etlat.setKeyListener(DigitsKeyListener.getInstance("1234567890-."));
        final EditText etlng = new EditText(this);
        etlng.setMinLines(2);
        etlng.setPadding(10, 10, 10, 10);
        etlng.setHint("请输入经度");
        etlng.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etlng.setKeyListener(DigitsKeyListener.getInstance("1234567890-."));
        linearLayout.addView(tvCity);
        linearLayout.addView(etCity);
        linearLayout.addView(tvLat);
        linearLayout.addView(etlat);
        linearLayout.addView(tvLng);
        linearLayout.addView(etlng);

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

                    }
                }).setView(linearLayout).create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(LocationActivity.this, "城市名称 不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String lat = etlat.getText().toString();
                if (TextUtils.isEmpty(lat)) {
                    Toast.makeText(LocationActivity.this, "纬度（lat）不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                double latD = 0;
                try {
                    latD = Double.parseDouble(lat);
                } catch (NumberFormatException e) {
                    Toast.makeText(LocationActivity.this, "请输入正确格式 的纬度", Toast.LENGTH_SHORT).show();
                    return;
                }
                String lng = etlng.getText().toString();
                if (TextUtils.isEmpty(lng)) {
                    Toast.makeText(LocationActivity.this, "经度（lng）不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                double lngD = 0;
                try {
                    lngD = Double.parseDouble(lng);
                } catch (NumberFormatException e) {
                    Toast.makeText(LocationActivity.this, "请输入正确格式 的经度", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertUrl(city, latD, lngD);
                alertDialog.dismiss();
                getUrlList();
            }
        });
    }

    private void showClickPop() {
        final String[] arrayFruit = new String[]{"选择", "删除", "取消所有选择"};

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
                                urlAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                int id = mList.get(position).id;
                                deleteById(id);
                                mList.remove(position);
                                urlAdapter.notifyDataSetChanged();
                                break;

                            case 2:
                                for (int i = 0; i < mList.size(); i++) {
                                    UrlBean urlBean = mList.get(i);
                                    urlBean.selected = 0;
                                    update(urlBean);
                                }
                                urlAdapter.notifyDataSetChanged();
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

    //往内容提供者添加数据  纬度（lat）
    public void insertUrl(String city, double latitude, double longitude) {
        try {
            ContentResolver contentResolver = this.getContentResolver();
            Uri insertUri = Uri.parse(uriString);
            ContentValues values = new ContentValues();
            values.put("city", city);
            values.put("latitude", latitude);
            values.put("longitude", longitude);
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
                String url = cursor.getString(cursor.getColumnIndex("city")) + "->lat=" + cursor.getDouble(cursor.getColumnIndex("latitude")) + "->lng=" + cursor.getDouble(cursor.getColumnIndex("longitude"));
                int selected = cursor.getInt(cursor.getColumnIndex("selected"));
                Log.i(TAG, "id=" + id + ",url=" + url + ",selected=" + selected);
                UrlBean urlBean = new UrlBean(id, url, selected);
                mList.add(urlBean);
            }
            urlAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
