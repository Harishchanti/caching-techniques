package com.cache.example.services;

public interface CacheService {
	public boolean put(String key,Object value);
	public Object get(String  key);
	public CacheType getCacheType();
}
