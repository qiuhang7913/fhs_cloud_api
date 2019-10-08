package com.self.framework.nosql.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class StringRedisService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @param key
     * @param value
     */
    public void insertObj(String key,String value){
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + value, e.getMessage());
        }
    }

    /**
     * 添加单个
     * 默认过期时间为两小时
     * @param key    key
     * @param filed  filed
     * @param domain 对象
     */
    public void insertObj(String key, String filed, Object domain){
        try {
            stringRedisTemplate.opsForHash().put(key, filed, domain);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + domain, e.getMessage());
        }

    }

    /**
     * redis 数据插入带失效时间
     * @param key
     * @param value
     */
    public void insertObj(String key, Long time, String value){
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + value, e.getMessage());
        }
    }

    /**
     * 添加单个
     * @param key    key
     * @param filed  filed
     * @param domain 对象
     * @param expire 过期时间（毫秒计）
     */
    public void insertObj(String key,String filed, Object domain,Integer expire){
        try {
            stringRedisTemplate.opsForHash().put(key, filed, domain);
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }catch (Exception e){
            logger.error("数据插入redis库异常,请求参数为{},异常错误信息可能为{}!", "key:" + key + "value:" + domain, e.getMessage());
        }
    }

    /**
     * key是否存在
     * @param key
     * @return
     */
    public Boolean isExist(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("数据操作异常!");
        }
        return false;
    }

    /**
     * 判断key和field下是否有值
     *
     * @param key 判断的key
     * @param field 判断的field
     */
    public Boolean hasKey(String key,String field) {
        try {
            return stringRedisTemplate.opsForHash().hasKey(key,field);
        }catch (Exception e){
            logger.error("数据操作异常!");
        }
        return false;

    }


    /**
     *
     * @param key
     * @return
     */
    public String getObj(String key){
        String value = "";
        try {
           if (isExist(key)){
               value = stringRedisTemplate.opsForValue().get(key);
           }
        }catch (Exception e){
            logger.error("数据查询异常!");
        }
        return value;
    }
    /**
     *
     * @param key
     * @return
     */
    public Object getObj(String key,String field){
        try {
            return stringRedisTemplate.opsForHash().get(key, field);
        }catch (Exception e){
            logger.error("数据查询异常!");
        }
        return null;
    }


    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 删除key下所有值
     *
     * @param key 查询的key
     */
    public void deleteKey(String key) {
        stringRedisTemplate.opsForHash().getOperations().delete(key);
    }

    /**
     * 删除key下所有值
     *
     * @param key 查询的key
     */
    public void deleteKey(String key, String filed) {
        stringRedisTemplate.opsForHash().delete(key, filed);
    }

    public void setTokenRefresh(String token,String username,String ip, String expireTime, String expireSecond){
        //刷新时间
        Integer expire = 7*24*60*60*1000;

        insertObj(token, "tokenValidTime",expireTime ,expire);
        insertObj(token, "expirationTime",expireSecond,expire);
        insertObj(token, "username", username, expire);
        insertObj(token, "ip",ip,expire);
    }
}
