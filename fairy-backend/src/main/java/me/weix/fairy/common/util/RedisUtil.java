package me.weix.fairy.common.util;

/**
 * @Author: WeiX
 * @Date: 2017/4/25
 * @Description:
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

	private static RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key
	 *            缓存的键值
	 * @param value
	 *            缓存的值
	 * @return 缓存的对象
	 */
	public static ValueOperations set(String key, Object value) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
		return operation;
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 *
	 * @param key
	 *            缓存的键值
	 * @param value
	 *            缓存的值
	 * @param expireTime
	 *            缓存时间
	 * @param timeUnit
	 *            缓存时间
	 * @return 缓存的对象
	 */
	public static ValueOperations set(String key, Object value, long expireTime, TimeUnit timeUnit) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		operation.set(key, JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
		// 缓存失效时间设置
		redisTemplate.expire(key, expireTime, timeUnit);
		return operation;
	}

	/**
	 * 获得缓存的基本对象。
	 *
	 * @param key
	 *            缓存键值
	 * @return 缓存键值对应的数据
	 */
	public static Object getCachedObject(String key) {
		ValueOperations<String, Object> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 缓存List数据
	 *
	 * @param key
	 *            缓存的键值
	 * @param dataList
	 *            待缓存的List数据
	 * @return 缓存的对象
	 */
	public static ListOperations<String, Object> set(String key, List<Object> dataList) {
		ListOperations listOperation = redisTemplate.opsForList();
		if (null != dataList) {
			int size = dataList.size();
			for (int i = 0; i < size; i++) {
				listOperation.rightPush(key, JSON.toJSONString(dataList.get(i), SerializerFeature.WriteMapNullValue));
			}
		}
		return listOperation;
	}

	/**
	 * 获得缓存的list对象
	 *
	 * @param key
	 *            缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public static List<Object> getList(String key) {
		List<Object> dataList = new ArrayList<Object>();
		ListOperations<String, Object> listOperation = redisTemplate.opsForList();
		Long size = listOperation.size(key);
		for (int i = 0; i < size; i++) {
			dataList.add((Object) listOperation.leftPop(key));
		}
		return dataList;
	}

	/**
	 * 缓存Set
	 *
	 * @param key
	 *            缓存键值
	 * @param dataSet
	 *            缓存的数据
	 * @return 缓存数据的对象
	 */
	public static BoundSetOperations<String, Object> set(String key, Set<Object> dataSet) {

		BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);
		Iterator<Object> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(JSON.toJSONString(it.next(), SerializerFeature.WriteMapNullValue));
		}
		return setOperation;
	}

	/**
	 * 获得缓存的set
	 *
	 * @param key
	 * @return
	 */
	public static Set getSet(String key) {
		Set<Object> dataSet = new HashSet<Object>();
		BoundSetOperations<String, Object> operation = redisTemplate.boundSetOps(key);
		Long size = operation.size();
		for (int i = 0; i < size; i++) {
			dataSet.add(operation.pop());
		}
		return dataSet;
	}

	/**
	 * 缓存Map
	 *
	 * @param key
	 * @param dataMap
	 * @return
	 */
	public static HashOperations set(String key, Map<Object, Object> dataMap) {
		HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
		if (null != dataMap) {
			for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
				hashOperations.put(key, JSON.toJSONString(entry.getKey(), SerializerFeature.WriteMapNullValue), JSON.toJSONString(entry.getValue(), SerializerFeature.WriteMapNullValue));
			}
		}
		return hashOperations;
	}

	/**
	 * 获得缓存的Map
	 *
	 * @param key
	 * @return
	 */
	public static Map<Object, Object> getCacheMap(String key) {
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		return map;
	}

	/**
	 * 根据key删除相应内容
	 *
	 * @param key
	 */
	public static void deleteObjectByKey(String key) {
		redisTemplate.delete(key);
	}
}
