package com.orhanobut.wasp.parsers;

import android.net.Uri;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Extend GsonParser
 * <p/>
 * from body: 网络请求结果解析
 * <p/>
 * to body: POST请求的body拼装
 * <p/>
 * Created by fengshzh on 15/12/25.
 */
public class ExGsonParser extends GsonParser {

    @Override
    public <T> T fromBody(byte[] content, Type type, String charset) throws IOException {
        if (content == null) {
            return null;
        }

        if ("byte[]".equals(type.toString())) {
            return (T) content;
        } else if (type == String.class) {
            return (T) new String(content, charset);
        }

        T typeValue = null;
        try {
            typeValue = super.fromBody(content, type, charset);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return typeValue;
    }

    /**
     * 将参数body转换成网络库能处理的POST body格式.
     * 最终交给{@Link com.orhanobut.wasp.VolleyRequest#getBody()}
     *
     * @param body 参数body,支持的类型有:
     *             byte[]/String: 直接返回
     *             Map<String, Object>: 转化为MIME为application/x-www-form-urlencoded形式(a=1&b=2)
     * @return 格式转化后的body,格式为byte[]/String
     */
    @Override
    public Object toBody(Object body) {
        if (body instanceof String || body instanceof byte[]) {
            return body;
        }

        if (body instanceof Map) {
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

        return super.toBody(body);
    }
}
