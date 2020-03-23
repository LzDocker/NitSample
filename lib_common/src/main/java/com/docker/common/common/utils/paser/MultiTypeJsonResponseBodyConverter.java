package com.docker.common.common.utils.paser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.docker.common.common.vo.servervo.vo.DynamicDataBase;
import com.docker.common.common.vo.servervo.vo.ExtDataBase;
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



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        MultiTypeJsonParser<ExtDataBase> multiTypeJsonParser =
                builder.registerTypeElementName("type")
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