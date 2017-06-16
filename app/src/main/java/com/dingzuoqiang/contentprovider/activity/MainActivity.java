package com.dingzuoqiang.contentprovider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dingzuoqiang.contentprovider.R;
import com.dingzuoqiang.contentprovider.db.Constant;

/**
 * Created by dingzuoqiang on 2017/6/16.
 * Email: 530858106@qq.com
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.lay_wmc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, UrlListActivity.class);
                intent.putExtra("table", Constant.TAB_WMC);
                startActivity(intent);
            }
        });
        findViewById(R.id.lay_huzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, UrlListActivity.class);
                intent.putExtra("table", Constant.TAB_HUZHU);
                startActivity(intent);
            }
        });
        findViewById(R.id.lay_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, UrlListActivity.class);
                intent.putExtra("table", Constant.TAB_STUDENT);
                startActivity(intent);
            }
        });
        findViewById(R.id.lay_coach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UrlListActivity.class);
                intent.putExtra("table", Constant.TAB_COACH);
                startActivity(intent);
            }
        });

    }
}
