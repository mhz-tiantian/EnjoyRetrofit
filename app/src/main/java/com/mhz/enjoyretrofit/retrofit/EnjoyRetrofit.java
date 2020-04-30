package com.mhz.enjoyretrofit.retrofit;

import com.mhz.enjoyretrofit.api.EnjoyWeatherApi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.HttpUrl;

public class EnjoyRetrofit {

    HttpUrl baseUrl;


    EnjoyRetrofit() {

    }

    /**
     * @param service
     * @param <T>
     * @return 返回T 类型的
     */
    public <T> T create(Class<T> service) {
        // 利用动态代理, 来返回动态的对象

        /**
         * newProxyInstance 参数1 , classLoader类型
         * 参数2, 代理的接口,
         * 3, InvocationHandler
         *
         */
        Object object = Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;

            }
        });

        // 把 Object 强转成 T 类型返回
        return (T) object;
    }

    public static final class Builder {

        public EnjoyRetrofit build() {
            return new   EnjoyRetrofit();

        }

        public Builder baseUrl(String baseUrl) {

            return this;
        }
    }

}
