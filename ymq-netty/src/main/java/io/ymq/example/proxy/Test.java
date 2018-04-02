package io.ymq.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-04-02 12:03
 **/
public class Test {

    public static void main(String[] args) {
        User user = new UserImpl();

        //为用户管理添加事务处理
        InvocationHandler handler = new TransactionHandler(user);

        User proxyUser = (User) Proxy.newProxyInstance(user.getClass().getClassLoader(), user.getClass().getInterfaces(), handler);

        proxyUser.addUser();
        proxyUser.delUser();

        System.out.println(proxyUser.getClass());
    }
}
