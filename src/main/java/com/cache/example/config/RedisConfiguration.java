package com.cache.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration implements EnvironmentAware {

	private Environment env;
	private final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);

	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		log.info("Initializing jedis connection factory ");
		JedisConnectionFactory jc = new JedisConnectionFactory();
		jc.setHostName(env.getProperty("redis.host"));
		jc.setPort(env.getProperty("redis.port", Integer.class));
		return jc;

	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		log.info("Initializing redis templete ");
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		//template.setKeySerializer(new StringRedisSerializer());
		//template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		//template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		//cacheManager.setDefaultExpiration(0);
		return cacheManager;
	}

}
