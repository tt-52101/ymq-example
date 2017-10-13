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
