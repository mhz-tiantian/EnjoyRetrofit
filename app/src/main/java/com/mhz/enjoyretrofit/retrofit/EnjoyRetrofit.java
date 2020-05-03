package com.mhz.enjoyretrofit.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class EnjoyRetrofit {

    HttpUrl baseUrl;
    // 线程安全的, 用分段锁来实现的
    final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();
    Call.Factory factory;

    EnjoyRetrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.factory = builder.callFactory;

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
         * 参数2, 代理的接口,   第二个参数就确定了  返回的类型  是个什么类型
         * 3, InvocationHandler
         *
         */
        Object object = Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            /**
             *
             * @param proxy  代理类 , 代理的真实的对象
             * @param method 真实对象 调用的方法
             * @param args  调用方法的参数 信息
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // ServiceMethod 用来记录请求的类型， 参数等基本信息 的类
                ServiceMethod serviceMethod = loadServiceMethod(method);
                return serviceMethod.invoke(args);

            }
        });

        // 把 Object 强转成 T 类型返回
        return (T) object;
    }

    /**
     * @param method
     * @return
     */
    private ServiceMethod loadServiceMethod(Method method) {
        // 这里是一个缓存的 策略,如果 缓存中存在,就直接返回, 不存在再去新生成
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }
        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                //
                serviceMethodCache.put(method, result);
            }
        }

        return result;
    }

    public static final class Builder {
        HttpUrl baseUrl;
        Call.Factory callFactory;


        public Builder baseUrl(String baseUrl) {
            this.baseUrl = HttpUrl.get(baseUrl);
            return this;
        }

        public Builder callFactory(Call.Factory factory) {
            this.callFactory = factory;
            return this;
        }
        public EnjoyRetrofit build() {
            if (callFactory == null) {
                // 如果call Factory 是空的话，就创建出来一个默认的
                callFactory = new OkHttpClient();
            }

            return new EnjoyRetrofit(this);

        }

    }

}
