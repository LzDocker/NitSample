package com.docker.cirlev2.util.paser;

import android.support.annotation.NonNull;

import com.docker.cirlev2.vo.pro.base.DynamicDataBase;
import com.docker.cirlev2.vo.pro.base.ExtDataBase;
import com.docker.core.di.netmodule.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MultiTypeJsonResponseBodyConverter implements Converter<ResponseBody, BaseResponse<List<DynamicDataBase>>> {

    Gson gson = createGson();

    private Gson createGson() {
        MultiTypeJsonParser.Builder<ExtDataBase> builder = new MultiTypeJsonParser.Builder();
        try {

            /*
             * 文章
             * */
            Class clazzNews = Class.forName("com.docker.cirlev2.vo.pro.News");
            builder.registerTypeElementValueWithClassType("news", clazzNews);

            /*
             * 动态
             * */
            Class clazzDynamic = Class.forName("com.docker.cirlev2.vo.pro.Dynamic");
            builder.registerTypeElementValueWithClassType("dynamic", clazzDynamic);

            /*
             * 问答
             * */
            Class clazzAnswer = Class.forName("com.docker.cirlev2.vo.pro.Answer");
            builder.registerTypeElementValueWithClassType("answer", clazzAnswer);

            /*
             * 商品
             * */
            Class clazzGoods = Class.forName("com.docker.cirlev2.vo.pro.Goods");
            builder.registerTypeElementValueWithClassType("goods", clazzGoods);

            /*
             * 活动
             * */
            Class clazzActive = Class.forName("com.docker.active.vo.ActiveVo");
            builder.registerTypeElementValueWithClassType("activity", clazzActive);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MultiTypeJsonParser<ExtDataBase> multiTypeJsonParser =
                builder.forceUseUpperTypeValue(true)
                        .registerTypeElementName("type")
                        .registerTargetClass(ExtDataBase.class)
                        .registerTargetUpperLevelClass(DynamicDataBase.class)
                        .build();
        return multiTypeJsonParser.getParseGson();
    }

    TypeAdapter<BaseResponse<List<DynamicDataBase>>> adapter = gson.getAdapter(new TypeToken<BaseResponse<List<DynamicDataBase>>>() {
    });

    @Override
    public BaseResponse<List<DynamicDataBase>> convert(@NonNull ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}