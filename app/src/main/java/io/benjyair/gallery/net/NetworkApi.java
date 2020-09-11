package io.benjyair.gallery.net;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkApi {

    private static final String BASE_URL = "https://image.so.com/";

    private static volatile NetworkApi instance;

    public static NetworkApi getInstance() {
        if (instance == null) {
            synchronized (NetworkApi.class) {
                if (instance == null) {
                    instance = new NetworkApi();
                }
            }
        }
        return instance;
    }

    private Retrofit retrofit;

    private NetworkApi() {
        init();
    }

    private void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Observable<Gallery> getGallery(String key, int index, int size) {
        return retrofit.create(GalleryService.class).getGallery(key, index, size).subscribeOn(Schedulers.io());
    }

}
