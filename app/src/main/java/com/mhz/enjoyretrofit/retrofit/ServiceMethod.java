package com.mhz.enjoyretrofit.retrofit;

import android.nfc.cardemulation.OffHostApduService;

import com.mhz.enjoyretrofit.retrofit.annotation.Field;
import com.mhz.enjoyretrofit.retrofit.annotation.GET;
import com.mhz.enjoyretrofit.retrofit.annotation.POST;
import com.mhz.enjoyretrofit.retrofit.annotation.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class ServiceMethod {

    private static final String HTTP_METHOD_GET = "GET";

    public static final String HTTP_METHOD_POST = "POST";

    private final HttpUrl baseUrl;
    private final Call.Factory factory;
    private  FormBody.Builder formBuild;
    // 用于记录当前请求是一个post请求 , 还是get请求
    private  String httpMethod;
    //记录请求地址
    private String realUrl;
    // 有没有请求体
    private boolean hasBody;

    private  final ParameterHandler[] parameterHandlers;


    private HttpUrl.Builder urlBuilder;



    public ServiceMethod(Builder builder) {
        this.hasBody = builder.hasBody;
        this.httpMethod = builder.httpMethod;
        this.realUrl = builder.realUrl;
        this.baseUrl = builder.enjoyRetrofit.baseUrl;
        this.factory = builder.enjoyRetrofit.factory;
        parameterHandlers = builder.parameterHandlers;
        if (hasBody) {
            formBuild = new FormBody.Builder();
        }


    }

    public Object invoke(Object[] args) {
        /**
         * 处理
         * 1. 请求的地址
         *
         */

        for (int i = 0; i < parameterHandlers.length; i++) {
            ParameterHandler handler = parameterHandlers[i];
            handler.apply(this,args[i].toString());
        }
        //
        HttpUrl url;
        if (urlBuilder == null) {
            urlBuilder = baseUrl.newBuilder(realUrl);
        }
        url = urlBuilder.build();
        FormBody formBody = null;
        if (formBuild != null) {
            formBody = formBuild.build();
        }
        Request request = new Request.Builder().url(url).method(httpMethod, formBody).build();

        return factory.newCall(request);
    }

    // post请求的处理  , 把 key -value 拼接到请求提里面
    public void addFiledParameter(String key, String value) {
        formBuild.add(key, value);

    }

    // get请求的处理  , 把 key -value拼接到url里面
    public void addQuereyParameter(String key, String value) {
        if (urlBuilder == null) {
            //把获取的注解的值, 还有baseUrl 拼接起来
            urlBuilder = baseUrl.newBuilder(realUrl);
        }
        urlBuilder.addQueryParameter(key, value);

    }

    public static final class Builder {
        private final EnjoyRetrofit enjoyRetrofit;
        //方法上的注解
        private final Annotation[] methodAnnotations;
        //方法上，差数的注解
        private final Annotation[][] parameterAnnotations;
        // 用于记录当前请求是一个post请求 , 还是get请求
        private  String httpMethod;
        //记录请求地址
        private String realUrl;
        // 有没有请求体
        private boolean hasBody;

        private ParameterHandler[] parameterHandlers;


        public Builder(EnjoyRetrofit enjoyRetrofit, Method method) {
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
                    this.httpMethod = HTTP_METHOD_GET;
                    // 获取请求的地址
                    this.realUrl = ((GET) annotation).value();
                    this.hasBody = false;
                } else if (annotation instanceof POST) {
                    httpMethod = HTTP_METHOD_POST;
                    // 获取请求地址
                    this.realUrl = ((POST) annotation).value();
                    this.hasBody = true;
                }

                // 解析方法参数上的注解
                int length = parameterAnnotations.length;
                parameterHandlers = new ParameterHandler[length];
                for (int i = 0; i < length; i++) {
                    // 得到一个参数上的注解
                    Annotation[] annotations = parameterAnnotations[i];
                    // 处理参数上的每一个注解
                    for (Annotation parameterAnnotation : annotations) {
                        if (parameterAnnotation instanceof Field) {
                            if (HTTP_METHOD_GET.equals(httpMethod)) {
                                throw new RuntimeException("field 注解只能用在post方法上");
                            }
                            // 得到注解上的值, 请求cause的
                            String value = ((Field) parameterAnnotation).value();
                            parameterHandlers[i] = new ParameterHandler.FiledParameterHandler(value);

                        } else if (parameterAnnotation instanceof Query) {
                            if (HTTP_METHOD_POST.equals(httpMethod)) {
                                throw new RuntimeException("query 注解只能用在get方法上");
                            }
                            //得到注解上的值  请求参数的key
                            String value = ((Query) parameterAnnotation).value();
                            parameterHandlers[i] = new ParameterHandler.QueryParameterHandler(value);
                        }

                    }

                }

            }


            return new ServiceMethod(this);
        }

    }
}
