package com.mhz.enjoyretrofit.onClick;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BindClass {

    public static void init(Activity activity) {
        Class<? extends Activity> activityClass = activity.getClass();
        // 获得自己的方法, 包括private 修饰的getDeclaredMethods()
        Method[] methods = activityClass.getDeclaredMethods();
        // 这个才是  Activity里面我们定义的方法
        for (Method method : methods) {
            Annotation[] methodAnnotations = method.getAnnotations();
            try {

                for (Annotation methodAnnotation : methodAnnotations) {
                    Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
                    if (annotationType.isAnnotationPresent(EventType.class)) {
                        EventType eventType = annotationType.getAnnotation(EventType.class);
                        Class listenerClass = eventType.listenerType();

                        // 如果是长按事件的话
                      if ( listenerClass.isAssignableFrom(View.OnLongClickListener.class)){
                          Class<?> returnType = method.getReturnType();
                          if (!(returnType.isAssignableFrom(boolean.class) ||returnType.isAssignableFrom(Boolean.class))) {
                              throw new RuntimeException("长按请在方法提添加返回值");
                          }
                      }

                        String listenerSetting = eventType.listenerSetting();
                        // 获取 注解onClick 或者onLongClick里面 value() 方法的值  这个是注解里面的方法
                        Method valueMethod = annotationType.getDeclaredMethod("value");

                        // invoke()  方法参数1, 目标, 参数2: 方法的参数 获得Value() 方法的值
                        int[] viewIds = (int[]) valueMethod.invoke(methodAnnotation);
                        valueMethod.setAccessible(true);
                        ListenerInvocationHandler<Activity> handler = new ListenerInvocationHandler<>(method, activity);
                        // 这里返回  要不是一个OnClickListener 的对象, 要是一个OnLongClickListener 的对象
                        Object proxyInstance = Proxy.newProxyInstance(listenerClass.getClassLoader(), new Class[]{listenerClass}, handler);
                        // 遍历View  然后设置点击事件, 或者长按事件
                        for (int id : viewIds) {
                            View view = activity.findViewById(id);
                            // getMethod() 参数1. 方法名字 , 差数 2, 方法差数的CLass 对象
                            Method listenerMethod = view.getClass().getMethod(listenerSetting, listenerClass);
                            // 这个就是我们需要的参数
//                            Log.e("bindClass", "proxyInstance" + proxyInstance);
                            listenerMethod.invoke(view, proxyInstance);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     *  也可以在自定义View 的类中  来实现注入
     * @param <T>
     */
     private  static  class  ListenerInvocationHandler<T> implements InvocationHandler{


        private Method method;
        private T target;


        public ListenerInvocationHandler(Method method, T target) {
            this.method = method;
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // invoke 方法接受
            return this.method.invoke(target, args);
        }
    }
}
