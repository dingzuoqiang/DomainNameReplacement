利用ContentProvider 相关知识 实现的一个域名替换工具，方便于 android 开发过程中切换 应用api请求的域名，不需要在项目里配置新的域名，再重新打包

#开发工程 根据自己实际需求 接入代码：

public class MyApplicaion extends Application { public static MyApplicaion instance;

@Override
public void onCreate() {
    // TODO Auto-generated method stub
    super.onCreate();
    if (AppEnvHelper.currentEnv() == AppEnvEnum.DEBUG) {
        changeServerHostFromProvider();
    }

}

public MyApplicaion() {
    instance = this;
}

public static Context getContext() {
    return MyApplicaion.instance;
}


@Override
public void onLowMemory() {
    super.onLowMemory();
    LogUtil.LogD(MyApplicaion.class, "================onLowMemory");
}

    private void changeServerHostFromProvider() {
        Uri uri = Uri.parse("content://com.dingzuoqiang.contentprovider.myprovider/huzhu");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String url = cursor.getString(cursor.getColumnIndex("url"));
                int selected = cursor.getInt(cursor.getColumnIndex("selected"));// 1 选中，否则未选中
                if (selected == 1 && !TextUtils.isEmpty(url)) {
                    Urls.BASE_URL_TEST = url;
                    Urls.BASE_URL_ONLINE = url;
//                    Urls.H5_URL_TEST = url;
//                    Urls.H5_URL_ONLINE = url;
                    return;
                }
            }
            cursor.close();
        }
    }
 }   
