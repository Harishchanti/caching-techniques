package com.cache.example.services;

public interface CacheService {
	public boolean put(String key,Object value);
	public <T> T get(String  key,Class<T> T);
	public CacheType getCacheType();
}
