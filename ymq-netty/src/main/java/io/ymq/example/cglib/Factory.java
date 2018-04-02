package io.ymq.example.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-04-02 14:05
 **/
public class Factory {
    /**
     * 获得增强之后的目标类，即添加了切入逻辑advice之后的目标类
     *
     * @param proxy
     * @return
     */
    public static User getInstance(CglibUserProxy proxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(User.class);

        //回调方法的参数为代理类对象CglibUserProxy，最后增强目标类调用的是代理类对象CglibUserProxy中的intercept方法
        enhancer.setCallback(proxy);

        // 此刻user不是单纯的目标类，而是增强过的目标类
        User user = (User) enhancer.create();
        return user;
    }
}