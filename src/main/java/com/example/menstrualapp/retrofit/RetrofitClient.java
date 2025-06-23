package com.example.menstrualapp.retrofit;
import com.example.menstrualapp.Config;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static final String BASE_URL="http://"+Config.Server_ip+":8000/api/";

    private static Retrofit retrofit = null;
    private static  RetrofitClient mInstance;

    private RetrofitClient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws java.io.IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .addHeader("Content-Type", "application/json")  // Set Content-Type
                                .addHeader("Accept", "application/json")      // Set Accept
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static synchronized RetrofitClient getInstance(){
        if (mInstance==null)
            mInstance=new RetrofitClient();
        return mInstance;
    }
    public ApiService getApi(){
        return  retrofit.create(ApiService.class);
    }

}
