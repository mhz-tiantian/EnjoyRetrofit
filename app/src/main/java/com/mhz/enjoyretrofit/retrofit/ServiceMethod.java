package com.mhz.enjoyretrofit.retrofit;

import com.mhz.enjoyretrofit.retrofit.annotation.GET;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ServiceMethod {


    public Object invoke(Object[] args) {
        return null;
    }

    public class builder {
        private final EnjoyRetrofit enjoyRetrofit;
        //方法上的注解
        private final Annotation[] methodAnnotations;
        //方法上，差数的注解
        private final Annotation[][] parameterAnnotations;



        public builder(EnjoyRetrofit enjoyRetrofit, Method method) {
            this.enjoyRetrofit = enjoyRetrofit;
            methodAnnotations = method.getAnnotations();
            // 获得方法参数 上的注解,  多个差数, 多个注解, 所以是一个二维数组
            parameterAnnotations = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            // 遍历 方法上的注解
            for (Annotation annotation : methodAnnotations) {
                // 如果是get 注解的话
                if (annotation instanceof GET) {

                }

            }


            return null;
        }
    }
}
