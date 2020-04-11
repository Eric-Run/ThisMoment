package com.dgut.moment;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MainApplication extends Application {

    private static final String TAG="MainApplication";
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MainApplication:create success!");
        mContext = this;
    }

    private int num;

    public int getNum(){
        return num;
    };

    public void setNum(int num){
        this.num=num;
    }

    public static Context getContext(){
        return mContext;
    }

}
