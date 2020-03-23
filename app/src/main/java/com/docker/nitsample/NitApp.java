package com.docker.nitsample;

import com.docker.cirlev2.util.paser.DynamicConverterFactory;
import com.docker.common.common.config.CommonWidgetModel;
import com.docker.common.common.config.ThiredPartConfig;
import com.docker.core.base.BaseApp;
import com.docker.core.di.netmodule.ConverterHoledr;
import com.docker.core.di.netmodule.GlobalConfigModule;
import com.docker.core.di.netmodule.HttpRequestHandler;
import com.docker.module_im.init.NimInitManagerCore;
import com.docker.nitsample.di.DaggerAppComponent;

import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class NitApp extends BaseApp {
    @Override
    protected void injectApp() {
        DaggerAppComponent.builder()
                .appModule(getAppModule())
                .globalConfigModule(getGlobalConfigModule())
                .httpClientModule(getHttpClientModule())
                .cacheModule(getCacheModule())
                .build()
                .inject(this);
    }

    @Override
    protected GlobalConfigModule getGlobalConfigModule() {

        ConverterHoledr converterHoledr = new ConverterHoledr();
        converterHoledr.mConverters.add(initDynamicProConverter());
        return GlobalConfigModule.buidler()
                .baseurl(ThiredPartConfig.ServeUrl)
                .converterHoled(converterHoledr)
                .globeHttpHandler(new HttpRequestHandler() {
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        if (response.code() == 505) {
                            Response originalResponse = new Response.Builder()
                                    .code(200) // 其实code可以随便给
                                    .protocol(Protocol.HTTP_2)
                                    .message("Network is closed by login")
                                    .body(ResponseBody.create(MediaType.parse("text/html; charset=utf-8"),
                                            "{\"errno\":\"0\",\"errmsg\":\"ok\",\"rst\":[]}")) // 返回空页面
                                    .request(chain.request())
                                    .build();
                            return originalResponse;
                        }
                        return response;
                    }

                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        return request;
                    }
                }).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonWidgetModel.initrefresh();
        ThiredPartConfig.init(this);
        // 网易云信初始化
        NimInitManagerCore nimInitManagerCore = new NimInitManagerCore();
        nimInitManagerCore.init(this);
    }

    /*
     * extInfomMap  key：     serverdatabean 中的type 字段值
     *              value:    serverdatabean 中 extdata 实现类的全路径名
     *
     * */
    private Converter.Factory initDynamicProConverter() {

        HashMap<String, String> extInfomMap = new HashMap<>();
        // 新闻
        extInfomMap.put("news","com.docker.cirlev2.vo.pro.News");

        return DynamicConverterFactory.create();
    }
}
