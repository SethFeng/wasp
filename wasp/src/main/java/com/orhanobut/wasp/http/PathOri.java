package com.orhanobut.wasp.http;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Define the path of the endpoint
 * Difference from PATH, the value will not be encoded by URLEncoder
 */
@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface PathOri {
    String value();
}
