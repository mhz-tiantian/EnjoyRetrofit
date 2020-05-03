package com.mhz.enjoyretrofit.onClick;

import android.view.View;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventType(listenerType = View.OnLongClickListener.class,listenerSetting = "setOnLongClickListener")
public @interface OnLongClick {

    @IdRes int[] value();
}
