package com.orhanobut.wasp.parsers;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.wasp.utils.MimeTypes;

import java.io.IOException;
import java.lang.reflect.Type;

public class GsonParser implements Parser {

  private final Gson gson;

  public GsonParser() {
    this(new Gson());
  }

  public GsonParser(Gson gson) {
    if (gson == null) {
      throw new NullPointerException("Gson object should not be null");
    }
    this.gson = gson;
  }

  @Override
  public <T> T fromBody(byte[] content, Type type, String charset) throws IOException {
    String contentStr = new String(content, charset);
    if (TextUtils.isEmpty(contentStr)) {
      return null;
    }
    if (type == null) {
      throw new NullPointerException("Type should not be null");
    }
    return gson.fromJson(contentStr, type);
  }

  @Override
  public Object toBody(Object body) {
    if (body == null) {
      return null;
    }
    return gson.toJson(body);
  }

  @Override
  public String getSupportedContentType() {
    return MimeTypes.CONTENT_JSON;
  }

}
