package com.reservation.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofitHttpLocalORLive = null;
    private static ApiClient instance = null;
    private ApiInterface apiInterface;
    // Keep your services here, build them in buildRetrofit method later

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    // Build retrofit once when creating a single instance
    private ApiClient() {
        try {
            getApiInterface();
        } catch (Exception ex) {

        }
    }
    public ApiInterface getUserService() {
        return this.apiInterface;
    }

    public static ApiInterface getApiInterface() {
        if (retrofitHttpLocalORLive == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    //.addInterceptor(ChuckerInterceptor(get))
                    .build();

            retrofitHttpLocalORLive = new Retrofit.Builder()
                    .baseUrl(ServiceURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitHttpLocalORLive.create(ApiInterface.class);
    }
}
