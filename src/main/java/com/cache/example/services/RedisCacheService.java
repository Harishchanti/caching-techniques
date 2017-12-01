package com.cache.example.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cache.example.dto.BookDTO;

@Service
public class RedisCacheService implements CacheService {

	private static final String HASH_KEY = "Student";

	private final Logger log = LoggerFactory.getLogger(RedisCacheService.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("rawtypes")
	private HashOperations hashOps;

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean put(String key, Object value) {
		boolean sucess = true;
		try {
			hashOps.put(key, HASH_KEY, value);
			log.info("Added Recored :  Key : {} \n value : {} ", key, value);
		} catch (Exception e) {
			sucess = false;
			log.error("Error while adding the key {} ", key);
			e.printStackTrace();
		}
		return sucess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get(String key) {
		Object bookdto = null;
		try {
			bookdto = hashOps.get(key,HASH_KEY);
		} catch (Exception e) {
			log.error("Error while getting key : {}", key);
		}
		return bookdto;
	}

	@Override
	public CacheType getCacheType() {
		return CacheType.REDIS;
	}

}
