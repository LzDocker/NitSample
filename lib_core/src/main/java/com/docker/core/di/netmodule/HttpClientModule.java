package com.docker.core.di.netmodule;

import android.content.Context;
import android.util.Log;

import com.docker.core.di.netmodule.converter.GsonConverterFactory;
import com.docker.core.di.module.cookie.CookieJarImpl;
import com.docker.core.di.module.cookie.PersistentCookieStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class HttpClientModule {

    private Context context;

    public HttpClientModule(Context context) {
        this.context = context;
    }

//    @Singleton
//    @Provides
//    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
//        Log.d("sss", "provideRetrofit: =============111========");
//
//        return builder
//                .baseUrl(httpUrl)
//                .client(client)
//                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl, ConverterHoledr converterHoledr) {
        builder.baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if (converterHoledr != null && converterHoledr.mConverters.size() > 0) {
            for (int i = 0; i < converterHoledr.mConverters.size(); i++) {
                builder.addConverterFactory(converterHoledr.mConverters.get(i));
                Log.d("sss", "provideRetrofit: ========add=  cover==");
            }
        }
        builder.addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }


//    @Singleton
//    @Provides
//    @Named("rxretrofit")
//    Retrofit provideRxRetrofit(Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl) {
//        return builder
//                .baseUrl(httpUrl)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }


    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            MyTrustManager mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }

    //实现X509TrustManager接口
    public static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder okHttpClient, Interceptor intercept
            , List<Interceptor> interceptors, CookieJar cookieJar, HttpLoggingInterceptor loggingInterceptor) {


//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient.Builder builder = okHttpClient
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(createSSLSocketFactory(), new MyTrustManager())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })

//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(intercept)
                .addInterceptor(loggingInterceptor);

        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }


    @Provides
    CookieJar providerCookieJar() {
        return new CookieJarImpl(new PersistentCookieStore(context));
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson;
    }

    @Singleton
    @Provides
    public MHeader provideHeader(HttpUrl httpUrl) {
        MHeader reauestHeader = new MHeader();
        reauestHeader.setBaseUrl(httpUrl.toString());
        reauestHeader.setServerUrl(httpUrl.toString());
        return reauestHeader;
    }

    @Singleton
    @Provides
    Interceptor provideIntercept(RequestInterceptor interceptor) {
        return interceptor;
    }


    @Singleton
    @Provides
    public HttpLoggingInterceptor provideLog() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}
