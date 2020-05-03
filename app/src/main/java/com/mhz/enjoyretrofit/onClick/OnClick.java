package com.mhz.enjoyretrofit.onClick;


import android.view.View;

import androidx.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventType(listenerType = View.OnClickListener.class,listenerSetting = "setOnClickListener")
public @interface OnClick {

    /**
     *  IdRes表示的是 资源的id
     * @return
     */
   @IdRes int[] value();

}
