package io.ymq.redis.jedis.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.ymq.redis.jedis.JedisClusterUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * 描述: 操作redis 工具类
 * author: yanpenglei
 * Date: 2017/10/13 15:01
 */
public class CacheUtils {

    private static final Logger LOG = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * 将数据存入缓存
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveString(String key, String val) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.set(key, val);
    }

    /**
     * 将数据存入缓存的集合中
     *
     * @param key
     * @param val
     * @return
     */
    public static void saveToSet(String key, String val) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.sadd(key, val);
    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET
     * if Not eXists』(如果不存在，则 SET)的简写。
     * 保存成功，返回 true
     * 保存失败，返回 false
     *
     * @param key
     * @param val
     * @return
     */
    public static boolean saveNX(String key, String val) {

        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        /** 设置成功，返回 1 设置失败，返回 0 **/
        return (jedis.setnx(key, val).intValue() == 1);
    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SETif Not eXists』(如果不存在，则 SET)的简写。 <br>
     * 保存成功，返回 true <br>
     * 保存失败，返回 false
     *
     * @param key
     * @param val
     * @param expire 超时时间
     * @return 保存成功，返回 true 否则返回 false
     */
    public static boolean saveNX(String key, String val, int expire) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        boolean ret = (jedis.setnx(key, val).intValue() == 1);
        if (ret) {
            jedis.expire(key, expire);
        }
        return ret;
    }

    /**
     * 将数据存入缓存（并设置失效时间）
     *
     * @param key
     * @param val
     * @param seconds
     * @return
     */
    public static void saveString(String key, String val, int seconds) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.set(key, val);
        jedis.expire(key, seconds);
    }

    /**
     * 将自增变量存入缓存
     */
    public static void saveSeq(String key, long seqNo) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.del(key);
        jedis.incrBy(key, seqNo);
    }

    /**
     * 将递增浮点数存入缓存
     */
    public static void saveFloat(String key, float data) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.del(key);
        jedis.incrByFloat(key, data);
    }

    /**
     * 保存复杂类型数据到缓存
     *
     * @param key
     * @param obj
     * @return
     */
    public static void saveBean(String key, Object obj) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.set(key, JSONObject.toJSONString(obj));
    }

    /**
     * 保存复杂类型数据到缓存（并设置失效时间）
     *
     * @param key
     * @param obj
     * @param seconds
     * @return
     */
    public static void saveBean(String key, Object obj, int seconds) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.set(key, JSONObject.toJSONString(obj));
        jedis.expire(key, seconds);
    }

    /**
     * 存到指定的队列中
     *
     * @param key
     * @param val
     * @param size 队列大小限制 0：不限制
     */
    public static void saveToQueue(String key, String val, long size) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        if (size > 0 && jedis.llen(key) >= size) {
            jedis.rpop(key);
        }
        jedis.lpush(key, val);
    }

    /**
     * 保存到hash集合中
     *
     * @param hName 集合名
     * @param key
     * @param value
     */
    public static void hashSet(String hName, String key, String value) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.hset(hName, key, value);
    }


    /**
     * 保存到hash集合中
     *
     * @param hName
     * @param key
     * @param t
     * @param <T>
     */
    public static <T> void hashSet(String hName, String key, T t) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.hset(hName, key, JSONObject.toJSONString(t));
    }

    /**
     * 取得复杂类型数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */

    public static <T> T getBean(String key, Class<T> clazz) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        String value = jedis.get(key);
        if (value == null) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    /**
     * 从缓存中取得字符串数据
     *
     * @param key
     * @return 数据
     */
    public static String getString(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        // 暂时从缓存中取得
        return jedis.get(key);
    }

    /**
     * 从指定队列里取得数据
     *
     * @param key
     * @param size 数据长度
     * @return
     */
    public static List<String> getFromQueue(String key, long size) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        if (!jedis.exists(key)) {
            return new ArrayList<String>();
        }
        if (size > 0) {
            return jedis.lrange(key, 0, size - 1);
        } else {
            return jedis.lrange(key, 0, jedis.llen(key) - 1);
        }
    }

    /**
     * 功能: 从指定队列里取得数据<br />
     *
     * @param key
     * @return
     */
    public static String popQueue(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();

        if (!jedis.exists(key)) {
            return null;
        }

        return jedis.rpop(key);
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public static long getSeqNext(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.incr(key);
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public static long getSeqNext(String key, long by) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.incrBy(key, by);
    }

    /**
     * 将序列值回退一个
     *
     * @param key
     * @return
     */
    public static void getSeqBack(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.decr(key);
    }

    /**
     * 从hash集合里取得
     *
     * @param hName
     * @param key
     * @return
     */
    public static String hashGet(String hName, String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.hget(hName, key);
    }

    /**
     * 从hash集合里取得
     *
     * @param hName
     * @param key
     * @return
     */
    public static <T> T hashGet(String hName, String key, Class<T> clazz) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        String value = jedis.hget(hName, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    /**
     * 增加浮点数的值
     *
     * @param key
     * @return
     */
    public static float incrFloat(String key, float incrBy) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.incrByFloat(key, incrBy).floatValue();
    }

    /**
     * 判断是否缓存了数据
     *
     * @param key 数据KEY
     * @return 判断是否缓存了
     */
    public static boolean isCached(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.exists(key);
    }

    /**
     * 判断hash集合中是否缓存了数据
     *
     * @param hName
     * @param key   数据KEY
     * @return 判断是否缓存了
     */
    public static boolean hashCached(String hName, String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.hexists(hName, key);
    }

    /**
     * 判断是否缓存在指定的集合中
     *
     * @param key 数据KEY
     * @param val 数据
     * @return 判断是否缓存了
     */
    public static boolean isMember(String key, String val) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.sismember(key, val);
    }

    /**
     * 从缓存中删除数据
     *
     * @param key
     * @return
     */
    public static String delString(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        String str = jedis.get(key);
        jedis.del(key);
        return str;
    }

    /**
     * 从缓存中删除数据
     *
     * @param key
     * @return
     */
    public static void delKey(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.del(key);
    }

    /**
     * 从缓存中删除复杂数据
     *
     * @param key
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> E delBean(String key, Class<E> clazz) {
        E obj = getBean(key, clazz);
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.del(key.getBytes());
        return obj;
    }

    /**
     * 设置超时时间
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, int seconds) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.expire(key, seconds);
    }

    /**
     * 列出set中所有成员
     *
     * @param setName set名
     * @return
     */
    public static Set<String> listSet(String setName) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.smembers(setName);

    }

    /**
     * 向set中追加一个值
     *
     * @param setName set名
     * @param value
     */
    public static void setSave(String setName, String value) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.sadd(setName, value);
    }

    /**
     * 逆序列出sorted set包括分数的set列表
     *
     * @param key   set名
     * @param start 开始位置
     * @param end   结束位置
     * @return 列表
     */
    public static Set<Tuple> listSortedsetRev(String key, int start, int end) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.zrevrangeWithScores(key, start, end);
    }

    /**
     * 逆序取得sorted sort排名
     *
     * @param key    set名
     * @param member 成员名
     * @return 排名
     */
    public static Long getRankRev(String key, String member) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.zrevrank(key, member);
    }

    /**
     * 根据成员名取得sorted sort分数
     *
     * @param key    set名
     * @param member 成员名
     * @return 分数
     */
    public static Double getMemberScore(String key, String member) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.zscore(key, member);
    }

    /**
     * 向sorted set中追加一个值
     *
     * @param key    set名
     * @param score  分数
     * @param member 成员名称
     */
    public static void saveToSortedset(String key, Double score, String member) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.zadd(key, score, member);
    }

    /**
     * 从sorted set删除一个值
     *
     * @param key    set名
     * @param member 成员名称
     */
    public static void delFromSortedset(String key, String member) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.zrem(key, member);
    }

    /**
     * 从hash map中取得复杂类型数据
     *
     * @param key
     * @param field
     * @param clazz
     */
    public static <T> T getBeanFromMap(String key, String field, Class<T> clazz) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        String value = jedis.hget(key, field);
        if (value == null) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    /**
     * 从hashmap中删除一个值
     *
     * @param key   map名
     * @param field 成员名称
     */
    public static void delFromMap(String key, String field) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        jedis.hdel(key, field);
    }

    /**
     * 功能: 从hash中取得全部key对应所有field
     *
     * @param key hash集的名称
     * @return
     */
    public static Map<String, String> hashGetAll(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.hgetAll(key);
    }


    /**
     * 根据key增长 ，计数器
     *
     * @param key
     * @return
     */
    public static long incr(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.incr(key);
    }

    /**
     * 根据key获取当前计数结果
     *
     * @param key
     * @return
     */
    public static String getCount(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.get(key);
    }

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表
     *
     * @param <T>
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lpush(String key, T value) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.lpush(key, JSONObject.toJSONString(value));
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。 与 LPUSH 相反，当
     * key 不存在的时候不会进行任何操作
     *
     * @param key
     * @param value
     * @return
     */
    public static <T> Long lpushx(String key, T value) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.lpushx(key, JSONObject.toJSONString(value));
    }

    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.llen(key);
    }

    /**
     * (返回复杂对象)返回存储在 key 的列表里指定范围内的元素。 start 和 end
     * 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     *
     * @param <T>
     * @param key
     * @return
     */
    public static <T> List<T> lrange(String key, long start, long end, Class<T> clazz) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        List<String> list = jedis.lrange(key, start, end);
        if (list == null) {
            return null;
        }
        List<T> ts = new ArrayList<T>(list.size());
        for (String s : list) {
            ts.add(JSONObject.parseObject(s, clazz));
        }
        return ts;
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。 start 和 end
     * 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     *
     * @param key
     * @return
     */
    public static List<String> lrange(String key, long start, long end) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.lrange(key, start, end);
    }

    /**
     * 移除并且返回 key 对应的 list 的第一个元素
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        JedisCluster jedis = JedisClusterUtils.getJedisCluster();
        return jedis.lpop(key);
    }

    /**
     * 获取所有匹配的key
     *
     * @param pattern 表达式 (2016* 匹配所有2016的key)
     * @return
     */
    public static TreeSet<String> keys(String pattern) {
        JedisCluster jedisCluster = JedisClusterUtils.getJedisCluster();
        LOG.info("Start getting keys :{}...", pattern);
        TreeSet<String> keys = new TreeSet<String>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String k : clusterNodes.keySet()) {
            LOG.info("Getting keys from: {}", k);
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception e) {
                LOG.error("Getting keys error: {}", e);
            } finally {
                LOG.info("Connection closed.");
                connection.close();// 用完一定要close这个链接！！！
            }
        }
        LOG.info("Keys gotten!");
        return keys;
    }
}
