package io.ymq.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 描述:使用 Phoenix 提供的api操作hbase读取数据
 *
 * @author yanpenglei
 * @create 2017-09-21 14:47
 **/
public class PhoenixTest {

    public static void main(String[] args) throws Throwable {
        try {

            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

            //这里配置zookeeper的地址，可单个，多个(用","分隔)可以是域名或者ip

            String url = "jdbc:phoenix:node4:2181";

            Connection conn = DriverManager.getConnection(url);

            Statement statement = conn.createStatement();

            long time = System.currentTimeMillis();

            ResultSet rs = statement.executeQuery("select * from test");

            while (rs.next()) {
                String myKey = rs.getString("MYKEY");
                String myColumn = rs.getString("MYCOLUMN");

                System.out.println("myKey=" + myKey + "myColumn=" + myColumn);
            }

            long timeUsed = System.currentTimeMillis() - time;

            System.out.println("time " + timeUsed + "mm");

            // 关闭连接
            rs.close();
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
