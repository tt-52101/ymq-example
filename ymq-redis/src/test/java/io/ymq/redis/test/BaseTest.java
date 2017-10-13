package io.ymq.redis.test;

import io.ymq.redis.jedis.JedisClusterUtils;
import io.ymq.redis.jedis.JedisSentinelUtils;
import org.junit.Test;

public class BaseTest {

    @Test
    public void clusterTest() {

        JedisClusterUtils.saveString("cluster-key", "www.ymq.io");

        System.out.println(JedisClusterUtils.getString("cluster-key"));

    }


    @Test
    public void sentineTest() {

        JedisSentinelUtils.saveString("sentine-key", "www.ymq.io");

        System.out.println(JedisSentinelUtils.getString("sentine-key"));

    }
}
