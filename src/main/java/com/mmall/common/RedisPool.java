package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by makersy on 2019
 */

public class RedisPool {
    private static JedisPool pool;  //jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));//jedis最大连接数
    private static Integer maxIdle =  Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "20"));//在jedispool中idle状态(空闲)实例的最大数量
    private static Integer minIdle =  Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "20"));//在jedispool中idle状态(空闲)实例的最小数量

    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.borrow", "true")); //在borrow一个jedis实例的时候，是否进行验证操作，true -> 得到的jedis实例是可以用的
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.return", "true")); //在return一个jedis实例的时候，是否进行验证操作，true -> 放回的jiedis实例是可以使用的

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));



    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true

        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
    }

    static {
        initPool();
    }

    /**
     * 获取jedis实例
     * @return
     */
    public static Jedis getJedis() {
        return pool.getResource();
    }

    /**
     * jedis对象出异常的时候，回收jedis对象资源
     * @param jedis
     */
    public static void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            pool.returnBrokenResource(jedis);
        }
    }

    /**
     * 回收jedis对象资源
     * @param jedis
     */
    public static void returnResource(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("hello", "world");
        returnResource(jedis);

        pool.destroy();//临时调用，销毁连接池中所有连接
        System.out.println("program is end");
    }
}
