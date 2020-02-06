package client;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {

    private static Retrofit rf;
    public static API api;
    public RetrofitWrapper(String ipAddress){
        rf = new Retrofit.Builder()
                .baseUrl(ipAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        api = rf.create(API.class);
    }


}
