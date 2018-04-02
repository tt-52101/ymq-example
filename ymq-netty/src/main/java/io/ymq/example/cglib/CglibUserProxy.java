package io.ymq.example.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-04-02 14:05
 **/
public class CglibUserProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("before----开启事物---------");

        methodProxy.invokeSuper(object, objects);

        System.out.println("after----关闭事物----------");
        return null;
    }
}
