package io.ymq.example.cglib;

/**
 * 描述: https://blog.csdn.net/dreamrealised/article/details/12885739
 *
 * @author yanpenglei
 * @create 2018-04-02 14:14
 **/
public class Test {

    public static void main(String[] args) {

        CglibUserProxy proxy = new CglibUserProxy();

        User user = Factory.getInstance(proxy);

        System.out.println(user.getClass());
        user.addUser();
        user.delUser();


    }
}
