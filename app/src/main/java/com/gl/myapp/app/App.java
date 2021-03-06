package com.gl.myapp.app;

import android.app.Application;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tsy.sdk.myokhttp.MyOkHttp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zft on 2018/8/31.
 */

public class App extends Application {


    public static final String BASE_URL = "http://192.168.10.20:8080/myclient/";

    private static App instance;
    private MyOkHttp myOkHttp;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        System.out.println("Create Application!");

        DensityUtils.setDensity(this);

        //持久化存储cookie
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //自定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                .build();
        myOkHttp = new MyOkHttp(okHttpClient);
    }

    public static synchronized App getInstance() {
        return instance;
    }

    public MyOkHttp getMyOkHttp() {
        return myOkHttp;
    }
}
