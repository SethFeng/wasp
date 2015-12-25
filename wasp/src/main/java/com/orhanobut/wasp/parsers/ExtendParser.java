package com.orhanobut.wasp.parsers;

import android.net.Uri;

import com.orhanobut.wasp.utils.MimeTypes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Extend GsonParser
 * <p/>
 * toBody(): Http POST请求时组装body，扩展支持String / byte[] / Map<String, Object>
 * fromBody(): 解析Http返回数据，扩展支持String / byte[]
 * <p/>
 * Created by fengshzh on 15/12/25.
 */
public class ExtendParser extends GsonParser {

    // 从返回数据body解析结果
    @Override
    public <T> T fromBody(String content, Type type) throws IOException {

        if (type == String.class) {
            return (T) content;
        } else if (type.toString().equals("byte[]")) {
            return (T) content.getBytes("ISO-8859-1");
        }

        T typeValue = null;
        try {
            typeValue = super.fromBody(content, type);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return typeValue;
    }

    // Post请求组装body
    @Override
    public String toBody(Object body) {
        if (body instanceof String) {
            return body.toString();
        } else if (body instanceof byte[]) {
            String bodyString = null;
            try {
                bodyString = new String((byte[]) body, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return bodyString;
        }

        if (!(body instanceof Map)) {
            throw new IllegalArgumentException("BodyMap accepts only Map instances");
        }
        Map<String, Object> map;
        try {
            map = (Map<String, Object>) body;
        } catch (Exception e) {
            throw new ClassCastException("Map type should be Map<String,Object>");
        }

        Uri.Builder bodyBuilder = new Uri.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            bodyBuilder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
        }

        return bodyBuilder.toString().substring(1); // 去掉"?"
    }

    @Override
    public String getSupportedContentType() {
        return MimeTypes.CONTENT_JSON;
    }
}
