package com.mhz.lib.statis2;

public class MyRentDemo {

    public static void main(String[] agrs) {
        // 静态代理
//        Rent rent = new HostRent();
//        RentProxy rentProxy = new RentProxy(rent);
//        rentProxy.rent();


        // 动态代理
        Rent rent1 = new HostRent();
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler(rent1);
        Rent object = (Rent) proxyInvocationHandler.getProxy();
        object.rent();


//        Object object = Proxy.newProxyInstance(MyRentDemo.class.getClassLoader(),
//                new Class[]{Rent.class}, new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
//                        return method.invoke();
//                    }
//                });
//        Rent rentObject = (Rent) object;
//        rentObject.rent();
    }
}
