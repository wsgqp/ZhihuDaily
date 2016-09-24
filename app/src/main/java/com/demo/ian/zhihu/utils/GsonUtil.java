package com.demo.ian.zhihu.utils;

import com.demo.ian.zhihu.mvp.domain.ZhihuDetail;
import com.demo.ian.zhihu.mvp.domain.ZhihuFirstPageList;
import com.demo.ian.zhihu.mvp.domain.ZhihuThemes;
import com.demo.ian.zhihu.mvp.domain.ZhihuThemeList;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Admin on 2016-9-8.
 */
public class GsonUtil {

    public static Type token = new TypeToken<List<String>>() {
    }.getType();
    public static Gson mGson = new GsonBuilder().
            setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return false;
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
//            .registerTypeAdapter(token, new TypeAdapter<List<String>>() {
//
//                @Override
//                public void write(JsonWriter out, List<String> value) throws IOException {
//                    // Ignore
//                }
//
//                @Override
//                public List<String> read(JsonReader in) throws IOException, java.io.IOException {
//                    List<String> list = new ArrayList<>();
//                    in.beginArray();
//                    while (in.hasNext()) {
//                        list.add(new String(in.nextString()));
//                    }
//                    in.endArray();
//                    return list;
//                }
//            })
            .create();

    public static ZhihuFirstPageList parseZhihuJson(String latest) {
        return mGson.fromJson(latest, ZhihuFirstPageList.class);
    }

    public static ZhihuDetail parseZhihuDetail(String msg) {
        return mGson.fromJson(msg, ZhihuDetail.class);
    }

    public static ZhihuThemes parseZhihuThemes(String msg) {
        return mGson.fromJson(msg, ZhihuThemes.class);
    }

    public static ZhihuThemeList parseZhihuTheme(String msg){
        return mGson.fromJson(msg, ZhihuThemeList.class);
    }
}
