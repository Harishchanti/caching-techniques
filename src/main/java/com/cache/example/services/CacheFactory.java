package com.cache.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheFactory {

	@Autowired
	List<CacheService> cacheServices;

	public CacheService getCacheObject(CacheType x) {
		return cacheServices.stream().filter(type -> x.equals(type.getCacheType())).findFirst().get();
	}

}
