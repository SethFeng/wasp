package com.orhanobut.wasp.parsers;

import java.io.IOException;
import java.lang.reflect.Type;

public interface Parser {

  /**
   * Parse an HTTP response body to a concrete object of the specified type.
   *
   * @param content HTTP response body content.
   * @param <T>     Target object type.
   * @param type    Target object type.
   * @return Instance of {@code <T>}.
   * @throws IOException if parsing was unable to complete.
   */
  <T> T fromBody(String content, Type type) throws IOException;

  /**
   * Convert an object to an appropriate representation for HTTP transport.
   *
   * @param body Object instance to convert.
   * @return Representation of the specified object as a String.
   */
  Object toBody(Object body);

  /**
   * @return The body content type supported by this parser.
   */
  String getSupportedContentType();
}
