package com.demol.map.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.demol.map.utils.language.LocalManageUtil;


import java.util.*;


/**
 * Created by hongming.wang on 2018/1/23.
 */

public class MyApplication extends Application {
    public static final String APP_NAME = "XXX";
    public static boolean isDebug=true;
    private int count = 0;
    private List<Activity> activities;
    private List<Fragment> fragments;
    private static Context mContext;
    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<String> forAddFriend = new ArrayList<>();

    public static int IsEnglish;
    public static Context getContext(){
        return mContext;

    }
    private static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        fragments=new ArrayList<>();
        app = this;
        mContext=getApplicationContext();
        activities=new ArrayList<>();
        fragments=new ArrayList<>();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
    private static Map<String,Activity> destoryMap = new HashMap<>();
    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName,activity);
    }
    /**
     *销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet=destoryMap.keySet();
        for (String key:keySet){
            destoryMap.get(key).finish();
        }
    }
    public static MyApplication getApp(){
        return app;
    }



    public void addActivity(Activity activity){
        if (!activities.contains(activity)){
            activities.add(activity);
        }
    }
    public void addFragment(Fragment fragment){
        if (!fragments.contains(fragment)){
            fragments.add(fragment);
        }
    }

    public List<Fragment> getFragments() {
        return fragments;
    }
    public void removeFragment(Fragment fragment){
        if (fragments.contains(fragment)){
            fragments.remove(fragment);
        }
    }
    public void removeAllFragment(){
        fragments.clear();
    }

    public void removeActivity(Activity activity){
        if (activities.contains(activity)){
            activities.remove(activity);
            activity.finish();
        }
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void removeAllActivity(){
        for (Activity activity:activities){
            activity.finish();
        }
    }
    /**
     * 判断app是否在后台
     * @return
     */
    public boolean isBackground(){
        if(count <= 0){
            return true;
        } else {
            return false;
        }
    }
    /*0中文1 英文*/
    public int IsEnglish(){
        String place = LocalManageUtil.getSetLanguageLocale(this).toString();
        String lang = LocalManageUtil.getSelectLanguage(this);
        if ("自动".equals(lang)){
        if ( "en_US".equals(place)){
            IsEnglish =1;
        }else {
            IsEnglish =0;
        }
        }else if ( "ENGLISH".equals(lang)){
            IsEnglish =1;
        }else {
            IsEnglish =0;
        }
        return IsEnglish;
    }
    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LocalManageUtil.setLocal(base));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());

    }


}
