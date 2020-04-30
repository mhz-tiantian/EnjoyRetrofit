package com.mhz.enjoyretrofit.retrofit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// PARAMETER 可以在差数上使用该注解  Retention 表示在运行期, 可以利用反射来拿到, 该注解
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String value();
}
