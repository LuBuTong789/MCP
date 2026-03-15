package com.example;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 增删改查工具类（基于 String 类型的 RedisTemplate）
 * 前置条件：已配置好 RedisTemplate（绑定连接工厂、设置字符串序列化器）
 */
@Component
public class RedisCrudUtil {

    // 注入配置好的 RedisTemplate<String, String>
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    // ===================== 一、字符串（String）类型 CRUD =====================
    /**
     * 新增/修改（String）：存在则修改，不存在则新增
     * @param key 键
     * @param value 值
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 新增/修改（String）：带过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位（秒/分/时等）
     */
    public void setStringWithExpire(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 查询（String）：根据key取值
     * @param key 键
     * @return 值（null表示不存在）
     */
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除（String）：根据key删除
     * @param key 键
     * @return 是否删除成功
     */
    public Boolean deleteString(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断key是否存在（通用）
     * @param key 键
     * @return true=存在，false=不存在
     */
    public Boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // ===================== 二、哈希（Hash）类型 CRUD =====================
    /**
     * 新增/修改（Hash）：向Hash中添加单个字段
     * @param hashKey Hash主键
     * @param field 子字段
     * @param value 子字段值
     */
    public void setHash(String hashKey, String field, String value) {
        redisTemplate.opsForHash().put(hashKey, field, value);
    }

    /**
     * 新增/修改（Hash）：批量添加字段
     * @param hashKey Hash主键
     * @param map 字段-值映射
     */
    public void setHashBatch(String hashKey, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(hashKey, map);
    }

    /**
     * 查询（Hash）：获取单个字段值
     * @param hashKey Hash主键
     * @param field 子字段
     * @return 子字段值
     */
    public String getHash(String hashKey, String field) {
        return (String) redisTemplate.opsForHash().get(hashKey, field);
    }

    /**
     * 查询（Hash）：获取Hash所有字段和值
     * @param hashKey Hash主键
     * @return 所有字段-值映射
     */
    public Map<Object, Object> getHashAll(String hashKey) {
        return redisTemplate.opsForHash().entries(hashKey);
    }

    /**
     * 删除（Hash）：删除Hash中的单个字段
     * @param hashKey Hash主键
     * @param field 子字段
     * @return 是否删除成功
     */
    public Long deleteHash(String hashKey, String field) {
        return redisTemplate.opsForHash().delete(hashKey, field);
    }

    // ===================== 三、列表（List）类型 CRUD =====================
    /**
     * 新增（List）：从列表尾部添加元素
     * @param listKey 列表键
     * @param value 元素值
     * @return 列表新长度
     */
    public Long addListRight(String listKey, String value) {
        return redisTemplate.opsForList().rightPush(listKey, value);
    }

    /**
     * 查询（List）：获取列表指定范围的元素
     * @param listKey 列表键
     * @param start 起始索引（0开始）
     * @param end 结束索引（-1表示最后一个）
     * @return 元素列表
     */
    public List<String> getList(String listKey, long start, long end) {
        return redisTemplate.opsForList().range(listKey, start, end);
    }

    /**
     * 修改（List）：修改列表指定索引的元素
     * @param listKey 列表键
     * @param index 索引（0开始）
     * @param value 新值
     */
    public void updateList(String listKey, long index, String value) {
        redisTemplate.opsForList().set(listKey, index, value);
    }

    /**
     * 删除（List）：删除列表中指定值的元素
     * @param listKey 列表键
     * @param count 删除次数（0=删除所有，正数=从头部删，负数=从尾部删）
     * @param value 要删除的值
     * @return 删除的元素个数
     */
    public Long deleteList(String listKey, long count, String value) {
        return redisTemplate.opsForList().remove(listKey, count, value);
    }

    // ===================== 通用操作 =====================
    /**
     * 设置key过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否设置成功
     */
    public Boolean expireKey(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 根据前缀模糊查询key
     * @param prefix 键前缀（如"test:*"）
     * @return 匹配的key集合
     */
    public Set<String> searchKeyByPrefix(String prefix) {
        return redisTemplate.keys(prefix);
    }
}