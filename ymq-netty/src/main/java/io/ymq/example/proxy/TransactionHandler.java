package io.ymq.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-04-02 11:53
 **/
public class TransactionHandler implements InvocationHandler {

    private Object target;

    public TransactionHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("开启事务.....");

        Object result = null;

        try {

            // 程序执行
            result = method.invoke(target, args);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("提交事务.....");
        return result;
    }


}
