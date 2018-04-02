package io.ymq.example.proxy;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-04-02 11:51
 **/
public class UserImpl implements User {

    @Override
    public void addUser() {
        System.out.println("添加用户");
    }

    @Override
    public void delUser() {
        System.out.println("删除用户");
    }

}
