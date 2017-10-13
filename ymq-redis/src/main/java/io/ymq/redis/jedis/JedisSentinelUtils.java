package io.ymq.redis.jedis;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

/**
 * 描述: java Jedis 操作 redis 单实例
 *
 * @author yanpenglei
 * @create 2017-10-13 14:27
 **/

public class JedisSentinelUtils {

    private static Logger LOG = LoggerFactory.getLogger(JedisSentinelUtils.class);

    private static Jedis jedis = null;

    static {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1000);
        config.setMaxIdle(10);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379);

        LOG.info("JedisPoolConfig:{}", JSONObject.toJSONString(config));

        jedis = pool.getResource();

    }

    public static Jedis getJedisCluster() {
        return jedis;
    }

    /**
     * 将数据存入缓存
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveString(String key, String val) {
        jedis.set(key, val);
    }

    /**
     * 从缓存中取得字符串数据
     *
     * @param key
     * @return 数据
     */
    public static String getString(String key) {
        // 暂时从缓存中取得
        return jedis.get(key);
    }

}
