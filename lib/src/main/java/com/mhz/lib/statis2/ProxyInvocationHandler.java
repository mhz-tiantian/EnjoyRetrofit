package com.mhz.lib.statis2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    private final Rent rent;

    public ProxyInvocationHandler(Rent rent) {
        this.rent = rent;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Rent.class}, this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        seeHouse();
        Object object = method.invoke(rent, objects);
        fare();
        return object;
    }


    private void seeHouse() {
        System.out.println("带客户来看房子");
    }

    private void fare() {
        System.out.println("收取中介费");
    }
}
