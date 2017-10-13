package io.ymq.redis.jedis;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 描述: java JedisCluster 操作 redis 集群
 *
 * @author yanpenglei
 * @create 2017-10-13 14:27
 **/

public class JedisClusterUtils {

    private static Logger LOG = LoggerFactory.getLogger(JedisClusterUtils.class);

    private static JedisCluster jedisCluster = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();

        //最大连接数, 默认8个
        config.setMaxTotal(1000);

        //大空闲连接数, 默认8个
        config.setMaxIdle(10);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(3000);

        //--------以下配置默认就可以-----------

        //最小空闲连接数, 默认0
        config.setMinIdle(0);

        //是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);

        //是否启用后进先出, 默认true
        config.setLifo(true);

        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);

        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);

        Set<HostAndPort> hps = new HashSet<HostAndPort>();

        String redisClusterIp = "10.4.89.161:6379";
        String[] ip = redisClusterIp.split(":");
        int port = Integer.valueOf(ip[1]);
        hps.add(new HostAndPort(ip[0], port));

        jedisCluster = new JedisCluster(hps, config);

        LOG.info("JedisPoolConfig:{}", JSONObject.toJSONString(config));

        Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();

        LOG.info("Get the redis thread pool:{}", nodes.toString());
    }

    public static JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    /**
     * 将数据存入缓存
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveString(String key, String val) {
        jedisCluster.set(key, val);
    }

    /**
     * 从缓存中取得字符串数据
     *
     * @param key
     * @return 数据
     */
    public static String getString(String key) {
        // 暂时从缓存中取得
        return jedisCluster.get(key);
    }


}
