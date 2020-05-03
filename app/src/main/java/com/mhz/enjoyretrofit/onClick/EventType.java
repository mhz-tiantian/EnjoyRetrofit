package com.mhz.enjoyretrofit.onClick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ANNOTATION_TYPE 可以作用在注解上面的注解
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventType {

    //监听的类型
    Class listenerType();

    // 设置监听  调用 setOnClickListener 还是setOnLongclickListener
    String listenerSetting();

}
